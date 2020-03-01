package com.example.gitissuepull.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitissuepull.App
import com.example.gitissuepull.R
import com.example.gitissuepull.databinding.ListFragmentBinding
import com.example.gitissuepull.entity.RepositoryResult
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding
    private lateinit var viewModel: ListViewModel


    private fun inject() {
        App.injector(this).also {
            it.inject(this)
            it.inject(viewModel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val owner = arguments?.getString("owner") ?: error("No owner specified")
        val repo = arguments?.getString("repo") ?: error("No repo specified")

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        inject()
        viewModel.attachWith(owner, repo)

        binding.listener = viewModel
        binding.manager = LinearLayoutManager(context).also {
            // Restore list state
            if (savedInstanceState != null && savedInstanceState.containsKey(TAG_MANAGER_PARCELABLE)) {
                it.onRestoreInstanceState(savedInstanceState.getParcelable(TAG_MANAGER_PARCELABLE))
            }
        }

        binding.adapter = ListAdapter().apply {
            println("SET ISSUE OBSERVER")
            viewModel.issueData.observe(viewLifecycleOwner, Observer {
                println("Kekeke")
                when (it.type()) {
                    RepositoryResult.Type.Value -> {
                        println("UPDATE WITH ${it.getValue().size}")
                        items.clear()
                        items.addAll(it.getValue())
                        notifyDataSetChanged()
                    }
                    RepositoryResult.Type.Error -> {
                        Snackbar.make(binding.listView, getString(R.string.list_error, it.getError()), Snackbar.LENGTH_SHORT).show()
                    }
                }
            })
        }

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { binding.loading = it })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save list state
        outState.putParcelable(TAG_MANAGER_PARCELABLE, binding.manager?.onSaveInstanceState())
    }


    private companion object {
        const val TAG_MANAGER_PARCELABLE = "lfListManager"
    }
}
