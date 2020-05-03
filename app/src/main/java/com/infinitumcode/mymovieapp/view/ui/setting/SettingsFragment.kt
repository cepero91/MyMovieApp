package com.infinitumcode.mymovieapp.view.ui.setting

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.infinitumcode.mymovieapp.R

class SettingsFragment : PreferenceFragmentCompat() {


    private var preferenceList: ListPreference? = null
    private var preferenceCategoryPopular: PreferenceCategory? = null
    private var preferenceCategoryChild: PreferenceCategory? = null
    private var preferenceCategoryFavorite: PreferenceCategory? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUIPreferences()
    }

    private fun initUIPreferences() {
        preferenceCategoryFavorite = findPreference("menu_favorite")
        preferenceCategoryChild = findPreference("menu_child")
        preferenceCategoryPopular = findPreference("menu_popular")
        preferenceList = findPreference("menu")
        manageVisibilityCategory(preferenceList!!.value)
        preferenceList!!.setOnPreferenceChangeListener { _, newValue ->
            manageVisibilityCategory(newValue)
            true
        }
    }

    private fun manageVisibilityCategory(newValue: Any?) {
        when (newValue) {
            "navigation_favorite" -> showHidePreferenceByIndex(0)
            "navigation_child" -> showHidePreferenceByIndex(1)
            "navigation_popular" -> showHidePreferenceByIndex(2)
        }
    }

    private fun showHidePreferenceByIndex(index: Int) {
        when (index) {
            0 -> {
                preferenceCategoryFavorite!!.isVisible = true
                preferenceCategoryChild!!.isVisible = false
                preferenceCategoryPopular!!.isVisible = false
            }
            1 -> {
                preferenceCategoryFavorite!!.isVisible = false
                preferenceCategoryChild!!.isVisible = true
                preferenceCategoryPopular!!.isVisible = false
            }
            2 -> {
                preferenceCategoryFavorite!!.isVisible = false
                preferenceCategoryChild!!.isVisible = false
                preferenceCategoryPopular!!.isVisible = true
            }
        }
    }
}
