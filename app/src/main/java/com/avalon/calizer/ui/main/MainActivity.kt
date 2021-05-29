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
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
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
                            addFollowList(users, FollowSaveType.FOLLOWERS_LAST.type)

                        }

                    }
                    is MainViewModel.FollowDataFlow.GetFollowDataSuccess -> {
                        delay((5005 + (0..250).random()).toLong())
                        it.follow.data?.let { users ->
                            addFollowList(users, FollowSaveType.FOLLOWERS_LAST.type)
                        }
                        viewModel.getUserFollowing(
                            userId = prefs.selectedAccount,
                            maxId = null,
                            rnkToken = null,
                            cookies = prefs.allCookie
                        )
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
                            addFollowList(users, FollowSaveType.FOLLOWING_LAST.type)

                        }

                    }
                    is MainViewModel.FollowDataFlow.GetFollowingDataSuccess -> {
                        delay((500 + (0..250).random()).toLong())
                        it.following.data?.let { users ->
                            addFollowList(users, FollowSaveType.FOLLOWING_LAST.type)
                        }
                        viewModel.stateSaveLaunch(prefs.selectedAccount)

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

                        it.userInfo.followersType.let { type ->
                            Log.d("hash",type.toString())
                            if (type == FollowSaveType.FOLLOWERS_FIRST.type) {
                                val cacheList = ArrayList<FollowData>()
                                followDataList.filter { data-> data.type == FollowSaveType.FOLLOWERS_LAST.type }.forEach { item->
                                    cacheList.add(
                                        FollowData(
                                            type = 0L,
                                            analyzeUserId = item.analyzeUserId,
                                            dsUserID = item.dsUserID,
                                            fullName = item.fullName,
                                            hasAnonymousProfilePicture = item.hasAnonymousProfilePicture,
                                            isPrivate = item.isPrivate,
                                            isVerified = item.isVerified,
                                            latestReelMedia = item.latestReelMedia,
                                            profilePicUrl = item.profilePicUrl,
                                            profilePicId = item.profilePicId,
                                            username = item.username

                                        )
                                    )

                                }
                                followDataList.addAll(cacheList)

                            }
                        }

                        it.userInfo.followingType.let { type ->
                            if (type == FollowSaveType.FOLLOWING_FIRST.type) {
                                val cacheList = ArrayList<FollowData>()
                                followDataList.filter { data-> data.type == FollowSaveType.FOLLOWING_LAST.type }.forEach { item->
                                    cacheList.add(
                                        FollowData(
                                            type = 2L,
                                            analyzeUserId = item.analyzeUserId,
                                            dsUserID = item.dsUserID,
                                            fullName = item.fullName,
                                            hasAnonymousProfilePicture = item.hasAnonymousProfilePicture,
                                            isPrivate = item.isPrivate,
                                            isVerified = item.isVerified,
                                            latestReelMedia = item.latestReelMedia,
                                            profilePicUrl = item.profilePicUrl,
                                            profilePicId = item.profilePicId,
                                            username = item.username

                                        )
                                    )

                                }
                                followDataList.addAll(cacheList)

                            }
                        }

                        it.userInfo.followingType?.let { it1 ->
                            it.userInfo.followersType?.let { it2 ->
                                saveFollowRoom(
                                    it2,
                                    it1
                                )
                            }
                        }
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

                    val analyzeTime: Date = Date(prefs.followUpdateDate)
                    if (Utils.getTimeDifference(analyzeTime)) {
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

    private fun saveFollowRoom(followersType:Long, followingType:Long) {
        if(followersType==FollowSaveType.FOLLOWERS_FIRST.type&&followingType==FollowSaveType.FOLLOWING_FIRST.type){
            lifecycleScope.launchWhenStarted {
                viewModel.updateUserType(
                    userId = prefs.selectedAccount,
                    followersType = FollowSaveType.FOLLOWERS_LAST.type,
                    followingType = FollowSaveType.FOLLOWING_LAST.type
                )

            }
        }

        lifecycleScope.launchWhenStarted {
            delay(1000)
            for (sizeTR in 0..3) {
                followDataList.filter { data ->
                    data.type == sizeTR.toLong()
                }.let { sizeC ->
                    Log.d("followSaveData", "$sizeTR -> " + sizeC.size.toString())
                }

            }
            viewModel.addFollow(followDataList,prefs.selectedAccount)
            prefs.followUpdateDate = System.currentTimeMillis()
        }


    }


    private suspend fun addFollowList(followData: ApiResponseUserFollow?, type: Long?) {

        if (followData != null) {
            val analyzeUserId = prefs.selectedAccount
            for (data in followData.users) {
                val followUpdateList = FollowData()
                followUpdateList.dsUserID = data.pk
                followUpdateList.type = type
                followUpdateList.analyzeUserId = analyzeUserId
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


