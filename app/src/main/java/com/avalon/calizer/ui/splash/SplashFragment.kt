package com.avalon.calizer.ui.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentSplashBinding
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.utils.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment :BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {


    @Inject
    lateinit var prefs:MySharedPreferences



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState ==null){
            lifecycleScope.launchWhenStarted {

              //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }
        navOto()
    }

    private fun navOto(){
        lifecycleScope.launch {
            delay(1000)
            if (prefs.showIntro) findNavController().navigate(R.id.action_splashFragment_to_destination_tutorial) else findNavController().navigate(R.id.action_splashFragment_to_destination_accounts)
        }

    }
}