package com.jacksondeng.gojek.popularrepositories.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jacksondeng.gojek.popularrepositories.R
import com.jacksondeng.gojek.popularrepositories.databinding.ListItemRepositoryBinding
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo
import com.jacksondeng.gojek.popularrepositories.util.RepoDiffCallback
import com.jacksondeng.gojek.popularrepositories.views.viewholder.RepoViewholder

interface InteractionListener {
    fun onItemClicked(repo: Repo)
}

class RepositoriesAdapter(private val interactionListener: InteractionListener) : androidx.recyclerview.widget.ListAdapter<Repo, RepoViewholder>(
    RepoDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemRepositoryBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.list_repository_item, parent, false
        )
        return RepoViewholder(binding,interactionListener)
    }

    override fun onBindViewHolder(holder: RepoViewholder, position: Int) =
        holder.bind(currentList[position])
}