package com.avalon.calizer.ui.main


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.avalon.calizer.R
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import com.avalon.calizer.databinding.ActivityMainBinding
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // private val viewModel: MainViewModel by viewModels()
    @Inject
    lateinit var viewModel: MainViewModel
    private var getFirstData = true

    @Inject
    lateinit var prefs: MySharedPreferences

    private val followDataList = ArrayList<FollowData>()


    private fun setupBottomNavigationMenu(navController: NavController) {
        binding.bottomNavigation.let {
            NavigationUI.setupWithNavController(it, navController)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item) || item.onNavDestinationSelected(
            findNavController(
                R.id.navHostFragment
            )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.itemIconTintList = null
        initData()
        initNavController()



        lifecycleScope.launchWhenStarted {
            viewModel.followData.collect {
                when (it) {
                    is MainViewModel.FollowDataFlow.GetFollowDataSync -> {
                        delay((500 + (0..250).random()).toLong())
                        it.follow.data?.let { users ->
                            viewModel.getUserFollowers(
                                userId = prefs.selectedAccount,
                                maxId = users.nextMaxId,
                                rnkToken = Utils.generateUUID(),
                                cookies = prefs.allCookie
                            )
                            addFollowList(users, prefs.followersType)

                        }

                    }
                    is MainViewModel.FollowDataFlow.GetFollowDataSuccess -> {
                        it.follow.data?.let { users ->
                            addFollowList(users, prefs.followersType)
                        }
                    }
                    is MainViewModel.FollowDataFlow.GetUserCookies -> {
                        it.accountsData.let { userInfo ->
                            viewModel.getUserFollowers(
                                userId = userInfo.dsUserID,
                                maxId = null,
                                cookies = userInfo.allCookie,
                                rnkToken = null
                            )
                        }
                    }
                    is MainViewModel.FollowDataFlow.SaveFollowers -> {
                        Log.d("StateSave", "Back")
                        /**
                         * eger type 0-1 ise followers
                         * eger type 2-3 ise following
                         * eger type 0-2 ise ekstra type degistir ve listenin uzerine ekle
                         * kaydet...
                         */
                        prefs.followersType.let { type ->
                            if (type == 0L) {
                                val list = followDataList
                                list.filter {data-> data.type == 0L }.forEach {last-> last.type = 1L }

                            }else{

                            }
                        }
                    }
                }
            }
        }


        //    if (Utils.getTimeStatus(PREFERENCES.followersUpdateDate)) {
        // PREFERENCES.followersUpdateDate = System.currentTimeMillis()


    }

    private fun initNavController() {
        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        setupBottomNavigationMenu(navController)
        binding.bottomNavigation.setOnNavigationItemReselectedListener {

        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.destination_profile || destination.id == R.id.destination_analyze || destination.id == R.id.destination_settings) {

                binding.bottomNavigation.visibility = View.VISIBLE
                if (getFirstData) {
                    getFirstData = false
                    viewModel.getUserDetails(prefs.selectedAccount)
                    val timeControl: Boolean = true
                    if (timeControl) {
                        viewModel.getUserFollowers(
                            userId = prefs.selectedAccount,
                            maxId = null,
                            rnkToken = null,
                            cookies = prefs.allCookie
                        )
                    }

                }

            } else {
                binding.bottomNavigation.visibility = View.GONE

            }

        }


    }

    fun initData() {

        lifecycleScope.launchWhenStarted {
            viewModel.userData.collect {
                when (it) {
                    is MainViewModel.UserDataFlow.GetUserDetails -> {


                    }
                    is MainViewModel.UserDataFlow.Empty -> {

                    }
                }
            }
        }

    }

    private fun followersData() {
        // if (PREFERENCES.firstLogin) {
        if (10 > 20) {
            Log.d("Response", "list-> " + followDataList.size.toString())
            // Log.d("Response", "listlast-> " + followersLastList.size.toString())


            viewModel.addFollowers(followDataList)


            //  PREFERENCES.firstLogin = false

        } else {
            // Log.d("Response", "listlast-> " + followersLastList.size.toString())
            // viewModel.addLastFollowers(followersLastList)

        }
    }

    private fun addFollowList(followData: ApiResponseUserFollow?, type: Long?) {

        if (followData != null) {


            for (data in followData.users) {

                val followUpdateList = FollowData()
                followUpdateList.pk = data.pk
                followUpdateList.type = type
                followUpdateList.fullName = data.fullName
                followUpdateList.profilePicUrl = data.profilePicUrl
                followUpdateList.hasAnonymousProfilePicture = data.hasAnonymousProfilePicture
                followUpdateList.isPrivate = data.isPrivate
                followUpdateList.isVerified = data.isVerified
                followUpdateList.username = data.username
                followDataList.add(followUpdateList)
            }

            Log.d("StateSave", "Add")

        }

    }

    fun getFollowersList(maxId: String? = null, userId: Long) {


        if (!maxId.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getUserFollowers(
                    userId = userId,
                    maxId = maxId,
                    rnkToken = Utils.generateUUID(),
                    prefs.allCookie
                )


            }

        } else {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getUserFollowers(
                    userId = userId,
                    cookies = prefs.allCookie,
                    maxId = null,
                    rnkToken = null
                )

            }

        }
    }


    fun getFollowingList(maxId: String? = null, userId: Long): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            delay((500 + (0..250).random()).toLong())
            if (!maxId.isNullOrEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getUserFollowers(
                        userId = userId,
                        maxId = maxId,
                        rnkToken = Utils.generateUUID(),
                        prefs.allCookie
                    )


                }

            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getUserFollowers(
                        userId = userId,
                        cookies = prefs.allCookie,
                        maxId = null,
                        rnkToken = null
                    )

                }

            }
        }


    }


}


