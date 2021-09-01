package gortea.jgmax.animalstorage.data.storage.dao

import gortea.jgmax.animalstorage.data.storage.entity.local.AnimalEntity
import io.reactivex.Completable
import io.reactivex.Single

interface AnimalDao {
    fun addEntity(entity: AnimalEntity): Completable
    fun getAllByName(): Single<List<AnimalEntity>>
    fun getAllByAge(): Single<List<AnimalEntity>>
    fun getAllByBreed(): Single<List<AnimalEntity>>
    fun getAll(): Single<List<AnimalEntity>>
    fun delete(entity: AnimalEntity): Completable
    fun update(entity: AnimalEntity): Completable
}