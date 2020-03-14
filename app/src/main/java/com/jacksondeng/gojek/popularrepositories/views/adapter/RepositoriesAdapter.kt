package com.jacksondeng.gojek.popularrepositories.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jacksondeng.gojek.popularrepositories.R
import com.jacksondeng.gojek.popularrepositories.databinding.ListRepositoryItemBinding
import com.jacksondeng.gojek.popularrepositories.model.entity.RepoItem
import com.jacksondeng.gojek.popularrepositories.util.RepoItemiffCallback
import com.jacksondeng.gojek.popularrepositories.views.viewholder.RepoItemViewholder

interface InteractionListener {
    fun onItemClicked(expanded: Boolean, position: Int)
}

class RepositoriesAdapter(private val interactionListener: InteractionListener) :
    androidx.recyclerview.widget.ListAdapter<RepoItem, RepoItemViewholder>(
        RepoItemiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListRepositoryItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.list_repository_item, parent, false
        )
        return RepoItemViewholder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: RepoItemViewholder, position: Int) =
        holder.bind(currentList[position])

    fun expand(position: Int) {
        currentList[position].expanded = true
        notifyItemChanged(position)
    }

    fun collapseItem(position: Int) {
        currentList[position].expanded = false
        notifyItemChanged(position)
    }
}