package com.jacksondeng.gojek.popularrepositories.views

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.jacksondeng.gojek.common.model.entity.RepoItem
import com.jacksondeng.gojek.common.util.EventType
import com.jacksondeng.gojek.common.util.State
import com.jacksondeng.gojek.common.util.ViewModelFactory
import com.jacksondeng.gojek.popularrepositories.R
import com.jacksondeng.gojek.popularrepositories.databinding.MainFragmentBinding
import com.jacksondeng.gojek.popularrepositories.util.*
import com.jacksondeng.gojek.githubrx.viewmodel.FetchRepositoriesViewModel
import com.jacksondeng.gojek.popularrepositories.views.adapter.RepositoriesAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: FetchRepositoriesViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var repoAdapter: RepositoriesAdapter
    private var isConnectedToNetwork = false

    private val networkCallback by lazy {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isConnectedToNetwork = true
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isConnectedToNetwork = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preLollipop {
            context?.installTls12IfNeeded {
                Toast.makeText(
                    context,
                    context?.getString(R.string.no_internet_connection_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }

            isConnectedToNetwork = context?.isOnline() ?: true
        }

        postLollipop {
            context?.registerNetworkCallback(networkCallback)
        }
        initVm()
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDestroy() {
        super.onDestroy()
        postLollipop {
            context?.unregisterNetworkCallback(networkCallback)
        }
    }

    private fun initVm() {
        viewModel =
            ViewModelProvider(this, viewModelFactory)[FetchRepositoriesViewModel::class.java]

        viewModel.fetchRepositories()

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loaded -> {
                    repoAdapter.submitList(state.repositories.map {
                        RepoItem(repo = it, expanded = false)
                    })
                }

                is State.Loading -> {
                    // TODO: Show loading state
                }

                is State.Error -> {
                    // TODO: Show error state
                }

                is State.Event -> {
                    handleEvent(state.type)
                }
            }
        })

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.emptyLayout.viewModel = viewModel
        binding.emptyLayout.lifecycleOwner = viewLifecycleOwner
    }

    private fun initViews() {
        repoAdapter = RepositoriesAdapter(interactionListener = viewModel)
        binding.repoList.apply {
            adapter = repoAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            // Prevent imageview flickering when submitList is called
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        binding.emptyLayout.btnTryAgain.setOnClickListener {
            if (isConnectedToNetwork) {
                viewModel.onRefresh()
            } else {
                Toast.makeText(
                    context,
                    context?.getString(R.string.no_internet_connection_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun handleEvent(eventType: EventType) {
        when (eventType) {
            is EventType.Expand -> {
                repoAdapter.expand(eventType.position)
            }

            is EventType.Collapse -> {
                repoAdapter.collapseItem(eventType.position)
            }
        }
    }
}
