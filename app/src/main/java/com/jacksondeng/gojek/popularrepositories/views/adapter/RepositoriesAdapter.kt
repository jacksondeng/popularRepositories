package com.jacksondeng.gojek.popularrepositories.views.adapter

import android.view.ViewGroup
import com.jacksondeng.gojek.popularrepositories.model.entity.Repo
import com.jacksondeng.gojek.popularrepositories.util.RepoDiffCallback
import com.jacksondeng.gojek.popularrepositories.views.viewholder.RepoViewholder

class RepositoriesAdapter : androidx.recyclerview.widget.ListAdapter<Repo, RepoViewholder>(
    RepoDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewholder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RepoViewholder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}