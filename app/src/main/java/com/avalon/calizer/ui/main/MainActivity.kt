package com.avalon.calizer.ui.main


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.avalon.calizer.R
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.calizer.databinding.ActivityMainBinding
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
   // private val viewModel: MainViewModel by viewModels()
    @Inject
    lateinit var viewModel:MainViewModel
    private var getFirstData = true

    @Inject
    lateinit var prefs: MySharedPreferences

    private val followersList = ArrayList<FollowData>()
    private var followersHashMap = HashMap<String, String>()

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
        initData()
        binding.bottomNavigation.itemIconTintList = null


        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        setupBottomNavigationMenu(navController)
        binding.bottomNavigation.setOnNavigationItemReselectedListener {

        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.destination_profile ||destination.id == R.id.destination_analyze ||destination.id == R.id.destination_settings){

                binding.bottomNavigation.visibility = View.VISIBLE
                if (getFirstData){
                    getFirstData= false
                    viewModel.getUserDetails(prefs.selectedAccount)
                    val timeControl:Boolean = true
                    if(timeControl){
                        viewModel.getUserFollowers(
                            userId = prefs.selectedAccount,
                            maxId = null,
                            rnkToken = null,
                            cookies = prefs.allCookie
                        )
                    }

                }

            }else{
                binding.bottomNavigation.visibility = View.GONE

            }

        }

        lifecycleScope.launchWhenStarted {
            viewModel.followersData.collect {
                when(it){
                    is MainViewModel.FollowersDataFlow.GetFollowersDataSync -> {
                        delay((5000 + (0..250).random()).toLong())
                        it.followers.data?.let { users ->
                            viewModel.getUserFollowers(
                                userId = prefs.selectedAccount,
                                maxId = users.nextMaxId,
                                rnkToken = Utils.generateUUID(),
                                cookies = prefs.allCookie
                            )
                            addFollowersList(users)

                        }

                    }
                    is MainViewModel.FollowersDataFlow.GetFollowersDataSuccess -> {
                        it.followers.data?.let { users ->
                            addFollowersList(users)
                        }
                    }
                    is MainViewModel.FollowersDataFlow.GetUserCookies -> {
                        it.accountsData.let { userInfo ->
                            viewModel.getUserFollowers(
                                userId = userInfo.dsUserID,
                                maxId = null,
                                cookies = userInfo.allCookie,
                                rnkToken = null
                            )
                        }
                    }
                }
            }
        }


        //    if (Utils.getTimeStatus(PREFERENCES.followersUpdateDate)) {
        // PREFERENCES.followersUpdateDate = System.currentTimeMillis()


        viewModel.allFollowers.observe(this, Observer { response ->
            if (response.isSuccessful) {
                if (!response.body()?.nextMaxId.isNullOrEmpty()) {

                    getFollowersList(
                        maxId = response.body()?.nextMaxId,
                        userId = 19748713375
                    )

                } else {
                    Log.d("Response", "null max id")

                    followersData()
                }

                //  setRoomFollowers(response.body())
            }
        })




    }

    fun initData(){

        lifecycleScope.launchWhenStarted {
           viewModel.userData.collect {
               when(it){
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
            Log.d("Response", "list-> " + followersList.size.toString())
           // Log.d("Response", "listlast-> " + followersLastList.size.toString())


            viewModel.addFollowers(followersList)


            //  PREFERENCES.firstLogin = false

        } else {
           // Log.d("Response", "listlast-> " + followersLastList.size.toString())
           // viewModel.addLastFollowers(followersLastList)

        }
    }

    private fun addFollowersList(followersData: ApiResponseUserFollowers?) {

        if (followersData != null) {


        /**
            for (data in followersData.users) {
                val newList = LastFollowersData()
                val oldList = FollowData()
                newList.pk = data.pk
                oldList.pk = data.pk
                newList.fullName = data.fullName
                oldList.fullName = data.fullName
                newList.profilePicUrl = data.profilePicUrl
                oldList.profilePicUrl = data.profilePicUrl
                newList.hasAnonymousProfilePicture = data.hasAnonymousProfilePicture
                oldList.hasAnonymousProfilePicture = data.hasAnonymousProfilePicture
                newList.isPrivate = data.isPrivate
                oldList.isPrivate = data.isPrivate
                newList.isVerified = data.isVerified
                oldList.isVerified = data.isVerified
                newList.username = data.username
                oldList.username = data.username
                followersLastList.add(newList)
                followersList.add(oldList)
            }
**/


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


