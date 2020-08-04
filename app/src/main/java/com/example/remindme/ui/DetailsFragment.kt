package com.example.remindme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.remindme.R
import com.example.remindme.models.Reminder
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var detailsTitle: TextView
    private lateinit var detailsDate: TextView
    private lateinit var detailsDescription: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsTitle = view.findViewById(R.id.detailsTitle)
        detailsDate = view.findViewById(R.id.detailsDate)
        detailsDescription = view. findViewById(R.id.detailsDescription)

        val id = requireArguments().getInt("ID")

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getReminderById(id)
        viewModel.reminderById.observe(this, Observer {
            updateFields(it)
        })

        editFab.setOnClickListener {
            val editReminderFragment = EditReminderFragment()
            val bundle = Bundle()
            bundle.putInt("ID", id)
            editReminderFragment.arguments = bundle
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, editReminderFragment)
                .addToBackStack(null)
                .commit()
        }

        deleteFab.setOnClickListener {
            val reminder = viewModel.reminderById.value!!
            viewModel.deleteReminder(reminder)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun updateFields(reminder: Reminder) {
        val date = "${reminder.numberOfMonth}.${reminder.month}.${reminder.year} ${reminder.hours}:${reminder.minutes}"
        detailsTitle.text = reminder.title
        detailsDate.text = date
        detailsDescription.text = reminder.description
    }
}