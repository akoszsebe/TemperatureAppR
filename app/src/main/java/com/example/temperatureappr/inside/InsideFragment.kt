package com.example.temperatureappr.inside

import android.os.Bundle
import android.view.View
import com.example.temperatureappr.R
import com.example.temperatureappr.base.BaseFragment
import com.example.temperatureappr.databinding.FragmentInsideBinding

class InsideFragment :
    BaseFragment<FragmentInsideBinding, InsideViewModel>(R.layout.fragment_inside) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = InsideViewModel()
        subscribeUi()
    }

    private fun subscribeUi() {}
}