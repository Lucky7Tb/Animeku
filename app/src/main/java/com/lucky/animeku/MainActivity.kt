package com.lucky.animeku

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lucky.animeku.utils.CheckInternetConnection

class MainActivity : AppCompatActivity() {
    private lateinit var navigationController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var checkInternetConnection: CheckInternetConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationController = findNavController(R.id.navHostFragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.topFragment, R.id.searchedFragment, R.id.favoriteFragment)
        )

        NavigationUI.setupActionBarWithNavController(this, navigationController, appBarConfiguration)

        val bottomBarNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomBarNavigation.setupWithNavController(navigationController)

        checkInternetConnection = CheckInternetConnection(this.application)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        checkInternetConnection.startCheckConsumeTime()
    }

    override fun onStop() {
        super.onStop()
        checkInternetConnection.stopCheckConsumeTime()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp()
    }
}
