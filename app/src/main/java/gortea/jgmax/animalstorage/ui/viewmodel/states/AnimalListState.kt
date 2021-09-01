package gortea.jgmax.animalstorage.ui.viewmodel.states

import gortea.jgmax.animalstorage.data.model.AnimalItem

sealed class AnimalListState {
    class Update(val newList: List<AnimalItem?>) : AnimalListState()
    class UpdateError<T>(val message: T) : AnimalListState()
    object Initial : AnimalListState()
    object SuccessInsert : AnimalListState()
}