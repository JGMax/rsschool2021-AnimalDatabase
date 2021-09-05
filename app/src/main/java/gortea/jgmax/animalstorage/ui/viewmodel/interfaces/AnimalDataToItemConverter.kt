package gortea.jgmax.animalstorage.ui.viewmodel.interfaces

import gortea.jgmax.animalstorage.data.model.AnimalItem

interface AnimalDataToItemConverter {
    fun dataToItem(name: String, age: Int, breed: String, id: Int = 0): AnimalItem
}