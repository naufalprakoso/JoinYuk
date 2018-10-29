package com.fj.joinyuk.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fj.joinyuk.R
import com.fj.joinyuk.adapter.MessageAdapter
import com.fj.joinyuk.model.Activity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_message.view.*
import java.util.ArrayList

class MessageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_message, container, false)

        val activities = ArrayList<Activity>()
        val databaseReference = FirebaseDatabase.getInstance().getReference("schedule")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val activity = dataSnapshot.getValue(Activity::class.java)
                    activity?.let { activities.add(it) }
                }

                view.rv_data.layoutManager = LinearLayoutManager(context)
                view.rv_data.adapter = MessageAdapter(context, activities){}
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        return view
    }
}
