package com.example.remindme.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.remindme.R
import com.example.remindme.ui.notifications.ReminderNotifications


class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager

        val valueFromNotification by lazy {
            intent.extras?.get(ReminderNotifications.VALUE_FROM_NOTIFICATION).toString()
        }
/*
        val id by lazy {
            intent.extras?.get("Hello")
                .toString()
                .toInt()
        }

 */

        if (savedInstanceState == null) {

            when (valueFromNotification) {
                "1" -> {

                    val id = intent.extras?.get(ID_FROM_NOTIFICATION).toString().toInt()
                    Log.i("tag", "main ${id}")
                    openDetailsFragment(id)
                }
                else -> {
                    val listFragment = RemindListFragment()
                    val transaction = fragmentManager.beginTransaction()
                    transaction.add(R.id.container, listFragment)
                    transaction.commit()
                }
            }
        } else if ((valueFromNotification == "1")) {
            val id = intent.extras?.get(ID_FROM_NOTIFICATION).toString().toInt()
            Log.i("tag", "main ${id}")
            openDetailsFragment(id)
        }
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun openDetailsFragment(id: Int) {
        val detailsFragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putInt("ID", id)
        detailsFragment.arguments = bundle
        fragmentManager
            .beginTransaction()
            .replace(R.id.container, detailsFragment)
            .commit()
    }

    companion object {
        const val ID_FROM_NOTIFICATION = "id"
    }
}