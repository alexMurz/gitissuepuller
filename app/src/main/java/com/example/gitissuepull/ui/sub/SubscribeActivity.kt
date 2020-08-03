package com.example.gitissuepull.ui.sub

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.gitissuepull.R
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitissuepull.App
import com.example.gitissuepull.databinding.SubscribeActivityBinding


class SubscribeActivity: AppCompatActivity() {

    lateinit var binding: SubscribeActivityBinding
    lateinit var viewModel: SubscribeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.subscribe_activity)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Show keyboard then view appears
        binding.repoOwnerEdittext.requestFocus()
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?)?.also { service ->
            service.showSoftInput(binding.repoOwnerEdittext, InputMethodManager.SHOW_IMPLICIT)
        }

        init(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    fun inject() {
        App.injector(this).also {
            it.inject(this)
            it.inject(viewModel)
        }
    }

    private fun init(savedInstanceState: Bundle?) {
        val ctx = this
        val lifecycleOwner = this

        viewModel = ViewModelProvider(this)[SubscribeViewModel::class.java]
        inject()
        viewModel.attach()

        // Setup Recycler
        binding.manager = LinearLayoutManager(ctx).also {
            // Restore list state
            if (savedInstanceState != null && savedInstanceState.containsKey(TAG_MANAGER_PARCELABLE)) {
                it.onRestoreInstanceState(savedInstanceState.getParcelable(TAG_MANAGER_PARCELABLE))
            }
        }
        binding.adapter = SubscribeRepoListAdapter().apply {
            eventListener = viewModel::clicked
            viewModel.loadedRepos.observe(lifecycleOwner, Observer {
                if (it == null) return@Observer
                items.clear()
                items.addAll(it)
                notifyDataSetChanged()
//                Snackbar.make(binding.listView, getString(R.string.sub_error, it.getError()), Snackbar.LENGTH_SHORT).show()
//                it.getError().printStackTrace()
            })
        }

        // Reach bottom of the page listener
        binding.listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.listView.canScrollVertically(1)) viewModel.pageEndReached()

            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
//                if (!binding.listView.canScrollVertically(1)) viewModel.pageEndReached()
            }
        })

        // Callback on IME action
        binding.repoOwnerEdittext.setOnEditorActionListener { _, _, _ ->
            viewModel.startLoading(binding.repoOwnerEdittext.text.toString())
            true
        }

        // Bind isLoading
        viewModel.isLoading.observe(lifecycleOwner, Observer {
            binding.isLoading = it
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save list state
        outState.putParcelable(TAG_MANAGER_PARCELABLE, binding.manager?.onSaveInstanceState())
    }


    private companion object {
        const val TAG_MANAGER_PARCELABLE = "managerParcelable"
    }

}