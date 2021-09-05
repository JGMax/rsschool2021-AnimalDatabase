package gortea.jgmax.animalstorage

import android.app.Application
import androidx.room.Room
import gortea.jgmax.animalstorage.data.storage.dao.AnimalDao
import gortea.jgmax.animalstorage.data.storage.dao.DaoProvider
import gortea.jgmax.animalstorage.data.storage.dao.implementation.local.CursorAnimalDao
import gortea.jgmax.animalstorage.data.storage.database.local.DATABASE_NAME
import gortea.jgmax.animalstorage.data.storage.database.local.MySQLiteOpenHelper
import gortea.jgmax.animalstorage.data.storage.database.local.RoomAnimalDatabase

class AnimalListApp : Application(), DaoProvider {
    override var roomDAO: AnimalDao? = null
    override var cursorDAO: AnimalDao? = null

    override fun onCreate() {
        super.onCreate()
        configureRoom()
        configureSQLite()
    }

    private fun configureRoom() {
        val db = Room.databaseBuilder(
            applicationContext,
            RoomAnimalDatabase::class.java,
            DATABASE_NAME
        ).build()
        roomDAO = db.getAnimalDao()
    }

    private fun configureSQLite() {
        val openHelper = MySQLiteOpenHelper(context = applicationContext)
        cursorDAO = CursorAnimalDao(openHelper)
    }
}