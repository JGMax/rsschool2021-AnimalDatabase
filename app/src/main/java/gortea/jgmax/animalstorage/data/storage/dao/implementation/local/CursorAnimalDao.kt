package gortea.jgmax.animalstorage.data.storage.dao.implementation.local

import android.database.Cursor
import android.database.SQLException
import gortea.jgmax.animalstorage.data.storage.dao.AnimalDao
import gortea.jgmax.animalstorage.data.storage.database.local.*
import gortea.jgmax.animalstorage.data.storage.entity.local.AnimalEntity
import gortea.jgmax.animalstorage.data.utils.types.SortType
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver

class CursorAnimalDao(private val accessHelper: MySQLiteOpenHelper) : AnimalDao {
    override fun addEntity(entity: AnimalEntity): Completable {
        return CompletableResponse(accessHelper::addEntity, entity)
    }

    override fun getAllByName(): Single<List<AnimalEntity>> {
        accessHelper.getCursorOfSortedAnimals(SortType.NAME).use {
            return SingleListResponse(it)
        }
    }

    override fun getAllByAge(): Single<List<AnimalEntity>> {
        accessHelper.getCursorOfSortedAnimals(SortType.AGE).use {
            return SingleListResponse(it)
        }
    }

    override fun getAllByBreed(): Single<List<AnimalEntity>> {
        accessHelper.getCursorOfSortedAnimals(SortType.BREED).use {
            return SingleListResponse(it)
        }
    }

    override fun getAll(): Single<List<AnimalEntity>> {
        accessHelper.getCursorOfAnimals().use {
            return SingleListResponse(it)
        }
    }

    override fun delete(entity: AnimalEntity): Completable {
        return CompletableResponse(accessHelper::deleteEntity, entity)
    }

    override fun update(entity: AnimalEntity): Completable {
        return CompletableResponse(accessHelper::updateEntity, entity)
    }

    private class CompletableResponse(
        private val action: (AnimalEntity) -> Unit,
        private val entity: AnimalEntity
    ) : Completable() {
        override fun subscribeActual(observer: CompletableObserver?) {
            try {
                action.invoke(entity)
                observer?.onComplete()
            } catch (e: Exception) {
                observer?.onError(e)
            }
        }
    }

    private class SingleListResponse(private val cursor: Cursor) : Single<List<AnimalEntity>>() {
        override fun subscribeActual(observer: SingleObserver<in List<AnimalEntity>>) {
            try {
                val list = getDataListFromCursor(cursor)
                observer.onSuccess(list)
            } catch (exception: SQLException) {
                observer.onError(exception)
            }
        }

        private fun getDataListFromCursor(cursor: Cursor): List<AnimalEntity> {
            val list = mutableListOf<AnimalEntity>()
            if (cursor.moveToFirst()) {
                do {
                    val idIndex = cursor.getColumnIndex(ID_COLUMN_NAME)
                    val nameIndex = cursor.getColumnIndex(ANIMAL_NAME_COLUMN_NAME)
                    val ageIndex = cursor.getColumnIndex(ANIMAL_AGE_COLUMN_NAME)
                    val breedIndex = cursor.getColumnIndex(ANIMAL_BREED_COLUMN_NAME)
                    list.add(
                        AnimalEntity(
                            id = cursor.getInt(idIndex),
                            name = cursor.getString(nameIndex),
                            age = cursor.getInt(ageIndex),
                            breed = cursor.getString(breedIndex)
                        )
                    )
                } while (cursor.moveToNext())
            }
            return list
        }
    }
}