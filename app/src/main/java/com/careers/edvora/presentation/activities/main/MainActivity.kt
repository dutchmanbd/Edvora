package com.careers.edvora.presentation.activities.main

import android.os.Bundle
import androidx.activity.viewModels
import com.careers.edvora.databinding.ActivityMainBinding
import com.careers.edvora.presentation.activities.BaseActivity
import com.careers.extensions.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val navController by lazy {
        findNavController(binding.navHostMainFragment.id)
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun initializeViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}