package com.avalon.calizer.ui.accounts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.databinding.FragmentAccountsBinding
import com.avalon.calizer.ui.accounts.adapters.AccountsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountsFragment : Fragment() {
    private lateinit var binding: FragmentAccountsBinding


    private val accountsAdapter by lazy { AccountsAdapter() }

    private val viewModel: AccountsViewModel by viewModels()
    private var isUpdate: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerview()
        viewModel.getAccountList()
        lifecycleScope.launchWhenStarted {
            viewModel.allAccounts.collect {
                when (it) {
                    is AccountsViewModel.LastAccountsState.Loading -> {
                        Log.d("StateTest", "Loading")
                    }
                    is AccountsViewModel.LastAccountsState.Success -> {
                        Log.d("StateTest", "Success")
                       // accountsAdapter.setData(it.allAccounts)
                        setAdapterData(it.allAccounts)

                    }
                    is AccountsViewModel.LastAccountsState.Error -> {
                        Log.d("StateTest", "Error")
                    }
                    is AccountsViewModel.LastAccountsState.UpdateData->{
                        Log.d("StateTest", "Data Update")
                        viewModel.getLastAccountList()
                    }
                    is AccountsViewModel.LastAccountsState.UserDetails->{
                        Log.d("StateTest", "User Details  ${it.userDetails.data?.user?.pk}")
                        updateAccount(
                            profile_Pic = it.userDetails.data?.user?.profilePicUrl,
                          user_name = it.userDetails.data?.user?.username.toString(),
                           ds_userId = it.userDetails.data?.user?.pk?.toString()
                       )
                    }
                    is AccountsViewModel.LastAccountsState.OldData -> {
                        Log.d("StateTest", "Old Data List -> ${it.allAccounts}")

                        for (data in it.allAccounts) {
                            viewModel.getUserDetails(
                                cookies = data.allCookie,
                                userId = data.dsUserID
                            )
                         }

                    }
                    else -> {
                        Log.d("StateTest", "Empty")
                    }
                }
            }
        }

//        viewModel.allAccounts.observe(viewLifecycleOwner, Observer {
//
//                for (data in it) {
//                    viewModel.getUserDetails(
//                        cookies = data.allCookie,
//                        userId = data.dsUserID
//                    )
//                }
//
//                accountsAdapter.setData(it)
//
//
//            // Log.d("list","${it.value}")
//
//        })


        binding.cvAccountsAdd.setOnClickListener {

            it.findNavController().navigate(R.id.action_destination_accounts_to_webLoginFragment)

        }

    }
    suspend fun setAdapterData(accountsData: List<AccountsData> ){
        Log.d("StateTest",".........")
        lifecycleScope.launchWhenStarted {
            accountsAdapter.setData(accountsData)
        }
        
    }


    fun updateAccount(profile_Pic: String?, user_name: String?, ds_userId: String?) {
        CoroutineScope(Dispatchers.IO).launch {
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