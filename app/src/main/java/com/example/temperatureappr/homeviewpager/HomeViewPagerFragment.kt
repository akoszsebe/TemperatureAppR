package com.example.temperatureappr.homeviewpager

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.temperatureappr.R
import com.example.temperatureappr.base.BaseFragment
import com.example.temperatureappr.databinding.FragmentViewPagerBinding
import com.example.temperatureappr.homeviewpager.adapter.HomeViewPagerAdapter

class HomeViewPagerFragment :
    BaseFragment<FragmentViewPagerBinding, HomeViewPagerViewModel>(
        R.layout.fragment_view_pager,
        HomeViewPagerViewModel::class
    ) {

    private lateinit var viewPagerPageChangeCallback: ViewPager2PageChangeCallback
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager
        viewPager.adapter = HomeViewPagerAdapter(this)
        binding.navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_outside -> {
                    viewPager.currentItem = 1
                    setStatusBarColor(R.color.colorPinkDark)
                    true
                }
                else -> {
                    viewPager.currentItem = 0
                    setStatusBarColor(R.color.colorGreenDark)
                    true
                }
            }
        }
        viewPagerPageChangeCallback = ViewPager2PageChangeCallback {
            viewPager.setCurrentItem(it, false)
            binding.navigation.menu[it].isChecked = true
            when (it) {
                1 -> setStatusBarColor(R.color.colorPinkDark)
                else -> setStatusBarColor(R.color.colorGreenDark)
            }
        }
        viewPager.registerOnPageChangeCallback(viewPagerPageChangeCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(viewPagerPageChangeCallback)

    }

    private fun setStatusBarColor(colorRes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity?.window?.statusBarColor = resources.getColor(colorRes);
        }
    }
}

class ViewPager2PageChangeCallback(private val listener: (Int) -> Unit) :
    ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        listener.invoke(position)
    }
}