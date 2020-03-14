package com.jacksondeng.gojek.popularrepositories.views.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jacksondeng.gojek.popularrepositories.R
import com.jacksondeng.gojek.popularrepositories.databinding.ListRepositoryItemBinding
import com.jacksondeng.gojek.popularrepositories.model.entity.RepoItem
import com.jacksondeng.gojek.popularrepositories.util.leftDrawable
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

        // Set it programmatically to prevent icons showing up in different sizes
        binding.langaugeTv.leftDrawable(R.drawable.ic_circle_with_paddings, R.dimen.textview_drawable_size)
        binding.starsTv.leftDrawable(R.drawable.star_yellow_16, R.dimen.textview_drawable_size)
        binding.forksTv.leftDrawable(R.drawable.fork_black_16, R.dimen.textview_drawable_size)
    }
}