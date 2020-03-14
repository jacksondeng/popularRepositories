package com.jacksondeng.gojek.popularrepositories.views.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jacksondeng.gojek.popularrepositories.databinding.ListItemRepositoryBinding
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo
import com.jacksondeng.gojek.popularrepositories.views.adapter.InteractionListener

class RepoViewholder(
    private val binding: ListItemRepositoryBinding,
    val interactionListener: InteractionListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(repo: Repo) {
        binding.obj = repo
        binding.root.setOnClickListener {
            interactionListener.onItemClicked(repo)
        }
        binding.executePendingBindings()
    }
}