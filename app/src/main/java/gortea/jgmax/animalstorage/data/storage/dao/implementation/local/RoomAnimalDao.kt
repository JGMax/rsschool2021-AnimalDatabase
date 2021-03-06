package gortea.jgmax.animalstorage.data.storage.dao.implementation.local

import androidx.room.*
import gortea.jgmax.animalstorage.data.storage.dao.AnimalDao
import gortea.jgmax.animalstorage.data.storage.database.local.ANIMAL_AGE_COLUMN_NAME
import gortea.jgmax.animalstorage.data.storage.database.local.ANIMAL_BREED_COLUMN_NAME
import gortea.jgmax.animalstorage.data.storage.database.local.ANIMAL_NAME_COLUMN_NAME
import gortea.jgmax.animalstorage.data.storage.database.local.TABLE_NAME
import gortea.jgmax.animalstorage.data.storage.entity.local.AnimalEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RoomAnimalDao: AnimalDao {
    @Insert(entity = AnimalEntity::class, onConflict = OnConflictStrategy.ABORT)
    @JvmSuppressWildcards
    override fun addEntity(entity: AnimalEntity): Completable

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $ANIMAL_AGE_COLUMN_NAME")
    override fun getAllByAge(): Single<List<AnimalEntity>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $ANIMAL_NAME_COLUMN_NAME")
    override fun getAllByName(): Single<List<AnimalEntity>>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $ANIMAL_BREED_COLUMN_NAME")
    override fun getAllByBreed(): Single<List<AnimalEntity>>

    @Query("SELECT * FROM $TABLE_NAME")
    override fun getAll(): Single<List<AnimalEntity>>

    @Delete(entity = AnimalEntity::class)
    override fun delete(entity: AnimalEntity): Completable

    @Update(entity = AnimalEntity::class, onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    override fun update(entity: AnimalEntity): Completable
}