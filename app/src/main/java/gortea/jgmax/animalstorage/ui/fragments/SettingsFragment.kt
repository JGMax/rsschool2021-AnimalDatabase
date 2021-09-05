package gortea.jgmax.animalstorage.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import gortea.jgmax.animalstorage.R
import gortea.jgmax.animalstorage.ui.navigation.ActionBarController

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBarController = context as? ActionBarController
        actionBarController?.setActionBarVisibility(false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}