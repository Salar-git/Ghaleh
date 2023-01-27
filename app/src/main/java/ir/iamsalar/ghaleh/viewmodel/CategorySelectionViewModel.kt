package ir.iamsalar.ghaleh.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ir.iamsalar.ghaleh.App
import ir.iamsalar.ghaleh.data.db.DB
import ir.iamsalar.ghaleh.data.db.entities.category.Category
import ir.iamsalar.ghaleh.data.repository.CategoryRepository
import ir.iamsalar.ghaleh.network.Api
import ir.iamsalar.ghaleh.prefs
import ir.iamsalar.ghaleh.utilities.internetCheck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class CategorySelectionViewModel(private val repository: CategoryRepository) : ViewModel() {

    private var _categoriesResponse = MutableLiveData<List<String>?>()
    val categoriesResponse: MutableLiveData<List<String>?> get() = _categoriesResponse

    var myCategories: List<Category> = mutableListOf()

    init {
        fetchAllCategoriesFromServer()
    }


    fun getAllCategory(): Flow<List<Category>> = repository.getAll
    fun addCategory(name: String) {
        var add = true
        myCategories.forEach {
            if (it.name.equals(name)) {
                add = false
            }
        }
        if (add) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.add(Category(0, name))
            }
        }

    }

    fun removeCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.remove(category)
        }
    }

    fun fetchAllCategoriesFromServer() {
        viewModelScope.launch {
            if (internetCheck(App.instance.applicationContext)) {
                try {
                    val result = Api.retrofitService.getCategories(prefs.getString("token")!!)
                    when (result.code()) {
                        HttpURLConnection.HTTP_OK -> {
                            _categoriesResponse.postValue(result.body())

                        }
                        HttpURLConnection.HTTP_BAD_REQUEST -> {
                            _categoriesResponse.postValue(null)
                        }
                        HttpURLConnection.HTTP_UNAUTHORIZED -> {
                            _categoriesResponse.postValue(null)
                        }
                    }
                } catch (e: Exception) {
                    _categoriesResponse.postValue(null)
                }


            }
        }
    }
}

class CategorySelectionViewModelFactory(private val db: DB) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategorySelectionViewModel::class.java)) {
            return CategorySelectionViewModel(CategoryRepository(db)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}