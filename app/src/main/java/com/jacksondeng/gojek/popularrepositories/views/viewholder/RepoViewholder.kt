package com.jacksondeng.gojek.popularrepositories.views.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jacksondeng.gojek.popularrepositories.databinding.ListRepositoryItemBinding
import com.jacksondeng.gojek.popularrepositories.model.entity.RepoItem
import com.jacksondeng.gojek.popularrepositories.views.adapter.InteractionListener

class RepoViewholder(
    private val binding: ListRepositoryItemBinding,
    private val interactionListener: InteractionListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RepoItem) {
        binding.obj = item
        binding.root.setOnClickListener {
            interactionListener.onItemClicked(item.expanded, adapterPosition)
        }
        binding.executePendingBindings()
    }
}