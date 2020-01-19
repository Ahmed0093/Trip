package com.development.task.triphava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.development.task.triphava.viewModel.TripResultViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DomainIntegration.withContext(this)
        initializeActionBar()
    }

    private fun initializeActionBar() {
        navController = findNavController(R.id.navFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        findViewById<Toolbar>(R.id.appBar)
            .setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.fragment_home_search -> {
                    appBar.visibility = View.GONE
                }
                R.id.fragment_search_result -> {
                    appBar.visibility = View.VISIBLE
                }
                R.id.fragment_trip_details -> {
                    appBar.visibility = View.VISIBLE

                }
            }
        }
    }
}

