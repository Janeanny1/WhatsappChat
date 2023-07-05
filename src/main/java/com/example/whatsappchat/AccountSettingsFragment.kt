package com.example.whatsappchat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.MultiSelectListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.whatsappchat.R
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.core.content.res.ResourcesCompat


class AccountSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        val publicInfoPref = MultiSelectListPreference(requireContext())
        publicInfoPref.entries = resources.getStringArray(R.array.entries_public_info)
        publicInfoPref.entryValues = resources.getStringArray(R.array.values_public_info)
        publicInfoPref.key = "key_public_info"
        publicInfoPref.title = "My_public_info"
        publicInfoPref.isIconSpaceReserved = false

        val logOutPref = Preference(requireContext())
        logOutPref.key = "key_log_out"
        logOutPref.title = "Log_out"
        logOutPref.isIconSpaceReserved = false

        val deleteAccPref = Preference(requireContext())
        deleteAccPref.key = "key_delete_account"
        deleteAccPref.summary = "This cannot be undone"
        deleteAccPref.title = "Delete my account"
        deleteAccPref.icon = ResourcesCompat.getDrawable(resources, android.R.drawable.ic_menu_delete, null)

        val privacyCategory = PreferenceCategory(requireContext())
        privacyCategory.title = "Privacy"
        privacyCategory.isIconSpaceReserved = false

        val securityCategory = PreferenceCategory(requireContext())
        securityCategory.title = "Security"
        securityCategory.isIconSpaceReserved = false

        val prefScreen = preferenceManager.createPreferenceScreen(requireContext())


        prefScreen.addPreference(privacyCategory)
        prefScreen.addPreference(securityCategory)

        privacyCategory.addPreference(publicInfoPref)

        securityCategory.addPreference(logOutPref)
        securityCategory.addPreference(deleteAccPref)


        preferenceScreen = prefScreen
    }



}