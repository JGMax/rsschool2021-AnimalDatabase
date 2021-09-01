package gortea.jgmax.animalstorage.data.storage.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import gortea.jgmax.animalstorage.data.storage.dao.implementation.local.RoomAnimalDao
import gortea.jgmax.animalstorage.data.storage.entity.local.AnimalEntity

@Database(entities = [AnimalEntity::class], version = 1)
abstract class RoomAnimalDatabase: RoomDatabase() {
    abstract fun getAnimalDao(): RoomAnimalDao

    companion object {
        const val DATABASE_NAME="animal_database"
    }
}