package com.example.temperatureappr.locationsetup

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.temperatureappr.R
import com.example.temperatureappr.base.BaseFragment
import com.example.temperatureappr.databinding.FragmentLocationSetupBinding
import com.example.temperatureappr.utils.REQUEST_PERMISSION_LOCATION

class LocationSetupFragment : BaseFragment<FragmentLocationSetupBinding, LocationSetupViewModel>(
    R.layout.fragment_location_setup,
    LocationSetupViewModel::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(binding.toolbar, true)
        binding.buttonGps.setOnClickListener {
            if (viewModel.locationHelper.checkPermissionForLocation(this)) {
                getLocation()
            }
        }
        initViewModel()
    }

    private fun getLocation() {
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

    override fun initViewModel() {
        viewModel.location.observe(viewLifecycleOwner, Observer { newLocation ->
            binding.textViewResult.text = newLocation.locationName
            sharedPrefs.setCurrentLocation(newLocation.locationId)
            hideLoader()
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_LOCATION -> if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                showDialog("GPS permission not allowed")
            }
        }
    }
}