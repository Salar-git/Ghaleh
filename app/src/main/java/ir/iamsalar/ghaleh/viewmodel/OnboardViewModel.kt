package ir.iamsalar.ghaleh.viewmodel

import android.util.Log
import androidx.lifecycle.*
import ir.iamsalar.ghaleh.App
import ir.iamsalar.ghaleh.network.Api
import ir.iamsalar.ghaleh.network.model.LogInModel
import ir.iamsalar.ghaleh.network.model.LogInResponseModel
import ir.iamsalar.ghaleh.utilities.internetCheck
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class OnboardViewModel() : ViewModel() {
    private var _loginResponse = MutableLiveData<LogInResponseModel?>()
    val loginResponse: MutableLiveData<LogInResponseModel?> get() = _loginResponse

    init {

    }

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            if (internetCheck(App.instance.applicationContext)) {
                try {
                    val result = Api.retrofitService.login(LogInModel(userName, password))
                    when (result.code()) {
                        HttpURLConnection.HTTP_OK -> {
                            _loginResponse.postValue(result.body())

                        }
                        HttpURLConnection.HTTP_BAD_REQUEST -> {
                            _loginResponse.postValue(null)
                        }
                    }
                } catch (e: Exception) {

                    _loginResponse.postValue(null)
                }


            }
        }
    }

}


class OnboardViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnboardViewModel::class.java)) {
            return OnboardViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}