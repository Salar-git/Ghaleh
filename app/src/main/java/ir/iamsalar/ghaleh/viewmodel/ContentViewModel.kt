package ir.iamsalar.ghaleh.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ir.iamsalar.ghaleh.App
import ir.iamsalar.ghaleh.network.Api
import ir.iamsalar.ghaleh.network.model.ContentResponseModel
import ir.iamsalar.ghaleh.network.model.ContetnFilterModel
import ir.iamsalar.ghaleh.network.model.LikeResponseModel
import ir.iamsalar.ghaleh.prefs
import ir.iamsalar.ghaleh.utilities.internetCheck
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class ContentViewModel() : ViewModel() {
    private var _contetnResponse = MutableLiveData<List<ContentResponseModel>?>()
    val contetnResponse: MutableLiveData<List<ContentResponseModel>?> get() = _contetnResponse
    private var _likeResponse = MutableLiveData<LikeResponseModel?>()
    val likeResponse: MutableLiveData<LikeResponseModel?> get() = _likeResponse
    var currentPosition=0

    val categories: MutableList<String> = mutableListOf()

    init {

    }

    fun clearCategories() {
        categories.clear()
    }

    fun addCategory(item: String) {
        categories.add(item)
    }

    fun fetchContents() {
        viewModelScope.launch {
            if (internetCheck(App.instance.applicationContext)) {
                try {
                    val result = Api.retrofitService.getContent(
                        prefs.getString("token")!!,
                        ContetnFilterModel(categories)
                    )
                    when (result.code()) {
                        HttpURLConnection.HTTP_OK -> {
                            _contetnResponse.postValue(result.body())

                        }
                        else -> {
                            _contetnResponse.postValue(null)
                        }
                    }
                } catch (e: Exception) {
                    _contetnResponse.postValue(null)
                }


            }
        }
    }

    fun like(id:Int) {
        viewModelScope.launch {
            if (internetCheck(App.instance.applicationContext)) {
                try {
                    val result = Api.retrofitService.like(
                        prefs.getString("token")!!,
                        id
                    )
                    when (result.code()) {
                        HttpURLConnection.HTTP_OK -> {
                            _likeResponse.postValue(result.body())

                        }
                        else -> {
                            _likeResponse.postValue(null)
                        }
                    }
                } catch (e: Exception) {
                    _likeResponse.postValue(null)
                }


            }
        }
    }

}


class ContentViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContentViewModel::class.java)) {
            return ContentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}