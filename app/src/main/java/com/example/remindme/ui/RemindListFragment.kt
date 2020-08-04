package com.example.remindme.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remindme.R
import com.example.remindme.adapter.OnItemClickListener
import com.example.remindme.adapter.ReminderAdapter
import com.example.remindme.models.Reminder
import kotlinx.android.synthetic.main.fragment_remind_list.*

class RemindListFragment : Fragment(), OnItemClickListener {
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_remind_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ReminderAdapter(this)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getReminders()
        viewModel.reminders.observe(viewLifecycleOwner, Observer { reminders ->
            reminders?.let {
                adapter.setReminders(it)
            }
        })

        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter

        addFab.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.container,
                    EditReminderFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        addFab.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, AddReminderFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onItemClick(reminder: Reminder, position: Int) {
        val detailsFragment = DetailsFragment()
        val bundle = Bundle()
        reminder.id?.let { bundle.putInt("ID", it) }
        detailsFragment.arguments = bundle
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

}