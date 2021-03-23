package com.avalon.calizer.ui.accounts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AccountsViewModel @ViewModelInject constructor(
    private val roomRepository: RoomRepository,
    private val repository: Repository
) : ViewModel() {
    val allAccounts: MutableLiveData<List<AccountsData>> = MutableLiveData()

    private val _res = MutableLiveData<Resource<ApiResponseReelsTray>>()
    val res :LiveData<Resource<ApiResponseReelsTray>>
    get() = _res


     fun getReelsTray(cookies:String) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        repository.getReelsTray(cookies).let {
            if (it.isSuccessful){
                _res.postValue(Resource.success(it.body()))
            }else{
                _res.postValue(Resource.error(it.errorBody().toString(),null))
            }
        }

    }

    fun getAccountList() {
        viewModelScope.launch(Dispatchers.IO) {
            allAccounts.postValue(roomRepository.getAccounts())
        }
    }

    suspend fun addAccount(accountsData: AccountsData) {
        roomRepository.addAccount(accountsData)
    }
}