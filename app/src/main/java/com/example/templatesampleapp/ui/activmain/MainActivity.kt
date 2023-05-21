package com.example.templatesampleapp.ui.activmain

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.templatesampleapp.R
import com.example.templatesampleapp.base.BaseActivity
import com.example.templatesampleapp.databinding.ActivityMainBinding
import com.example.templatesampleapp.helper.showLog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val viewModel by viewModels<MainViewModel>()
    lateinit var appBarConfiguration: AppBarConfiguration

    val setOftopDest =
        setOf(R.id.homeFragment, R.id.categoryFragment, R.id.authorFragment, R.id.favourateFragment)
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.topAppBar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOftopDest,
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { controller, destination, arguments ->

            showLog("Show App BAr = ${arguments?.getBoolean("ShowAppBar", true)}  ii${destination.id}")
            binding.bottomNavigation.isVisible = arguments?.getBoolean("ShowAppBar", true)?:true
        })


    }
}