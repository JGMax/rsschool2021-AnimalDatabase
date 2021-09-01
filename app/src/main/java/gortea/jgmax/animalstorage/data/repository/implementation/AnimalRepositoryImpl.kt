package gortea.jgmax.animalstorage.data.repository.implementation

import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.data.model.toEntity
import gortea.jgmax.animalstorage.data.repository.AnimalRepository
import gortea.jgmax.animalstorage.data.storage.dao.AnimalDao
import gortea.jgmax.animalstorage.data.utils.types.SortType
import gortea.jgmax.animalstorage.data.storage.entity.local.toItem
import io.reactivex.Completable
import io.reactivex.Single

class AnimalRepositoryImpl(private val dao: AnimalDao) : AnimalRepository {
    override fun addItem(item: AnimalItem): Completable = dao.addEntity(item.toEntity())

    override fun getAll(sortBy: SortType?): Single<List<AnimalItem>> {
        return when (sortBy) {
            SortType.AGE -> dao.getAllByAge()
            SortType.NAME -> dao.getAllByName()
            SortType.BREED -> dao.getAllByBreed()
            null -> dao.getAll()
        }.map { list -> List(list.size) { list[it].toItem() } }
    }

    override fun delete(item: AnimalItem): Completable = dao.delete(item.toEntity())

    override fun update(item: AnimalItem): Completable = dao.update(item.toEntity())
}