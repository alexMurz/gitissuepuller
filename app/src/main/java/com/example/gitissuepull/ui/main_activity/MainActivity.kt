package com.example.gitissuepull.ui.main_activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gitissuepull.App
import com.example.gitissuepull.R
import com.example.gitissuepull.databinding.MainActivityBinding
import com.example.gitissuepull.entity.api.Repository
import com.example.gitissuepull.ui.list.ListFragment
import com.example.gitissuepull.ui.sub.SubscribeActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: MainActivityBinding
    lateinit var viewModel: MainActivityViewModel

    lateinit var drawerToggle: ActionBarDrawerToggle

    // Save ID to preserve fragment through rotations
    private var currentSubscription = -1

    private fun inject() {
        App.injector(this).also {
            it.inject(this)
            it.inject(viewModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        inject()
        viewModel.attach()
        init(savedInstanceState)
        attachLiveData()
    }

    private fun init(savedInstanceState: Bundle?) {
        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerToggle = ActionBarDrawerToggle(this, binding.drawer, binding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ).apply {
            isDrawerIndicatorEnabled = true
            syncState()
            binding.drawer.addDrawerListener(this)
        }

        // Preserve ID through rotations
        currentSubscription = savedInstanceState?.getInt(TAG_CURRENT_VIEW_ID, -1) ?: -1


        // Navi callback
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    // Setup liveData here, because backButton does not cause recreate then returning from other activity
    // but liveData still dies
    private fun attachLiveData() {
        // Change fragment in accordance with selected subscription
        viewModel.currentSub.observe(this, Observer {
            binding.isEmpty = it == null
            println("Test sub ${it?.id}")
            if (it != null) {
                if (it.id != currentSubscription) {
                    currentSubscription = it.id
                    showSubscription(it)
                }
            } else showNoSub()
        })

        // List of subscriptions
        viewModel.subscriptions.observe(this, Observer {
            val menu = binding.navigationView.menu
            menu.removeGroup(R.id.nav_menu_group_repo)
            for (sub in it) {
                menu.add(R.id.nav_menu_group_repo, sub.id, 0, viewModel.getSubscriptionTitle(sub))
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAG_CURRENT_VIEW_ID, currentSubscription)
    }

    private fun showSubscription(sub: Repository) {
        title = viewModel.getSubscriptionTitle(sub)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment().apply {
                arguments = Bundle().apply {
                    putString("owner", sub.owner.login)
                    putString("repo", sub.name)
                }
            })
            .commit()
    }

    private fun showNoSub() {
        title = getString(R.string.main_no_repo_title)
        for (f in supportFragmentManager.fragments) if (f is ListFragment) {
            supportFragmentManager.beginTransaction().remove(f).commit()
        }
    }

    private fun openNewSubscription() {
        startActivity(Intent(this, SubscribeActivity::class.java))
    }

    // Drawer navigation callback
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_menu_add_repo -> openNewSubscription()
            else -> viewModel.subscriptionClicked(item.itemId)
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    // Setup MainMenu, aka RepoMenu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /// Toolbar button + Repo menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> binding.drawer.openDrawer(GravityCompat.START)

            R.id.menu_unsubscribe -> viewModel.currentSub.value?.also { sub ->
                AlertDialog.Builder(this)
                    .setTitle(R.string.unsub_title)
                    .setMessage(getString(R.string.unsub_message, sub.fullName))
                    .setPositiveButton(R.string.unsub_confirm) {_, _ -> viewModel.unsubscribe() }
                    .setNegativeButton(R.string.unsub_cancel) { _, _ -> }
                    .show()
            }

            R.id.menu_open -> viewModel.openExternally()?.also {
                Intent(Intent.ACTION_VIEW).apply {
                    data = it
                    startActivity(this)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    /// Sync for drawer toggle state
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    companion object {
        const val TAG_CURRENT_VIEW_ID = "currentViewID"
    }


}
