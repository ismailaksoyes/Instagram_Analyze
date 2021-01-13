package com.avalon.avalon.ui.accounts.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.avalon.avalon.data.local.AccountsList
import com.avalon.avalon.databinding.AccountsItemBinding
import com.avalon.avalon.utils.loadPPUrl

class AccountsAdapter(): RecyclerView.Adapter<AccountsAdapter.MainViewHolder>() {
    private var _accountsList = emptyList<AccountsList>()


    class MainViewHolder(var binding: AccountsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(accountsList: AccountsList){
            binding.ivProfileImage.loadPPUrl(accountsList.url)
            binding.tvAccountsUsername.text = accountsList.userName
            binding.cvAccounts.setOnClickListener {
              Log.d("Response",accountsList.pk.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = AccountsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
       holder.bind(_accountsList[position])
    }

    override fun getItemCount(): Int {
        return _accountsList.size
    }
    fun setData(accountsList:List<AccountsList> ){
       _accountsList = accountsList
        notifyDataSetChanged()

    }
}