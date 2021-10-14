package com.forkis.exchange.presenter.setter

import android.util.Log
import android.widget.TextView
import org.joda.time.DateTime
import java.util.*

class SetDate {
    var todayDateTime = DateTime(Date())
    var yesterdayDateTime = todayDateTime.minusDays(1)
    var tomorrowDateTime = todayDateTime.plusDays(1)
    fun setTomorrowDate(today: TextView, tomorrow: TextView){
        val todayText = "${todayDateTime.dayOfMonth}.${todayDateTime.monthOfYear}.${todayDateTime.year}"
        val tomorrowText = "${tomorrowDateTime.dayOfMonth}.${tomorrowDateTime.monthOfYear}.${tomorrowDateTime.year}"
        Log.d("TODAY", todayText)
        Log.d("TOMORROW", tomorrowText)

        today.text = todayText
        tomorrow.text = tomorrowText

    }
    fun setYesterdayDate(today: TextView, tomorrow: TextView){
        val todayText = "${yesterdayDateTime.dayOfMonth}.${yesterdayDateTime.monthOfYear}.${yesterdayDateTime.year}"
        val tomorrowText = "${todayDateTime.dayOfMonth}.${todayDateTime.monthOfYear}.${todayDateTime.year}"
        Log.d("TODAY", todayText)
        Log.d("TOMORROW", tomorrowText)

        today.text = todayText
        tomorrow.text = tomorrowText

    }
}