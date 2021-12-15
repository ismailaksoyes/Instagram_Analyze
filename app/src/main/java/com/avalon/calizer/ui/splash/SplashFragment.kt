package com.avalon.calizer.ui.splash

import android.os.Bundle

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentSplashBinding
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.utils.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment :BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {


    @Inject
    lateinit var prefs:MySharedPreferences

    private val viewModel: SplashViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locale = context!!.resources.configuration.locale.country
        viewModel.getLocalization("en")

        lifecycleScope.launch {
            viewModel.localHash.collect {
                navOto()
            }
        }

    }

    private fun navOto(){
        lifecycleScope.launch {
            delay(2000)
            if (prefs.showIntro) findNavController().navigate(R.id.action_splashFragment_to_destination_tutorial) else findNavController().navigate(R.id.action_splashFragment_to_destination_accounts)
        }

    }
}