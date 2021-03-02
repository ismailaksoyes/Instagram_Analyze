package com.avalon.calizer.ui.accounts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.data.local.AccountsList
import com.avalon.calizer.databinding.FragmentAccountsBinding
import com.avalon.calizer.ui.accounts.adapters.AccountsAdapter


class AccountsFragment : Fragment() {
    private lateinit var binding: FragmentAccountsBinding
    private val accountsAdapter by lazy { AccountsAdapter() }


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
        val newlist = ArrayList<AccountsList>()

        for (i in 0..1){
            val eelist = AccountsList(
                url = "https://pbs.twimg.com/profile_images/1092891292609249281/cJfKotNP_400x400.jpg",
                userName = "testtts1",
                pk = 110101L
            )

            newlist.add(eelist)
        }
        accountsAdapter.setData(newlist)
    }

    private fun setupRecyclerview() {
        binding.rcAccountsList.adapter = accountsAdapter
        binding.rcAccountsList.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.VERTICAL,false)

    }


}