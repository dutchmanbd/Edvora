package com.careers.edvora.presentation.fragments.rides

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.careers.edvora.R
import com.careers.edvora.databinding.FragmentRidesBinding
import com.careers.edvora.presentation.activities.main.MainViewModel
import com.careers.edvora.presentation.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RidesFragment : BaseFragment<MainViewModel, FragmentRidesBinding>(
    R.layout.fragment_rides
) {
    // This is shared viewModel
    override val viewModel by activityViewModels<MainViewModel>()

    override fun initializeViewBinding(view: View) = FragmentRidesBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}