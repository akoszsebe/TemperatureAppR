package com.example.temperatureappr.homeviewpager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.temperatureappr.inside.InsideFragment
import com.example.temperatureappr.outside.OutsideFragment

const val INSIDE_PAGE_INDEX = 0
const val OUTSIDE_PAGE_INDEX = 1

class HomeViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        INSIDE_PAGE_INDEX to { InsideFragment() },
        OUTSIDE_PAGE_INDEX to { OutsideFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}