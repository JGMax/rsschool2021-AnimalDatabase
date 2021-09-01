package gortea.jgmax.animalstorage.data.repository

import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.data.utils.types.SortType
import io.reactivex.Completable
import io.reactivex.Single

interface AnimalRepository {
    fun addItem(item: AnimalItem): Completable
    fun getAll(sortBy: SortType?): Single<List<AnimalItem>>
    fun delete(item: AnimalItem): Completable
    fun update(item: AnimalItem): Completable
}