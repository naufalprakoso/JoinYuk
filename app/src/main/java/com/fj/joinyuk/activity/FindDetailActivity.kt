package com.fj.joinyuk.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.fj.joinyuk.R
import com.fj.joinyuk.adapter.ActivityAdapter
import com.fj.joinyuk.model.Activity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_find_detail.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

class FindDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_detail)

        val getCategory = intent.getStringExtra("category")

        val activities = ArrayList<Activity>()
        val databaseReference = FirebaseDatabase.getInstance().getReference("activity")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val activity = dataSnapshot.getValue(Activity::class.java)
                    if (activity?.category.equals(getCategory)){
                        activity?.let { activities.add(it) }
                    }
                }

                rv_data.layoutManager = LinearLayoutManager(applicationContext)
                rv_data.adapter = ActivityAdapter(applicationContext, activities){
                    startActivity<ActivityDetailActivity>(
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
    }
}
