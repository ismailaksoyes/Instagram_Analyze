package com.avalon.calizer.ui.main.fragments.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log

import androidx.preference.PreferenceFragmentCompat
import com.avalon.calizer.R


class SettingsFragment : PreferenceFragmentCompat() {



    private lateinit var viewModel: SettingsViewModel



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)




    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIFECYCLE CREATE-> ", "onCreate:Profile ")
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings,rootKey)
    }

}