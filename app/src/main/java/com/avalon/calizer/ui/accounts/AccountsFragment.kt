package com.avalon.calizer.ui.accounts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.databinding.FragmentAccountsBinding
import com.avalon.calizer.ui.accounts.adapters.AccountsAdapter
import com.avalon.calizer.ui.accounts.adapters.SelectedUserInterface
import com.avalon.calizer.utils.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountsFragment  : Fragment() {
    private lateinit var binding: FragmentAccountsBinding

    @Inject
    lateinit var prefs: MySharedPreferences

    @Inject lateinit var viewModel: AccountsViewModel

    private val accountsAdapter by lazy {
        AccountsAdapter(object : SelectedUserInterface {
            override fun getData(accountsData: AccountsData) {
                super.getData(accountsData)
                nextMain(accountsData)
            }
        })
    }


    fun nextMain(accountsData: AccountsData) {
        prefs.userName = accountsData.userName
        prefs.selectedAccount = accountsData.dsUserID
        prefs.allCookie = accountsData.allCookie

        findNavController().navigate(R.id.action_destination_accounts_to_destination_profile)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        observeAccountData()

        lifecycleScope.launchWhenCreated {
            viewModel.getAccountList()
        }

        binding.cvAccountsAdd.setOnClickListener {

            it.findNavController().navigate(R.id.action_destination_accounts_to_webLoginFragment)

        }

    }

    private fun setAdapterData(accountsData: List<AccountsData>) {
        lifecycleScope.launchWhenStarted {
            accountsAdapter.setData(accountsData)
        }

    }

    private fun observeAccountData(){
        lifecycleScope.launchWhenStarted {
            viewModel.allAccounts.collect {
                when (it) {
                    is AccountsViewModel.LastAccountsState.Loading -> {
                    }
                    is AccountsViewModel.LastAccountsState.Success -> {
                        binding.shmView.stopShimmer()
                        binding.shmView.visibility = View.GONE
                        setAdapterData(it.allAccounts)

                    }
                    is AccountsViewModel.LastAccountsState.Error -> {
                    }
                    is AccountsViewModel.LastAccountsState.UpdateData -> {
                        viewModel.getLastAccountList()
                    }
                    is AccountsViewModel.LastAccountsState.UserDetails -> {
                        it.userDetails.user.let { itUserData->
                            val accountInfo = AccountsInfoData(
                                profilePic = itUserData.profilePicUrl,
                                userName =  itUserData.username.toString(),
                                userId =  itUserData.pk,
                                followers = itUserData.followerCount.toLong(),
                                following = itUserData.followingCount.toLong(),
                                posts = itUserData.mediaCount.toLong()
                            )
                            updateAccount(
                                profile_Pic = itUserData.profilePicUrl,
                                user_name = itUserData.username.toString(),
                                ds_userId = itUserData.pk.toString()
                            )
                        }

                    }
                    is AccountsViewModel.LastAccountsState.OldData -> {
                        binding.shmView.visibility = View.VISIBLE
                        binding.shmView.startShimmer()
                        for (data in it.allAccounts) {
                            viewModel.getUserDetails(
                                cookies = data.allCookie,
                                userId = data.dsUserID
                            )
                        }

                    }
                    else -> {
                    }
                }
            }
        }
    }


    private fun updateAccount(profile_Pic: String?, user_name: String?, ds_userId: String?) {
        lifecycleScope.launchWhenCreated {
            viewModel.updateAccount(profile_Pic, user_name, ds_userId)
        }
    }

    private fun setupRecyclerview() {
        binding.rcAccountsList.adapter = accountsAdapter
        binding.rcAccountsList.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

    }

    private fun addAccountWeb() {
        binding.cvAccountsAdd.setOnClickListener {

        }
    }

}