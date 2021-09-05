package gortea.jgmax.animalstorage.data.storage.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.data.storage.database.local.*

@Entity(tableName = TABLE_NAME)
data class AnimalEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = ID_COLUMN_NAME) val id: Int,
    @ColumnInfo(name = ANIMAL_NAME_COLUMN_NAME, typeAffinity = ColumnInfo.TEXT) val name: String,
    @ColumnInfo(name = ANIMAL_AGE_COLUMN_NAME, typeAffinity = ColumnInfo.INTEGER) val age: Int,
    @ColumnInfo(name = ANIMAL_BREED_COLUMN_NAME, typeAffinity = ColumnInfo.TEXT) val breed: String
)

fun AnimalEntity.toItem(): AnimalItem {
    return AnimalItem(
        id = id,
        name = name,
        age = age,
        breed = breed
    )
}
