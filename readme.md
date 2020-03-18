# Trending Repositories

I created a few modules (githubcoroutine and githubrx) to demonstrate different way of doing async api calls or other async operations.

The final goal is to have two product flavors so that we can easily switch the implementation under the hood by toggle built variant. 

But there are still a lot of improvements can be made and a long way to go. For instance I need to create a BaseViewmodel and other dependencies in app module and githubcoroutine and githubrx modules' will have to extends those. 

With the current settings to switch from using the dependencies from one module to another, I will have to a few places in app module from (import com.jacksondeng.gojek.githubrx.viewmodel.FetchRepositoriesViewModel) to (import com.jacksondeng.gojek.githubcoroutine.viewmodel.FetchRepositoriesViewModel). Same goes to the some other dependencies.

Also I didn't have enough time to do UI test because I spent too much time on separating the module. 
