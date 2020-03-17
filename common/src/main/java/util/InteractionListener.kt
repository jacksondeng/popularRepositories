package util

interface InteractionListener {
    fun onItemClicked(expanded: Boolean, position: Int)
}