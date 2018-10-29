package com.fj.joinyuk.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fj.joinyuk.activity.FindDetailActivity
import com.fj.joinyuk.R
import kotlinx.android.synthetic.main.fragment_find_activity.*
import kotlinx.android.synthetic.main.fragment_find_activity.view.*
import org.jetbrains.anko.startActivity

class FindActivityFragment : Fragment(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v){
            cv_game -> context?.startActivity<FindDetailActivity>("category" to "Game")
            cv_sport -> context?.startActivity<FindDetailActivity>("category" to "Sport")
            cv_fun_activity -> context?.startActivity<FindDetailActivity>("category" to "Fun Activity")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_find_activity, container, false)

        view.cv_game.setOnClickListener(this)
        view.cv_sport.setOnClickListener(this)
        view.cv_fun_activity.setOnClickListener(this)

        return view
    }
}
