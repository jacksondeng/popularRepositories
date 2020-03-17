package com.jacksondeng.gojek.popularrepositories.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jacksondeng.gojek.common.model.entity.RepoItem
import util.InteractionListener
import com.jacksondeng.gojek.popularrepositories.R
import com.jacksondeng.gojek.popularrepositories.databinding.ListRepositoryItemBinding
import com.jacksondeng.gojek.popularrepositories.util.RepoItemiffCallback
import com.jacksondeng.gojek.popularrepositories.views.viewholder.RepoItemViewholder

class RepositoriesAdapter(private val interactionListener: InteractionListener) :
    androidx.recyclerview.widget.ListAdapter<RepoItem, RepoItemViewholder>(
        RepoItemiffCallback()
    ) {

    private var expandedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListRepositoryItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.list_repository_item, parent, false
        )
        return RepoItemViewholder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: RepoItemViewholder, position: Int) =
        holder.bind(currentList[position].apply { expanded = (position == expandedPosition) })

    fun expand(position: Int) {
        if (position in 0 until currentList.size) {
            expandedPosition = position
            notifyDataSetChanged()
        }
    }

    fun collapseItem(position: Int) {
        if (position in 0 until currentList.size) {
            expandedPosition = -1
            notifyItemChanged(position)
        }
    }
}