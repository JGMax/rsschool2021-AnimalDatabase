package gortea.jgmax.animalstorage.data.storage.database.local

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import gortea.jgmax.animalstorage.data.storage.entity.local.AnimalEntity
import gortea.jgmax.animalstorage.data.utils.types.SortType

private const val CREATE_TABLE_SQL =
    "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
            "($ID_COLUMN_NAME INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$ANIMAL_NAME_COLUMN_NAME TEXT, " +
            "$ANIMAL_AGE_COLUMN_NAME INTEGER, " +
            "$ANIMAL_BREED_COLUMN_NAME TEXT);"

class MySQLiteOpenHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(CREATE_TABLE_SQL)
        } catch (exception: SQLException) {
            Log.e("Database creation", "Exception while trying to create database", exception)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.e("Upgrade", "Initialized")
    }

    fun getCursorOfAnimals(): Cursor {
        return readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getCursorOfSortedAnimals(sortType: SortType): Cursor {
        return readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY ${sortType.value}", null)
    }

    fun addEntity(entity: AnimalEntity) {
        readableDatabase.execSQL("INSERT INTO $TABLE_NAME ($ANIMAL_NAME_COLUMN_NAME, $ANIMAL_AGE_COLUMN_NAME, $ANIMAL_BREED_COLUMN_NAME) VALUES (${entity.name}, ${entity.age}, ${entity.breed})")
    }

    fun deleteEntity(entity: AnimalEntity) {
        readableDatabase.execSQL("DELETE FROM $TABLE_NAME WHERE id=${entity.id}")
    }

    fun updateEntity(entity: AnimalEntity) {
        readableDatabase.execSQL("UPDATE $TABLE_NAME SET $ANIMAL_NAME_COLUMN_NAME=${entity.name}, $ANIMAL_AGE_COLUMN_NAME=${entity.age}, $ANIMAL_BREED_COLUMN_NAME=${entity.breed} WHERE id=${entity.id}")
    }
}