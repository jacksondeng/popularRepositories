package com.jacksondeng.gojek.popularrepositories.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.jacksondeng.gojek.popularrepositories.R
import com.jacksondeng.gojek.popularrepositories.data.api.BASE_URL
import com.jacksondeng.gojek.popularrepositories.data.api.FetchRepositoriesApiImpl
import com.jacksondeng.gojek.popularrepositories.data.api.TIMEOUT
import com.jacksondeng.gojek.popularrepositories.data.repo.FetchRepositoriesRepoImpl
import com.jacksondeng.gojek.popularrepositories.databinding.MainFragmentBinding
import com.jacksondeng.gojek.popularrepositories.model.entity.RepoItem
import com.jacksondeng.gojek.popularrepositories.util.EventType
import com.jacksondeng.gojek.popularrepositories.util.State
import com.jacksondeng.gojek.popularrepositories.viewmodel.FetchRepositoriesViewModel
import com.jacksondeng.gojek.popularrepositories.views.adapter.RepositoriesAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: FetchRepositoriesViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var repoAdapter: RepositoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initVm()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchRepositories()
    }

    private fun initVm() {
        // TODO: Inject with dagger
        viewModel = FetchRepositoriesViewModel(
            repo = FetchRepositoriesRepoImpl(
                api = FetchRepositoriesApiImpl(
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(MoshiConverterFactory.create())
                        .client(
                            OkHttpClient
                                .Builder()
                                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                                .addInterceptor(HttpLoggingInterceptor())
                                .build()
                        )
                        .build()
                )
            )
        )

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
