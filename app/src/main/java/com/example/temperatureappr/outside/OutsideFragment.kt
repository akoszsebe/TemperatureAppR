package com.example.temperatureappr.outside

import android.os.Bundle
import android.view.View
import com.example.temperatureappr.R
import com.example.temperatureappr.base.BaseFragment
import com.example.temperatureappr.databinding.FragmentOutsideBinding

class OutsideFragment :
    BaseFragment<FragmentOutsideBinding, OutsideViewModel>(R.layout.fragment_outside) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = OutsideViewModel()
        subscribeUi()
    }

    private fun subscribeUi() {}
}