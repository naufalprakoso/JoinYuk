package com.fj.joinyuk.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fj.joinyuk.R
import com.fj.joinyuk.model.Activity
import kotlinx.android.synthetic.main.item_message.view.*
import org.jetbrains.anko.sdk25.listeners.onClick

class MessageAdapter(
        private val context: Context?,
        private var activities: ArrayList<Activity>,
        private val listener: (Activity) -> Unit)
    : RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(activities[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, parent, false))

    override fun getItemCount(): Int = activities.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindItem(activity: Activity, listener: (Activity) -> Unit){
            itemView.txt_name.text = activity.name
            itemView.txt_desc.text = activity.description
            itemView.cv_item.onClick{
                listener(activity)
            }
        }
    }
}