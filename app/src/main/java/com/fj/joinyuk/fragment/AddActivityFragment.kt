package com.fj.joinyuk.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.fj.joinyuk.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_add_activity.*
import kotlinx.android.synthetic.main.fragment_add_activity.view.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class AddActivityFragment : Fragment(), View.OnClickListener {

    private lateinit var calendar: Calendar
    private lateinit var date: DatePickerDialog.OnDateSetListener
    private lateinit var getDate: String

    override fun onClick(v: View?) {
        when(v){
            btn_create -> {
                val name = edt_name.text.toString()
                val category = spinner_category.selectedItem.toString()
                val location = edt_location.text.toString()
                val date = edt_date.text.toString()
                val time = edt_time.text.toString()
                val capacity = edt_capacity.text.toString()
                val description = edt_description.text.toString()

                when{
                    name.isEmpty() -> edt_name.error = getString(R.string.must_be_filled)
                    category.equals("Choose Activity Category") -> context?.toast(getString(R.string.must_be_filled))
                    location.isEmpty() -> edt_location.error = getString(R.string.must_be_filled)
                    date.isEmpty() -> edt_date.error = getString(R.string.must_be_filled)
                    time.isEmpty() -> edt_time.error = getString(R.string.must_be_filled)
                    capacity.isEmpty() -> edt_capacity.error = getString(R.string.must_be_filled)
                    description.isEmpty() -> edt_description.error = getString(R.string.must_be_filled)
                    else -> {
                        val id = UUID.randomUUID()
                        val database = FirebaseDatabase
                                .getInstance()
                                .getReference("activity")
                                .child(id.toString())

                        database.child("name").setValue(name)
                        database.child("location").setValue(location)
                        database.child("category").setValue(category)
                        database.child("date").setValue(getDate)
                        database.child("time").setValue(time)
                        database.child("capacity").setValue(capacity.toInt())
                        database.child("description").setValue(description)
                        database.child("username").setValue("Naufal Prakoso") //TODO: Edit

                        edt_name.setText("")
                        edt_location.setText("")
                        edt_date.setText("")
                        edt_time.setText("")
                        edt_capacity.setText("")
                        edt_description.setText("")

                        context?.toast("Added Successfully")
                    }
                }
            }

            edt_time -> showTimeDialog()

            edt_date -> {
                DatePickerDialog(
                        context,
                        R.style.DialogTheme,
                        date,
                        2018,
                        1,
                        1
                ).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_activity, container, false)

        view.btn_create.setOnClickListener(this)

        val adapterPaymentType = ArrayAdapter.createFromResource(
                context,
                R.array.activity_category_array,
                android.R.layout.simple_spinner_item)
        adapterPaymentType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinner_category.adapter = adapterPaymentType

        view.edt_time.setOnClickListener(this)
        view.edt_date.setOnClickListener(this)

        calendar = Calendar.getInstance()
        date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        return view
    }

    private fun updateLabel() {
        val myFormat = "dd/MM/yy"
        val simpleDateFormat = SimpleDateFormat(myFormat, Locale.US)

        edt_date.setText(simpleDateFormat.format(calendar.time))
        getDate = simpleDateFormat.format(calendar.time)
    }

    @SuppressLint("SetTextI18n")
    private fun showTimeDialog() {
        val calendar = Calendar.getInstance()
        val timePickerDialog: TimePickerDialog

        timePickerDialog = TimePickerDialog(
                context,
                R.style.DialogTheme,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    edt_time.setText("$hourOfDay:$minute")
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(context)
        )

        timePickerDialog.show()
    }
}
