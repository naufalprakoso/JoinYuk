package com.fj.joinyuk.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.Gravity
import android.widget.FrameLayout
import com.fj.joinyuk.R
import com.fj.joinyuk.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import re.robz.bottomnavigation.circularcolorreveal.BottomNavigationCircularColorReveal

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init Fragment
        val fragmentInit = HomeFragment()
        openFragment(fragmentInit)

        val colors = resources.getIntArray(R.array.menu_colors)
        val reveal = BottomNavigationCircularColorReveal(colors)

        reveal.setuptWithBottomNavigationView(bottom_navigation)

        reveal.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> {
                    openFragment(fragmentInit)
                    supportActionBar?.title = getString(R.string.home)
                }
                R.id.menu_find_activity -> {
                    val fragment = FindActivityFragment()
                    openFragment(fragment)
                    supportActionBar?.title = getString(R.string.find_activity)
                }
                R.id.menu_activity -> {
                    val fragment = AddActivityFragment()
                    openFragment(fragment)
                    supportActionBar?.title = getString(R.string.add_activity)
                }
                R.id.menu_message -> {
                    val fragment = MessageFragment()
                    openFragment(fragment)
                    supportActionBar?.title = getString(R.string.message)
                }
                R.id.menu_schedule -> {
                    val fragment = ScheduleFragment()
                    openFragment(fragment)
                    supportActionBar?.title = getString(R.string.schedule)
                }
            }
            true
        }

        bottom_navigation.getChildAt(0).setBackgroundResource(R.color.fake_transparent)
        ViewCompat.setOnApplyWindowInsetsListener(bottom_navigation) { _, insets ->
            if (insets.systemWindowInsetBottom > 0) {
                val subview = bottom_navigation.getChildAt(0)
                val layoutParams = subview.layoutParams as FrameLayout.LayoutParams
                layoutParams.gravity = Gravity.TOP
                layoutParams.bottomMargin = insets.systemWindowInsetBottom
                subview.layoutParams = layoutParams
            }
            insets.consumeSystemWindowInsets()
        }
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container_home, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
