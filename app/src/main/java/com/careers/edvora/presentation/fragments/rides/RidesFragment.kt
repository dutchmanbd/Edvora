package com.careers.edvora.presentation.fragments.rides

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.activityViewModels
import com.careers.edvora.R
import com.careers.edvora.data.mapper.toCityNames
import com.careers.edvora.data.mapper.toStateNames
import com.careers.edvora.data.mapper.toStates
import com.careers.edvora.databinding.FragmentRidesBinding
import com.careers.edvora.databinding.LayoutFilterBinding
import com.careers.edvora.domain.model.Ride
import com.careers.edvora.domain.model.State
import com.careers.edvora.presentation.activities.main.MainViewModel
import com.careers.edvora.presentation.fragments.base.BaseFragment
import com.careers.extensions.hide
import com.careers.extensions.show
import com.careers.utilities.DateTimeUtils
import com.careers.utilities.Resource
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RidesFragment : BaseFragment<MainViewModel, FragmentRidesBinding>(
    R.layout.fragment_rides
) {

    @Inject
    lateinit var rideAdapter: RideAdapter

    @Inject
    lateinit var stateAdapter: StateAdapter

    @Inject
    lateinit var cityAdapter: CityAdapter


    private val rides = mutableListOf<Ride>()
    private var userStationCode = -1

    private var filterPopup: PopupWindow? = null

    private val allStates = mutableListOf<State>()

    private var selectedStateName = ""
    private var selectedCityName = ""
    private var selectedTabPosition = 0

    // This is shared viewModel
    override val viewModel by activityViewModels<MainViewModel>()

    override fun initializeViewBinding(view: View) = FragmentRidesBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabItems()
        setupRecyclerViews()
        setupListeners()
        subscribeObservers()
    }

    private fun setupListeners() {
        binding.btnFilters.setOnClickListener {
            dismissPopup()
            filterPopup = showAlertFilter()
            filterPopup?.isOutsideTouchable = true
            filterPopup?.isFocusable = true
            filterPopup?.showAsDropDown(binding.btnFilters)
        }
    }


    override fun onPause() {
        super.onPause()
        dismissPopup()
    }

    private fun showAlertFilter(): PopupWindow {
        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_filter, null)
        val filterBinding = LayoutFilterBinding.bind(view)
        filterBinding.rvStates.adapter = stateAdapter
        filterBinding.rvCity.adapter = cityAdapter

        stateAdapter.differ.submitList(allStates.toStateNames())
        cityAdapter.differ.submitList(
            if (selectedStateName.isNotEmpty()) allStates.toCityNames(selectedStateName)
            else allStates.toCityNames()
        )


        filterBinding.btnState.setOnClickListener {
            filterBinding.rvCity.hide()
            filterBinding.rvStates.show()
        }

        filterBinding.btnCity.setOnClickListener {
            filterBinding.rvStates.hide()
            filterBinding.rvCity.show()
            if (selectedStateName.isNotEmpty()) {
                cityAdapter.differ.submitList(allStates.toCityNames(selectedStateName))
            }
        }

        stateAdapter.setOnItemClickListener { _, item ->
            selectedStateName = item
            selectedCityName = ""
            filterBinding.rvStates.hide()
            filteredRides()
        }

        cityAdapter.setOnItemClickListener { _, item ->
            selectedCityName = item
            filterBinding.rvCity.hide()
            filteredRides()
            dismissPopup()
        }

        return PopupWindow(
            view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun dismissPopup() {
        filterPopup?.let {
            if (it.isShowing) {
                it.dismiss()
            }
            filterPopup = null
        }

    }

    private fun setupRecyclerViews() {
        binding.rvRides.adapter = rideAdapter
    }

    private fun subscribeObservers() {

        viewModel.user.observe(viewLifecycleOwner) { user ->
            user ?: return@observe
            userStationCode = user.stationCode
            getRides(userStationCode)
        }
    }

    private fun getRides(userStationCode: Int) {
        viewModel.getRides(userStationCode).observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Failure -> {
                    binding.piRide.hide()
                }
                is Resource.Loading -> {
                    binding.piRide.show()
                }
                is Resource.Success -> {
                    binding.piRide.hide()
                    rides.clear()
                    allStates.clear()
                    rides.addAll(resource.data)
                    allStates.addAll(rides.toStates())
                    filteredRides()
                }
            }
        }
    }

    private fun setupTabItems() {
        binding.tlRides.addTab(binding.tlRides.newTab().setText(getString(R.string.nearest)))
        binding.tlRides.addTab(binding.tlRides.newTab().setText(getString(R.string.upcoming)))
        binding.tlRides.addTab(binding.tlRides.newTab().setText(getString(R.string.past)))

        binding.tlRides.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedTabPosition = tab?.position ?: 0
                filteredRides()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun filteredRides() {
        val upcoming = rides.filter {
            System.currentTimeMillis() < DateTimeUtils.getTimeStamp(it.date)
        }

        val past = rides.filter {
            System.currentTimeMillis() > DateTimeUtils.getTimeStamp(it.date)
        }

        if (!upcoming.isNullOrEmpty()) {
            binding.tlRides.getTabAt(1)?.text = getString(R.string.upcoming_format, upcoming.size)
        }
        if (!past.isNullOrEmpty()) {
            binding.tlRides.getTabAt(2)?.text = getString(R.string.past_format, past.size)
        }

        val rides = if (selectedTabPosition == 0) {
            rides.sortedBy {
                it.distance
            }
        } else if (selectedTabPosition == 1) {
            upcoming
        } else {
            past
        }

        val filtered = rides.filter {
            it.state.contains(selectedStateName) && it.city.contains(selectedCityName)
        }

        updateRidesAndPosition(filtered)
    }

    private fun updateRidesAndPosition(rides: List<Ride>) {
        binding.rvRides.scrollToPosition(0)
        rideAdapter.differ.submitList(rides)
    }


}