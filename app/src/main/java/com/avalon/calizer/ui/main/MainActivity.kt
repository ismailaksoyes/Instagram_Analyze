package com.avalon.calizer.ui.main


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.avalon.calizer.R
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.follow.FollowRequestParams
import com.avalon.calizer.data.local.follow.FollowingData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import com.avalon.calizer.databinding.ActivityMainBinding
import com.avalon.calizer.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel by viewModels()
    private var getFirstData = true

    @Inject
    lateinit var prefs: MySharedPreferences


    private val followersDataList = ArrayList<FollowersData>()
    private val followingDataList = ArrayList<FollowingData>()

    private lateinit var navController: NavController




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.itemIconTintList = null
        initNavController()
        observeFollowData()
        observeSaveFollowData()

    }

    private fun initNavController() {
        navController = binding.navHost.getFragment<NavHostFragment>().navController
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.destination_profile || destination.id == R.id.destination_analyze || destination.id == R.id.destination_settings) {

                binding.bottomNavigation.visibility = View.VISIBLE
                if (getFirstData) {
                    getFirstData = false
                    viewModel.startAnalyze()
                }

            } else {
                binding.bottomNavigation.visibility = View.GONE

            }

        }



    }

    private fun analyzeStartCheck(){
        val followersAnalyzeTime = Date(prefs.followersUpdateDate)
        val followingAnalyzeTime = Date(prefs.followingUpdateDate)
        if (Utils.getTimeDifference(followersAnalyzeTime)){
            lifecycleScope.launch(Dispatchers.IO) {
                getUserFollowers(FollowRequestParams())
            }
        }
        if (Utils.getTimeDifference(followingAnalyzeTime)){
            lifecycleScope.launch(Dispatchers.IO) {
                getUserFollowing(FollowRequestParams())
            }
        }

    }

    private fun observeSaveFollowData() {
        lifecycleScope.launchWhenCreated {
            viewModel.saveFollowData.collect { itSave ->
                when (itSave) {
                    is MainViewModel.FollowSaveState.SaveFollowers -> {
                        saveFollowersData(itSave.isSave)
                    }
                    is MainViewModel.FollowSaveState.SaveFollowing -> {
                        saveFollowingData(itSave.isSave)
                    }
                }
            }
        }
    }

    private suspend fun getUserFollowers(followRequestParams: FollowRequestParams) {
        delay((500 + (0..250).random()).toLong())
        viewModel.getUserFollowers(
            userId = prefs.selectedAccount,
            maxId = followRequestParams.maxId,
            rnkToken = followRequestParams.rnkToken,
            cookies = prefs.allCookie
        )
    }

    private suspend fun getUserFollowing(followRequestParams: FollowRequestParams) {
        delay((500 + (0..250).random()).toLong())
        viewModel.getUserFollowing(
            userId = prefs.selectedAccount,
            maxId = followRequestParams.maxId,
            rnkToken = followRequestParams.rnkToken,
            cookies = prefs.allCookie
        )
    }

    private fun observeFollowData() {
        lifecycleScope.launchWhenStarted {
            viewModel.followData.collect { itFollowData ->
                when (itFollowData) {
                    is MainViewModel.FollowDataFlow.GetFollowersDataSync -> {
                        itFollowData.follow.let { users ->
                            getUserFollowers(
                                FollowRequestParams(
                                    users.nextMaxId,
                                    Utils.generateUUID()
                                )
                            )
                            addFollowersList(users)
                        }

                    }
                    is MainViewModel.FollowDataFlow.GetFollowersDataSuccess -> {
                        lifecycleScope.launch(Dispatchers.IO) {
                            addFollowersList(itFollowData.follow)
                            viewModel.getSaveFollowersType()
                        }

                    }
                    is MainViewModel.FollowDataFlow.GetFollowingDataSync -> {
                        itFollowData.following.let { users ->
                            getUserFollowing(
                                FollowRequestParams(
                                    users.nextMaxId,
                                    Utils.generateUUID()
                                )
                            )
                            addFollowingList(users)

                        }

                    }
                    is MainViewModel.FollowDataFlow.GetFollowingDataSuccess -> {
                        lifecycleScope.launch(Dispatchers.IO) {
                            addFollowingList(itFollowData.following)
                            viewModel.getSaveFollowingType()
                        }

                    }
                    is MainViewModel.FollowDataFlow.StartAnalyze -> {
                        analyzeStartCheck()
                    }
                    is MainViewModel.FollowDataFlow.Error -> {

                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun saveFollowersData(isFirst: Boolean) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (isFirst){
                viewModel.addFollowersData(followersDataList)
                val oldFollowers = followersDataList.toOldFollowersData()
                viewModel.addOldFollowersData(oldFollowers)
                viewModel.updateFollowersSaveType()
                prefs.followersUpdateDate =System.currentTimeMillis()
            }else{
                viewModel.addFollowersData(followersDataList)
            }
        }

    }

    private fun saveFollowingData(isFirst: Boolean) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (isFirst){
                viewModel.addFollowingData(followingDataList)
                val oldFollowing = followingDataList.toOldFollowingData()
                viewModel.addOldFollowingData(oldFollowing)
                viewModel.updateFollowingSaveType()
                prefs.followingUpdateDate =System.currentTimeMillis()
            }else{
                viewModel.addFollowingData(followingDataList)
            }
        }


    }


    private fun addFollowersList(followData: ApiResponseUserFollow?) {
        lifecycleScope.launch(Dispatchers.IO) { }
        followData?.let { itFollowers ->
            val analyzeUserId = prefs.selectedAccount
            val followersList = itFollowers.toFollowersData(analyzeUserId)
            followersDataList.addAll(followersList)
        }
    }

    private fun addFollowingList(followData: ApiResponseUserFollow?) {
        lifecycleScope.launch(Dispatchers.IO) { }
        followData?.let { itFollowing ->
            val analyzeUserId = prefs.selectedAccount
            val followingList = itFollowing.toFollowingData(analyzeUserId)
            followingDataList.addAll(followingList)
        }
    }


}


