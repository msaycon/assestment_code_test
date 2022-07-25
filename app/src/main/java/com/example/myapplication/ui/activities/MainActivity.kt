package com.example.myapplication.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityMainBinding

/**
 * Created by msaycon on 17,Jul,2022
 */
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        initializedViews()
        initializedListeners()
    }

    override fun initializedViews() {
        setSupportActionBar(binding.appBar.toolbar)

        navController = findNavController(R.id.fragment_container)

        NavigationUI.setupActionBarWithNavController(this, navController)
        NavigationUI.setupWithNavController(binding.appBar.toolbar, navController)

        binding.appBar.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun setToolbarTitle(title: String) {
        binding.appBar.toolbar.title = title
    }
}