package gortea.jgmax.animalstorage.ui.viewmodel.interfaces

import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.data.storage.dao.AnimalDao
import gortea.jgmax.animalstorage.data.utils.types.SortType

interface AnimalListCommunication {
    fun attachDao(dao: AnimalDao)
    fun addNewItem(animalItem: AnimalItem)
    fun deleteItem(animalItem: AnimalItem)
    fun updateItem(animalItem: AnimalItem)
    fun fetchItems(sortBy: SortType?)
}
