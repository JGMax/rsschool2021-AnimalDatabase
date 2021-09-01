package gortea.jgmax.animalstorage.ui.view

interface StateObserver {
    fun observeState()
    fun<T> showError(message: T)
}