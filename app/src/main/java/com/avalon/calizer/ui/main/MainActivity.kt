package com.avalon.calizer.ui.main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.avalon.calizer.databinding.ActivityMainBinding
import com.avalon.calizer.R
import com.avalon.calizer.data.local.FollowersData
import com.avalon.calizer.data.local.LastFollowersData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.calizer.ui.main.fragments.MainViewPagerAdapter
import com.avalon.calizer.ui.main.fragments.analyze.AnalyzeFragment
import com.avalon.calizer.ui.main.fragments.profile.ProfileFragment
import com.avalon.calizer.ui.main.fragments.settings.SettingsFragment
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var cookies: String

    @Inject
    lateinit var prefs: MySharedPreferences

    private val followersList = ArrayList<FollowersData>()
    private val followersLastList = ArrayList<LastFollowersData>()

    private fun setupBottomNavigationMenu(navController: NavController) {
        binding.bottomNavigation?.let {
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
        prefs.selectedAccount = 1000L
        Log.d("RoomHash", "${prefs.selectedAccount}")

        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        setupBottomNavigationMenu(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.destination_profile ||destination.id == R.id.destination_analyze ||destination.id == R.id.destination_settings){

                binding.bottomNavigation.visibility = View.VISIBLE

            }else{
                binding.bottomNavigation.visibility = View.GONE

            }

        }


        //val dbRepository = RoomRepository(roomDao)
        //val repository = Repository()
        // val factory = MainViewModelFactory(dbRepository, repository)
        // viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        // PREFERENCES.firstLogin = true
        // if (!PREFERENCES.allCookie.isNullOrEmpty()) {
        //    cookies = PREFERENCES.allCookie!!
        //    if (Utils.getTimeStatus(PREFERENCES.followersUpdateDate)) {

        //  getFollowersList(userId = "19748713375")

        // PREFERENCES.followersUpdateDate = System.currentTimeMillis()
        //      }
        //   }

        viewModel.allFollowers.observe(this, Observer { response ->
            if (response.isSuccessful) {
                if (!response.body()?.nextMaxId.isNullOrEmpty()) {

                    getFollowersList(
                        maxId = response.body()?.nextMaxId,
                        userId = "19748713375"
                    )

                } else {
                    Log.d("Response", "null max id")

                    followersData()
                }

                setRoomFollowers(response.body())
            }
        })

        viewModel.noFollow.observe(this, Observer { response ->
            Log.d("Response", "" + response.username)

        })


    }


    private fun followersData() {
        // if (PREFERENCES.firstLogin) {
        if (10 > 20) {
            Log.d("Response", "list-> " + followersList.size.toString())
            Log.d("Response", "listlast-> " + followersLastList.size.toString())

            viewModel.getNotFollow()
            viewModel.addFollowers(followersList)
            viewModel.addLastFollowers(followersLastList)

            //  PREFERENCES.firstLogin = false

        } else {
            Log.d("Response", "listlast-> " + followersLastList.size.toString())
            viewModel.addLastFollowers(followersLastList)

        }
    }

    private fun setRoomFollowers(followersData: ApiResponseUserFollowers?) {

        if (followersData != null) {

            for (data in followersData.users) {
                val newList = LastFollowersData()
                val oldList = FollowersData()
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


        }

    }

    fun getFollowersList(maxId: String? = null, userId: String): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            delay((500 + (0..250).random()).toLong())
            if (!maxId.isNullOrEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getUserFollowers(
                        userId = userId,
                        maxId = maxId,
                        rnkToken = Utils.generateUUID(),
                        cookies
                    )


                }

            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getUserFollowers(
                        userId = userId,
                        cookies = cookies,
                        maxId = null,
                        rnkToken = null
                    )

                }

            }
        }


    }

    fun getFollowingList(maxId: String? = null, userId: String): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            delay((500 + (0..250).random()).toLong())
            if (!maxId.isNullOrEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getUserFollowers(
                        userId = userId,
                        maxId = maxId,
                        rnkToken = Utils.generateUUID(),
                        cookies
                    )


                }

            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getUserFollowers(
                        userId = userId,
                        cookies = cookies,
                        maxId = null,
                        rnkToken = null
                    )

                }

            }
        }


    }


}


