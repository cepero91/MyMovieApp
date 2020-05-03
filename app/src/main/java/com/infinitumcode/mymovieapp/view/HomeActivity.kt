package com.infinitumcode.mymovieapp.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.infinitumcode.mymovieapp.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.view.*

class HomeActivity : DaggerAppCompatActivity(), NavController.OnDestinationChangedListener,
    SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var sharedPreferences: SharedPreferences

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_popular,
                R.id.navigation_child,
                R.id.navigation_favorite,
                R.id.navigation_setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener(this)
        navView.setupWithNavController(navController)
        updateNavViewMenuUI()
    }

    override fun onResume() {
        super.onResume()
        sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return configureToolbar(menu)
    }

    private fun configureToolbar(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.common_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                navController.navigate(R.id.navigation_search)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateNavViewMenuUI() {
        if (sharedPreferences.contains("favorite_menu"))
            changeMenuLabel(
                R.id.navigation_favorite,
                sharedPreferences.getString("favorite_menu", "")!!
            )
        if (sharedPreferences.contains("child_menu"))
            changeMenuLabel(R.id.navigation_child, sharedPreferences.getString("child_menu", "")!!)
        if (sharedPreferences.contains("popular_menu"))
            changeMenuLabel(
                R.id.navigation_popular,
                sharedPreferences.getString("popular_menu", "")!!
            )
        if (sharedPreferences.contains("favorite_visible"))
            changeMenuVisibility(
                R.id.navigation_favorite,
                sharedPreferences.getBoolean("favorite_visible", true)
            )
        if (sharedPreferences.contains("child_visible"))
            changeMenuVisibility(
                R.id.navigation_child,
                sharedPreferences.getBoolean("child_visible", true)
            )
        if (sharedPreferences.contains("popular_visible"))
            changeMenuVisibility(
                R.id.navigation_popular,
                sharedPreferences.getBoolean("popular_visible", true)
            )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.detail_fragment, R.id.splash_fragment -> {
                toolbar.visibility = View.GONE
            }
            else -> {
                toolbar.visibility = View.VISIBLE
                toolbar.tvToolbarTitle.text = destination.label
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "favorite_menu" -> changeMenuLabel(
                R.id.navigation_favorite,
                sharedPreferences!!.getString(key, "")!!
            )
            "child_menu" -> changeMenuLabel(
                R.id.navigation_child,
                sharedPreferences!!.getString(key, "")!!
            )
            "popular_menu" -> changeMenuLabel(
                R.id.navigation_popular,
                sharedPreferences!!.getString(key, "")!!
            )
            "favorite_visible" -> changeMenuVisibility(
                R.id.navigation_favorite,
                sharedPreferences!!.getBoolean(key, true)
            )
            "child_visible" -> changeMenuVisibility(
                R.id.navigation_child,
                sharedPreferences!!.getBoolean(key, true)
            )
            "popular_visible" -> changeMenuVisibility(
                R.id.navigation_popular,
                sharedPreferences!!.getBoolean(key, true)
            )
        }
    }


    private fun changeMenuLabel(menuId: Int, label: String) {
        val menuItem = nav_view.menu.findItem(menuId)
        menuItem.title = label
    }

    private fun changeMenuVisibility(menuId: Int, visibility: Boolean) {
        val menuItem = nav_view.menu.findItem(menuId)
        menuItem.isVisible = visibility
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}
