package com.avalon.calizer.ui.accounts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.AccountsList
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.databinding.FragmentAccountsBinding
import com.avalon.calizer.ui.accounts.adapters.AccountsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class AccountsFragment : Fragment() {
    private lateinit var binding: FragmentAccountsBinding
    var csfr = ""
    var dsUserID = ""
    var igDId = ""
    var rur = ""
    var sessID = ""
    var shbid = ""
    var shbts = ""
    var mid = ""
    var allCookie = ""

    private val accountsAdapter by lazy { AccountsAdapter() }

    private val viewModel: AccountsViewModel by viewModels()


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
        viewModel.allAccounts.observe(viewLifecycleOwner, Observer {
            accountsAdapter.setData(it)
           // Log.d("list","${it.value}")
        })
        val newlist = ArrayList<AccountsList>()

        for (i in 0..1){
            val eelist = AccountsList(
                url = "https://pbs.twimg.com/profile_images/1092891292609249281/cJfKotNP_400x400.jpg",
                userName = "testtts1",
                pk = 110101L
            )

            newlist.add(eelist)
        }
        binding.cvAccountsAdd.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.addAccount(AccountsData(
                    profilePic = "https://thispersondoesnotexist.com/image",
                    userName = "denemeuyelik",
                    pk = Random.nextLong(1,500)
                ))
               viewModel.getAccountList()
            }

        }

    }

    private fun setupRecyclerview() {
        binding.rcAccountsList.adapter = accountsAdapter
        binding.rcAccountsList.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.VERTICAL,false)

    }

    private fun addAccountWeb(){
        binding.cvAccountsAdd.setOnClickListener {

        }
    }

}