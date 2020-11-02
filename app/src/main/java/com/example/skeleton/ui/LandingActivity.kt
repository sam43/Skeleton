package com.example.skeleton.ui

import android.os.Bundle
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.skeleton.R
import com.example.skeleton.base.BaseActivity
import com.example.skeleton.base.BaseViewModel
import com.example.skeleton.databinding.ActivityLandingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : BaseActivity<ActivityLandingBinding, BaseViewModel>() {
/*    override val bindingVariable: ViewBinding
        get() = ActivityMainBinding.inflate(layoutInflater)*/
    override val layoutId: Int
        get() = R.layout.activity_landing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment: NavHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_character, R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}