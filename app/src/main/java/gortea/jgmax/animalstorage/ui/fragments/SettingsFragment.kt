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
        val sortEnablePreference: SwitchPreferenceCompat? = findPreference(getString(R.string.sort_data_switch_key))
        val sortByList: ListPreference? = findPreference(getString(R.string.sort_list_key))
        sortByList?.value
        sortEnablePreference?.setOnPreferenceChangeListener { preference, _ ->
            val me = preference as SwitchPreferenceCompat
            sortByList?.isEnabled = !me.isChecked
            true
        }
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