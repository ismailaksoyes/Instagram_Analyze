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
import com.avalon.calizer.utils.FollowSaveType
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
                        delay((5005 + (0..250).random()).toLong())
                        it.follow.data?.let { users ->
                            addFollowList(users, prefs.followersType)
                        }
                        viewModel.getUserFollowing(
                            userId = prefs.selectedAccount,
                            maxId = null,
                            rnkToken = null,
                            cookies = prefs.allCookie
                        )
                        Log.d("StateSave", "getUserFollowing")
                    }
                    is MainViewModel.FollowDataFlow.GetFollowingDataSync -> {
                        delay((500 + (0..250).random()).toLong())
                        it.following.data?.let { users ->
                            viewModel.getUserFollowing(
                                userId = prefs.selectedAccount,
                                maxId = users.nextMaxId,
                                rnkToken = Utils.generateUUID(),
                                cookies = prefs.allCookie
                            )
                            addFollowList(users, prefs.followingType)

                        }

                    }
                    is MainViewModel.FollowDataFlow.GetFollowingDataSuccess -> {
                        delay((500 + (0..250).random()).toLong())
                        Log.d("StateSave", "FollowingOK")
                        it.following.data?.let { users ->
                            addFollowList(users, prefs.followingType)
                        }
                        viewModel.stateSaveLaunch()

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
                    is MainViewModel.FollowDataFlow.SaveFollow -> {
                        Log.d("StateSave", "Back")
                        /**
                         * eger type 0-1 ise followers
                         * eger type 2-3 ise following
                         * eger type 0-2 ise ekstra type degistir ve listenin uzerine ekle
                         * kaydet...
                         */
                        Log.d("NewList", "okSave ${prefs.followersType} ${prefs.followingType}")

                        prefs.followersType.let { type ->

                            if (type != FollowSaveType.FOLLOWERS_FIRST.type) {
                                val listFollowers =
                                    followDataList.map { followData -> followData.copy() }
                                listFollowers.filter { data -> data.type == FollowSaveType.FOLLOWERS_FIRST.type }
                                    .forEach { last ->
                                        last.type = FollowSaveType.FOLLOWERS_LAST.type
                                    }
                                followDataList.addAll(listFollowers)
                                listFollowers.forEach { list ->
                                    Log.d("NewList", "1-> ${list.type}")
                                }
                                // prefs.followersType = FollowSaveType.FOLLOWERS_LAST.type

                            }
                        }
                        //prefs.followersType = 2
                        prefs.followingType.let { type ->

                            if (type == FollowSaveType.FOLLOWING_FIRST.type) {
                                //  val list = followDataList.map { followData -> followData.copy() }
                                val cacheFollowersList = followDataList.map { fol->
                                    fol.copy(type=3)
                                }

                                followDataList.addAll(cacheFollowersList)

                                // prefs.followersType = FollowSaveType.FOLLOWING_LAST.type

                            }
                        }

                        saveFollowRoom(followDataList)
                    }
                    is MainViewModel.FollowDataFlow.Error -> {
                        Log.d("errortest", it.toString())
                    }
                    else -> {
                        Log.d("errortest", "nulllll")
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
                        lifecycleScope.launchWhenStarted {
                            viewModel.getUserFollowers(
                                userId = prefs.selectedAccount,
                                maxId = null,
                                rnkToken = null,
                                cookies = prefs.allCookie
                            )
                        }

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

    fun saveFollowRoom(followData: ArrayList<FollowData>) {
        //viewModel.addFollow(followData)

        lifecycleScope.launchWhenStarted {
            delay(1000)
            for (sizeTR in 0..3) {
                followDataList.filter { data ->
                    data.type == sizeTR.toLong()
                }.let { sizeC ->
                    Log.d("followSaveData", "$sizeTR -> " + sizeC.size.toString())
                }
            }
        }


    }

    private fun followersData() {
        // if (PREFERENCES.firstLogin) {
        if (10 > 20) {
            Log.d("Response", "list-> " + followDataList.size.toString())
            // Log.d("Response", "listlast-> " + followersLastList.size.toString())


            //  viewModel.addFollowers(followDataList)


            //  PREFERENCES.firstLogin = false

        } else {
            // Log.d("Response", "listlast-> " + followersLastList.size.toString())
            // viewModel.addLastFollowers(followersLastList)

        }
    }

    private suspend fun addFollowList(followData: ApiResponseUserFollow?, type: Long?) {

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


}


