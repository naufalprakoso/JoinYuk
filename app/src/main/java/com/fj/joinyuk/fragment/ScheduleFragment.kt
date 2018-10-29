package com.fj.joinyuk.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fj.joinyuk.R
import com.fj.joinyuk.activity.ActivityDetailActivity
import com.fj.joinyuk.adapter.ActivityAdapter
import com.fj.joinyuk.model.Activity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

class ScheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        val activities = ArrayList<Activity>()
        val databaseReference = FirebaseDatabase.getInstance().getReference("schedule")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val activity = dataSnapshot.getValue(Activity::class.java)
                    activity?.let { activities.add(it) }
                }

                view.rv_data.layoutManager = LinearLayoutManager(context)
                view.rv_data.adapter = ActivityAdapter(context, activities){
                    context?.startActivity<ActivityDetailActivity>(
                            "name" to it.name,
                            "username" to it.username,
                            "location" to it.location,
                            "capacity" to it.capacity,
                            "date" to it.date,
                            "time" to it.time,
                            "description" to it.description
                    )
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        return view
    }
}
