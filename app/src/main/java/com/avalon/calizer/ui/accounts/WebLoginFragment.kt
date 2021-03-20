package com.avalon.calizer.ui.accounts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentWebLoginBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WebLoginFragment : Fragment() {
   private lateinit var binding: FragmentWebLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebLoginBinding.inflate(inflater,container,false)
        return binding.root
    }


}