package com.jacksondeng.gojek.popularrepositories.util

import androidx.recyclerview.widget.DiffUtil
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo

class RepoDiffCallback: DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return  oldItem.author == newItem.author && oldItem.name == newItem.name &&
                oldItem.repoUrl == newItem.repoUrl && oldItem.stars == newItem.stars &&
                oldItem.forks == newItem.forks && oldItem.description == newItem.description &&
                oldItem.language == newItem.language && oldItem.languageColor == newItem.languageColor &&
                oldItem.avatarUrl == newItem.avatarUrl
    }

}