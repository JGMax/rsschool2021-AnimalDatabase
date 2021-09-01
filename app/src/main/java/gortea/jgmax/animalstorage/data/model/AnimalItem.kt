package gortea.jgmax.animalstorage.data.model

import gortea.jgmax.animalstorage.data.storage.entity.local.AnimalEntity
import java.io.Serializable

data class AnimalItem(
    val id: Long = 0,
    val name: String,
    val age: Int,
    val breed: String
) : Serializable

fun AnimalItem.toEntity(): AnimalEntity {
    return AnimalEntity(
        id = id,
        name = name,
        age = age,
        breed = breed
    )
}