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
import com.avalon.calizer.utils.loadPPUrl

class AccountsAdapter(): RecyclerView.Adapter<AccountsAdapter.MainViewHolder>() {
    private var _accountsList = emptyList<AccountsData>()


    class MainViewHolder(var binding: AccountsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(accountsList: AccountsData){
            binding.ivProfileImage.loadPPUrl(accountsList.profilePic)
            binding.tvAccountsUsername.text = accountsList.userName
            binding.cvAccounts.setOnClickListener {
              Log.d("Response",accountsList.pk.toString())
                it.findNavController().navigate(R.id.action_destination_accounts_to_destination_profile)
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
    fun setData(accountsList:List<AccountsData> ){
       _accountsList = accountsList
        notifyDataSetChanged()

    }
}