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
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class AccountsAdapter(var selectedUserInterface: SelectedUserInterface) :
    RecyclerView.Adapter<AccountsAdapter.MainViewHolder>() {
    private var _accountsList = emptyList<AccountsData>()

    @Inject
    lateinit var prefs: MySharedPreferences


    class MainViewHolder(
        var binding: AccountsItemBinding,
        val selectedUserInterface: SelectedUserInterface
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(accountsList: AccountsData) {
            Log.d("UserId","${accountsList.dsUserID}")
            if(accountsList.profilePic.isEmpty() && accountsList.userName.isEmpty()){
                binding.ivProfileImage.loadPPUrl("https://slangit.com/img/slang/pp_4215.jpg")
                binding.tvAccountsUsername.text = "bir hata olustu!"
            }else{
                binding.ivProfileImage.loadPPUrl(accountsList.profilePic)
                binding.tvAccountsUsername.text = accountsList.userName
            }

            binding.cvAccounts.setOnClickListener {
                val sendData = AccountsData(
                    userName = accountsList.userName,
                    allCookie = accountsList.allCookie,
                    dsUserID = accountsList.dsUserID
                )
                selectedUserInterface.getData(sendData)

                 // it.findNavController().navigate(R.id.action_destination_analyze_to_allFollowersFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            AccountsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding, selectedUserInterface)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(_accountsList[position])
    }

    override fun getItemCount(): Int {
        return _accountsList.size
    }

    fun setData(accountsList: List<AccountsData>) {
        _accountsList = accountsList
        notifyDataSetChanged()

    }
}