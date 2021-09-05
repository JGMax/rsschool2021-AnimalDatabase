package gortea.jgmax.animalstorage.data.utils.types

import gortea.jgmax.animalstorage.data.storage.database.local.ANIMAL_AGE_COLUMN_NAME
import gortea.jgmax.animalstorage.data.storage.database.local.ANIMAL_BREED_COLUMN_NAME
import gortea.jgmax.animalstorage.data.storage.database.local.ANIMAL_NAME_COLUMN_NAME

enum class SortType(val value: String) {
    NAME(ANIMAL_NAME_COLUMN_NAME),
    AGE(ANIMAL_AGE_COLUMN_NAME),
    BREED(ANIMAL_BREED_COLUMN_NAME)
}