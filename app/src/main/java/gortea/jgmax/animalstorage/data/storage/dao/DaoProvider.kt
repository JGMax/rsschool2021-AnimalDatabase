package gortea.jgmax.animalstorage.data.storage.dao

interface DaoProvider {
    var roomDAO: AnimalDao?
    var cursorDAO: AnimalDao?
}