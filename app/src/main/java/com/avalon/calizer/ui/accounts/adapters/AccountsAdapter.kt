package com.avalon.calizer.ui.accounts.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.R
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.AccountsList
import com.avalon.calizer.databinding.AccountsItemBinding
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.loadPPUrl
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class AccountsAdapter : RecyclerView.Adapter<AccountsAdapter.MainViewHolder>() {
    private var _accountsList = emptyList<AccountsData>()

    @Inject
    lateinit var prefs: MySharedPreferences


    class MainViewHolder(var binding: AccountsItemBinding,var prefs:MySharedPreferences):RecyclerView.ViewHolder(binding.root){
        fun bind(accountsList: AccountsData){
            binding.ivProfileImage.loadPPUrl(accountsList.profilePic)
            binding.tvAccountsUsername.text = accountsList.userName
            binding.cvAccounts.setOnClickListener {

                prefs.allCookie = accountsList.allCookie
prefs.userName = accountsList.userName
                prefs.selectedAccount = accountsList.dsUserID


             //   it.findNavController().navigate(R.id.action_destination_accounts_to_destination_profile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = AccountsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainViewHolder(binding,prefs)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
       holder.bind(_accountsList[position])
    }

    override fun getItemCount(): Int {
        return _accountsList.size
    }
    fun setData(accountsList:List<AccountsData> ){
       _accountsList = accountsList
        notifyDataSetChanged()

    }
}