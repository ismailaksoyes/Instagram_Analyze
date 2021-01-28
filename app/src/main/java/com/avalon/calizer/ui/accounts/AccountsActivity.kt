
package com.avalon.calizer.ui.accounts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.data.local.AccountsList
import com.avalon.calizer.databinding.ActivityAccountsBinding
import com.avalon.calizer.ui.accounts.adapters.AccountsAdapter

class AccountsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountsBinding
    private val accountsAdapter by lazy { AccountsAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountsBinding.inflate(layoutInflater)
        setContentView(binding.root)
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


       // accountsAdapter.setData(newlist)

    }
    private fun setupRecyclerview() {
        binding.rcAccountsList.adapter = accountsAdapter
        binding.rcAccountsList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

    }
}