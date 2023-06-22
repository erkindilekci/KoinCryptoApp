package com.erkindilekci.koincryptoapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erkindilekci.koincryptoapp.data.local.dto.CryptoDto
import com.erkindilekci.koincryptoapp.domain.repository.CryptoRepository
import com.erkindilekci.koincryptoapp.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//class ListViewModel(
//    private val repository: CryptoRepository
//) : ViewModel() {
//
//    val cryptoList = MutableLiveData<Resource<List<CryptoDto>>>()
//    val showError = MutableLiveData<Resource<Boolean>>()
//    val isLoading = MutableLiveData<Resource<Boolean>>()
//
//
//    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        showError.value =
//            Resource.error(throwable.localizedMessage ?: "Unknown error occurred!", data = true)
//    }
//
//    fun getDataFromApi() {
//        isLoading.value = Resource.loading(true)
//
//        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
//            val resource = repository.getCryptos()
//
//            withContext(Dispatchers.Main) {
//                resource.data?.let {
//                    cryptoList.value = resource
//                }
//            }
//        }
//    }
//}


class ListViewModel(
    private val cryptoDownloadRepository : CryptoRepository
) : ViewModel() {

    val cryptoList = MutableLiveData<Resource<List<CryptoDto>>>()
    val showError = MutableLiveData<Resource<Boolean>>()
    val isLoading = MutableLiveData<Resource<Boolean>>()
    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        showError.value = Resource.error(throwable.localizedMessage ?: "error!",data = true)
    }




    fun getDataFromApi() {
        isLoading.value = Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = cryptoDownloadRepository.getCryptos()
            withContext(Dispatchers.Main) {
                resource.data?.let {
                    isLoading.value = Resource.loading(false)
                    showError.value = Resource.error("",data = false)
                    cryptoList.value = resource
                }
            }
        }

        /*
       val BASE_URL = "https://raw.githubusercontent.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)



        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cryptoError.value = false
                    cryptoLoading.value = false
                    response.body()?.let {
                        cryptoList.value = it
                        }
                    }
                }
            }
        }

        */
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
