package gortea.jgmax.animalstorage.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gortea.jgmax.animalstorage.R
import gortea.jgmax.animalstorage.data.model.AnimalItem
import gortea.jgmax.animalstorage.data.repository.AnimalRepository
import gortea.jgmax.animalstorage.data.repository.implementation.AnimalRepositoryImpl
import gortea.jgmax.animalstorage.data.storage.dao.AnimalDao
import gortea.jgmax.animalstorage.data.utils.types.SortType
import gortea.jgmax.animalstorage.ui.viewmodel.interfaces.AnimalDataToItemConverter
import gortea.jgmax.animalstorage.ui.viewmodel.interfaces.AnimalListCommunication
import gortea.jgmax.animalstorage.ui.viewmodel.states.AnimalListState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AnimalListViewModel : ViewModel(), AnimalListCommunication,
    AnimalDataToItemConverter {
    private val list: MutableList<AnimalItem?> = mutableListOf()
    private val state: MutableLiveData<AnimalListState> = MutableLiveData(AnimalListState.Initial)
    private val disposeBag = CompositeDisposable()
    private var sortType: SortType? = null
    private lateinit var repository: AnimalRepository

    private fun <T> setErrorState(message: T) {
        state.value = AnimalListState.UpdateError(message)
    }

    private fun setUpdateState(list: List<AnimalItem?>) {
        state.value = AnimalListState.Update(list.toList())
    }

    private fun setSuccessInsertState() {
        state.value = AnimalListState.SuccessInsert
    }

    override fun attachDao(dao: AnimalDao) {
        repository = AnimalRepositoryImpl(dao)
    }

    override fun addNewItem(animalItem: AnimalItem) {
        val disposable = repository.addItem(animalItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    setSuccessInsertState()
                    fetchItems(isForcing = true)
                },
                {
                    setErrorState(it.localizedMessage)
                }
            )
        disposeBag.add(disposable)
    }

    override fun deleteItem(animalItem: AnimalItem) {
        val disposable = repository.delete(animalItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (list.size == 2) {
                        list.clear()
                    } else {
                        list.remove(animalItem)
                    }
                    setUpdateState(list)
                },
                {
                    setErrorState(it.localizedMessage)
                }
            )
        disposeBag.add(disposable)
    }

    override fun updateItem(animalItem: AnimalItem) {
        val disposable = repository.update(animalItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    setSuccessInsertState()
                    val index = list.indexOfFirst { it?.id == animalItem.id }
                    list[index] = animalItem
                    setUpdateState(list)
                },
                {
                    setErrorState(it.localizedMessage)
                }
            )
        disposeBag.add(disposable)
    }

    fun fetchItems(sortBy: SortType? = sortType, isForcing: Boolean = false) {
        if (isForcing || sortBy != sortType || list.isEmpty()) {
            fetchItems(sortBy)
        } else {
            setUpdateState(list)
        }
    }

    override fun fetchItems(sortBy: SortType?) {
        val disposable = repository.getAll(sortBy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    sortType = sortBy
                    list.clear()
                    list.addAll(it + null)
                    setUpdateState(list)
                },
                {
                    setErrorState(it.localizedMessage)
                }
            )
        disposeBag.add(disposable)
    }

    override fun onCleared() {
        disposeBag.dispose()
        disposeBag.clear()
        super.onCleared()
    }

    fun addNewItem(name: String?, age: String?, breed: String?) {
        if (!checkFields(name, age, breed)) return
        addNewItem(
            dataToItem(
                requireNotNull(name),
                requireNotNull(age).toInt(),
                requireNotNull(breed)
            )
        )
    }

    fun updateItem(id: Long, name: String?, age: String?, breed: String?) {
        if (!checkFields(name, age, breed)) return
        updateItem(
            dataToItem(
                id = id,
                name = requireNotNull(name),
                age = requireNotNull(age).toInt(),
                breed = requireNotNull(breed)
            )
        )
    }

    private fun checkFields(name: String?, age: String?, breed: String?): Boolean {
        return (checkString(name, R.string.empty_name_field_error)
                && checkString(age, R.string.empty_age_field_error)
                && checkString(breed, R.string.empty_breed_field_error)
                && checkInt(requireNotNull(age), R.string.incorrect_age_field_error))
    }

    private fun checkString(str: String?, @StringRes errorMessage: Int): Boolean {
        return if (str.isNullOrEmpty()) {
            setErrorState(errorMessage)
            false
        } else {
            true
        }
    }

    private fun checkInt(strInt: String, @StringRes errorMessage: Int): Boolean {
        return if (strInt.toIntOrNull() == null) {
            setErrorState(errorMessage)
            false
        } else {
            true
        }
    }

    override fun dataToItem(name: String, age: Int, breed: String, id: Long): AnimalItem {
        return AnimalItem(
            id = id,
            name = name,
            age = age,
            breed = breed
        )
    }

    fun getAnimalList(): List<AnimalItem?> = list.toList()
    fun getState(): LiveData<AnimalListState> = state
}