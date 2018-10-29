package com.fj.joinyuk.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fj.joinyuk.R
import com.fj.joinyuk.model.Activity
import kotlinx.android.synthetic.main.item_activity.view.*
import org.jetbrains.anko.sdk25.listeners.onClick

class ActivityAdapter(
        private val context: Context?,
        private var activities: ArrayList<Activity>,
        private val listener: (Activity) -> Unit)
    : RecyclerView.Adapter<ActivityAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(activities[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false))

    override fun getItemCount(): Int = activities.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindItem(activity: Activity, listener: (Activity) -> Unit){
            itemView.txt_username.text = activity.username
            itemView.txt_name.text = activity.name
            itemView.txt_location.text = activity.location
            itemView.txt_date.text = activity.date
            itemView.txt_time.text = activity.time

            val capacityStatus = activity.capacity?.minus(0)
            itemView.txt_capacity.text = "0/$capacityStatus"

            itemView.cv_activity.onClick{
                listener(activity)
            }
        }
    }
}