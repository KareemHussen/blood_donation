package com.example.blooddonation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.blooddonation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BloodDonation)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavigationView.background = null;

        val navController = findNavController(R.id.fragmentContainerView2)


        val graphInflater = navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)
        val destination=if(onBoardingFinished()) R.id.loginFragment else R.id.viewPagerFragment

        navGraph.setStartDestination(destination)
        navController.graph=navGraph
        getMainActivityComponentsState(navController)

        binding.bottomNavigationView.setupWithNavController(navController)
        supportActionBar?.hide()

        binding.fab.setOnClickListener {
            navController.navigate(R.id.createRequest)
        }

    }

    private fun getMainActivityComponentsState(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.profileFragment,
                R.id.searchFragment,
                R.id.donationRequestFragment,
            )
        )
        navController.addOnDestinationChangedListener { _: NavController, destination: NavDestination, _: Bundle? ->
            binding.bottomAppBar.isVisible =
                appBarConfiguration.topLevelDestinations.contains(destination.id)
            binding.fab.isVisible=appBarConfiguration.topLevelDestinations.contains(destination.id)
        }
    }
    private fun onBoardingFinished(): Boolean{
        val sharedPref = getSharedPreferences("onBoarding", MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }


}