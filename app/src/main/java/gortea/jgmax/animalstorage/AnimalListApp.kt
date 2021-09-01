package gortea.jgmax.animalstorage

import android.app.Application
import androidx.room.Room
import gortea.jgmax.animalstorage.data.storage.dao.AnimalDao
import gortea.jgmax.animalstorage.data.storage.database.local.RoomAnimalDatabase

class AnimalListApp : Application() {
    lateinit var animalListDao: AnimalDao

    override fun onCreate() {
        super.onCreate()
        configureRoom()
    }

    private fun configureRoom() {
        val db = Room.databaseBuilder(
            applicationContext,
            RoomAnimalDatabase::class.java,
            RoomAnimalDatabase.DATABASE_NAME
        ).build()
        animalListDao = db.getAnimalDao()
    }
}