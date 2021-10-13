package com.forkis.exchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.forkis.exchange.presenter.viewHolder.CurrencyAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView
import org.joda.time.DateTime
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var mainActionBar: MaterialToolbar
    lateinit var oopsText: TextView
    lateinit var todayDate: MaterialTextView
    lateinit var tomorrowDate: MaterialTextView
    lateinit var dateLayout: ConstraintLayout
    lateinit var currencyList: RecyclerView
    lateinit var todayDateTime: DateTime
    lateinit var tomorrowDateTime: DateTime
    lateinit var yesterdayDateTime: DateTime
    lateinit var currencyAdapter: CurrencyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialise()
        setDate()
        chooseDate()
    }


    override fun onResume() {
        super.onResume()
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun initialise(){
        mainActionBar = findViewById(R.id.main_action_bar)
        oopsText = findViewById(R.id.oops_text)
        todayDate = findViewById(R.id.today_date)
        tomorrowDate = findViewById(R.id.tomorrow_date)
        dateLayout = findViewById(R.id.date_layout)
        currencyList = findViewById(R.id.currency_list)
        currencyAdapter = CurrencyAdapter(mutableListOf())
        setActionBar()
    }

    fun setDate(){
        todayDateTime = DateTime(Date())
        yesterdayDateTime = todayDateTime.minusDays(1)
        tomorrowDateTime = todayDateTime.plusDays(1)
    }

    fun chooseDate(){
        val todayText = "${todayDateTime.dayOfMonth()}.${todayDateTime.monthOfYear()}.${todayDateTime.year}"
        val tomorrowText = "${tomorrowDateTime.dayOfMonth()}.${tomorrowDateTime.monthOfYear()}.${tomorrowDateTime.year}"
        todayDate.text = todayText
        todayDate.text = tomorrowText
    }

    fun setActionBar(actionBar: MaterialToolbar = mainActionBar){
        setSupportActionBar(actionBar)
    }

}