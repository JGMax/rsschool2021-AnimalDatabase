package gortea.jgmax.animalstorage.ui.fragments

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import gortea.jgmax.animalstorage.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey)
        val sortEnablePreference: SwitchPreferenceCompat? = findPreference("sort_data_key")
        val sortByList: ListPreference? = findPreference("sort_list")
        sortByList?.value
        sortEnablePreference?.setOnPreferenceChangeListener { preference, _ ->
            val me = preference as SwitchPreferenceCompat
            sortByList?.isEnabled = !me.isChecked
            true
        }
    }
}