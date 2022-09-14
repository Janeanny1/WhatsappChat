package com.example.whatsappchat

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.*


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val dataStore = DataStore()
       // preferenceManager.preferenceDataStore = dataStore

        val accSettingsPref = findPreference<Preference>(getString(R.string.key_account_settings))

        accSettingsPref?.setOnPreferenceClickListener {

            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_frag) as NavHostFragment
            val navController = navHostFragment.navController
            val action = SettingsFragmentDirections.actionSettingsToAccSettings()
            navController.navigate(action)

            true
        }

        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val autoReplyTime = sharedPreference.getString(getString(R.string.key_auto_reply_time), "")
        Log.i("SettingsFragment", "Auto Reply Time: $autoReplyTime")

        val autoDownload = sharedPreference.getBoolean("key_auto_download", false)
        Log.i("SettingsFragment", "Auto Download: $autoDownload")

        val statusPref = findPreference<EditTextPreference>("key_status")
        statusPref?.setOnPreferenceChangeListener { preference, newValue ->

            val newStatus = newValue as String

            if (newStatus.contains("bad")) {

                Toast.makeText(context, "Inappropriate Status. Please maintain community guidelines",
                    Toast.LENGTH_SHORT).show()

                false
            } else {
                true
            }
        }

        val notificationPref = findPreference<SwitchPreferenceCompat>("key_new_msg_notif")
        notificationPref?.summaryProvider = Preference.SummaryProvider<SwitchPreferenceCompat> { switchPref ->

            if (switchPref.isChecked)
                "Status: ON"
            else
                "Status: OFF"
        }

        notificationPref?.preferenceDataStore = dataStore

        val isNotifEnabled = dataStore.getBoolean("key_new_msg_notif", false)

    }

    class DataStore : PreferenceDataStore(){


        override fun getBoolean(key: String?, defValue: Boolean): Boolean {

            if (key == "key_new_msg_notif"){
                Log.i("DataStore", "getBoolean executed for $key")
            }

            return defValue
        }

        override fun putBoolean(key: String?, value: Boolean) {

            if (key == "key_new_msg_notif"){

                Log.i("DataStore", "putBoolean executed for $key with new value: $value")
            }
        }

    }


}