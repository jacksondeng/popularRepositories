package com.jacksondeng.gojek.popularrepositories.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jacksondeng.gojek.popularrepositories.R
import com.jacksondeng.gojek.popularrepositories.data.api.BASE_URL
import com.jacksondeng.gojek.popularrepositories.data.api.FetchRepositoriesApiImpl
import com.jacksondeng.gojek.popularrepositories.data.api.TIMEOUT
import com.jacksondeng.gojek.popularrepositories.data.repo.FetchRepositoriesRepoImpl
import com.jacksondeng.gojek.popularrepositories.databinding.MainFragmentBinding
import com.jacksondeng.gojek.popularrepositories.util.State
import com.jacksondeng.gojek.popularrepositories.viemodel.FetchRepositoriesViewModel
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
                    // TODO: Display list
                }


                is State.Loading -> {
                    // TODO: Show loading state
                }

                is State.RefreshList -> {
                    // TODO: Refresh adapter
                }

                is State.Error -> {
                    // TODO: Show error state
                }
            }
        })
    }
}
