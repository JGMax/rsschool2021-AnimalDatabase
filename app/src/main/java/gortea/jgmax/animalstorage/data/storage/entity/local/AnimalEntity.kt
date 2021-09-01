package gortea.jgmax.animalstorage.data.storage.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.data.storage.entity.local.AnimalEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class AnimalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "animal_name") val name: String,
    @ColumnInfo(name = "animal_age") val age: Int,
    @ColumnInfo(name = "animal_breed") val breed: String
) {
    companion object {
        const val TABLE_NAME = "animals"
    }
}

fun AnimalEntity.toItem(): AnimalItem {
    return AnimalItem(
        id = id,
        name = name,
        age = age,
        breed = breed
    )
}
