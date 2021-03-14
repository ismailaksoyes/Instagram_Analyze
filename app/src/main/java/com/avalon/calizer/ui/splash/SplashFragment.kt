package com.avalon.calizer.ui.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentSplashBinding
import com.avalon.calizer.utils.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_view_pager.view.*
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var prefs:MySharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("hashTest","${prefs.showIntro}")
        navOto()
      //  if (prefs.showIntro) findNavController().navigate(R.id.action_splashFragment_to_destination_tutorial) else findNavController().navigate(R.id.action_splashFragment_to_destination_accounts)
    }
    fun navOto(){
        lifecycleScope.launch {
            delay(3000)
            if (prefs.showIntro) findNavController().navigate(R.id.action_splashFragment_to_destination_tutorial) else findNavController().navigate(R.id.action_splashFragment_to_destination_accounts)
        }



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }



}