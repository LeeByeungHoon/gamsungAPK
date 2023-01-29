package com.hannip.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.navigation.NavigationBarView
import com.hannip.myapplication.databinding.ActivityMainBinding
import com.hannip.myapplication.ui.adapter.FragmentAdapter


class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener,
    NavigationBarView.OnItemReselectedListener {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        (getActivity(this) as AppCompatActivity).supportActionBar?.hide()
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_user
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        val viewPager: ViewPager2 = findViewById(R.id.nav_host_fragment_activity_main)
        viewPager.adapter = FragmentAdapter(this)
        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    navView.menu.getItem(position).isChecked = true
                }
            }
        )

        // 리스너 연결
        navView.setOnItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val viewPager: ViewPager2 = findViewById(R.id.nav_host_fragment_activity_main)
        when (item.itemId) {
            R.id.navigation_home -> {
                viewPager.currentItem = 0
                return true
            }
            R.id.navigation_dashboard -> {
                viewPager.currentItem = 1
                return true
            }
            R.id.navigation_notifications -> {
                viewPager.currentItem = 2
                return true
            }
            R.id.navigation_user -> {
                viewPager.currentItem = 3
                return true
            }
            else -> {
                return false
            }
        }
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        TODO("Not yet implemented")
    }
}
