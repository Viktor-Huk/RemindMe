package com.example.remindme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remindme.R
import com.example.remindme.models.Reminder
import kotlinx.android.synthetic.main.list_item.view.*

class ReminderAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {
    private var reminders = listOf<Reminder>()

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView = itemView.titleTextView
        val dateTextView = itemView.dateTextView

        fun bind(reminder: Reminder, clickListener: OnItemClickListener) {
            val date =
                "${reminder.numberOfMonth}.${reminder.month}.${reminder.year} ${reminder.hours}:${reminder.minutes}"
            titleTextView.text = reminder.title
            dateTextView.text = date

            itemView.setOnClickListener {
                clickListener.onItemClick(reminder, adapterPosition)
            }
        }
    }

    fun setReminders(reminders: List<Reminder>) {
        this.reminders = reminders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ReminderViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reminders.size
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(reminders[position], clickListener)
    }
}

interface OnItemClickListener {
    fun onItemClick(reminder: Reminder, position: Int)
}