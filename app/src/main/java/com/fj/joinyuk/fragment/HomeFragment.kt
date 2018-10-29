package com.fj.joinyuk.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.fj.joinyuk.activity.ActivityDetailActivity
import com.fj.joinyuk.R
import com.fj.joinyuk.adapter.ActivityAdapter
import com.fj.joinyuk.model.Activity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.smarteist.autoimageslider.SliderLayout
import kotlinx.android.synthetic.main.fragment_home.view.*
import com.smarteist.autoimageslider.SliderView
import org.jetbrains.anko.startActivity
import java.util.ArrayList

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.img_slider.setIndicatorAnimation(SliderLayout.Animations.FILL)
        view.img_slider.scrollTimeInSec = 1

        setSliderViews(view)

        val activities = ArrayList<Activity>()
        val databaseReference = FirebaseDatabase.getInstance().getReference("activity")

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

    private fun setSliderViews(view: View) {
        for (i in 0..3) {
            val sliderView = SliderView(context)

            when (i) {
                0 -> sliderView.imageUrl = "https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                1 -> sliderView.imageUrl = "https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                2 -> sliderView.imageUrl = "https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"
                3 -> sliderView.imageUrl = "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            sliderView.setDescription("Welcome to Join Yuk!")

            view.img_slider.addSliderView(sliderView)
        }
    }
}
