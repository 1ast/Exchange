package com.forkis.exchange.presenter.setter

import android.widget.TextView
import org.joda.time.DateTime
import java.util.*

class SetDate {
    var todayDateTime = DateTime(Date())
    var yesterdayDateTime = todayDateTime.minusDays(1)
    var tomorrowDateTime = todayDateTime.plusDays(1)

    /**
     * Set date if we have tomorrow currency
     * @param today today TextView
     * @param tomorrow tomorrow TextView
     */
    fun setTomorrowDate(today: TextView, tomorrow: TextView){
        val todayText = "${todayDateTime.dayOfMonth}.${todayDateTime.monthOfYear}.${todayDateTime.year}"
        val tomorrowText = "${tomorrowDateTime.dayOfMonth}.${tomorrowDateTime.monthOfYear}.${tomorrowDateTime.year}"

        today.text = todayText
        tomorrow.text = tomorrowText

    }

    /**
     * Set date if we haven't tomorrow currency
     * @param yesterday yesterday/today TextView
     * @param today today/tomorrow TextView
     */
    fun setYesterdayDate(yesterday: TextView, today: TextView){
        val todayText = "${yesterdayDateTime.dayOfMonth}.${yesterdayDateTime.monthOfYear}.${yesterdayDateTime.year}"
        val tomorrowText = "${todayDateTime.dayOfMonth}.${todayDateTime.monthOfYear}.${todayDateTime.year}"
        yesterday.text = todayText
        today.text = tomorrowText

    }
}