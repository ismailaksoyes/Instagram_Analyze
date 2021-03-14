package com.avalon.calizer.ui.accounts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.calizer.data.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AccountsViewModel @ViewModelInject constructor(private val roomRepository: RoomRepository):ViewModel() {
    val allAccounts: MutableLiveData<LiveData<List<AccountsData>>> = MutableLiveData()

    init {
        allAccounts.value = roomRepository.getAccounts()
    }
    fun getAccountList(){
        viewModelScope.launch(Dispatchers.IO) {
            allAccounts.value = roomRepository.getAccounts()
        }
    }

    suspend fun addAccount(accountsData: AccountsData){
        roomRepository.addAccount(accountsData)
    }
}