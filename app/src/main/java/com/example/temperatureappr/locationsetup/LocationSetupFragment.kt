package com.example.temperatureappr.locationsetup

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.temperatureappr.R
import com.example.temperatureappr.base.BaseFragment
import com.example.temperatureappr.databinding.FragmentLocationSetupBinding

class LocationSetupFragment : BaseFragment<FragmentLocationSetupBinding, LocationSetupViewModel>(
    R.layout.fragment_location_setup,
    LocationSetupViewModel::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(binding.toolbar, true)
        binding.buttonGps.setOnClickListener {
            if (viewModel.locationHelper.checkPermissionForLocation(this.requireActivity())) {
                val locationManager =
                    this.requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    showDialogNoGps()
                } else {
                    showLoader()
                    viewModel.locationHelper.startLocationUpdates(
                        this.requireContext(),
                        viewModel.locationCallback
                    )
                }
            }
        }
        initViewModel()
    }

    override fun initViewModel() {
        viewModel.location.observe(viewLifecycleOwner, Observer { newLocation ->
            binding.textViewResult.text = newLocation.locationName
            sharedPrefs.setCurrentLocation(newLocation.locationId)
            hideLoader()
        })
    }
}