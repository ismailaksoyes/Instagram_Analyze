package com.avalon.calizer.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.avalon.calizer.R
import com.avalon.calizer.databinding.GlobalLoadingAnimBinding
import javax.inject.Inject

class LoadingAnim ( val fm: FragmentManager) :DialogFragment() {
    lateinit var binding: GlobalLoadingAnimBinding

    companion object {
        const val TAG = "LoadingAnim"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,R.style.LoadingDialog)
        isCancelable = false
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = GlobalLoadingAnimBinding.inflate(inflater,container,false)
        return binding.root
    }

    fun showDialog(tag: String? = null){
        show(fm,tag)
    }

    fun closeDialog(){
        dismissAllowingStateLoss()
    }

}