package com.jacksondeng.gojek.popularrepositories.util

import androidx.recyclerview.widget.DiffUtil
import com.jacksondeng.gojek.common.model.entity.RepoItem

class RepoItemiffCallback : DiffUtil.ItemCallback<RepoItem>() {
    override fun areItemsTheSame(oldItem: RepoItem, newItem: RepoItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldRepoItem: RepoItem, newRepoItem: RepoItem): Boolean {
        val oldRepo = oldRepoItem.repo
        val newRepo = newRepoItem.repo
        return oldRepo.author == newRepo.author && oldRepo.name == newRepo.name &&
                oldRepo.repoUrl == newRepo.repoUrl && oldRepo.stars == newRepo.stars &&
                oldRepo.forks == newRepo.forks && oldRepo.description == newRepo.description &&
                oldRepo.language == newRepo.language && oldRepo.languageColor == newRepo.languageColor &&
                oldRepo.avatarUrl == newRepo.avatarUrl
    }

}