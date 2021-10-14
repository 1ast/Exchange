package com.forkis.exchange.presenter.setter

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import com.forkis.exchange.MainActivity.Companion.setVisibility
import com.forkis.exchange.model.Currencies
import com.forkis.exchange.presenter.downloader.ExchangeAPI
import com.forkis.exchange.presenter.viewHolder.CurrencyAdapter
import kotlinx.coroutines.*

class SetCurrency {
    companion object {
        @DelicateCoroutinesApi
        suspend fun loadData(
            date: SetDate, oopsText: TextView, progressBar: ProgressBar, progressText: TextView,
            currencies: Pair<Currencies, Currencies>, currencyAdapter: CurrencyAdapter,
            todayTextView: TextView, tomorrowTextView: TextView
        ): Pair<Currencies, Currencies> {
            val coroutineExceptionHandler = CoroutineExceptionHandler { _, t ->
                run {
                    t.printStackTrace()
                    Handler(Looper.getMainLooper()).post {
                        setVisibility(oopsText, true)
                        setVisibility(progressBar, false)
                        setVisibility(progressText, false)

                    }
                }
            }

            var currency = currencies
            val job = GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                Log.d("Coroutines", "Launched")
                val exchangeAPI = ExchangeAPI()
                var isComplete = true
                val todayCurrency = exchangeAPI.getRates(
                    "${date.todayDateTime.year}-${date.todayDateTime.monthOfYear}-${date.todayDateTime.dayOfMonth}",
                )
                if (todayCurrency.isSuccessful) {
                    currency = currency.copy(first = todayCurrency.body()!!)
                } else {
                    isComplete = false
                }

                val tomorrowCurrency = exchangeAPI.getRates(
                    "${date.tomorrowDateTime.year}-${date.tomorrowDateTime.monthOfYear}-${date.tomorrowDateTime.dayOfMonth}",
                )
                var haveYesterday = false
                if (tomorrowCurrency.isSuccessful) {
                    currency = currency.copy(second = tomorrowCurrency.body()!!)
                    if (currency.second.size == 0) {
                        haveYesterday = true
                    }
                } else {
                    isComplete = false
                }

                if (haveYesterday) {
                    val yesterdayCurrency = exchangeAPI.getRates(
                        "${date.yesterdayDateTime.year}-${date.yesterdayDateTime.monthOfYear}-${date.yesterdayDateTime.dayOfMonth}",
                    )

                    if (yesterdayCurrency.isSuccessful) {
                        currency = currency.copy(
                            first = yesterdayCurrency.body()!!,
                            second = todayCurrency.body()!!
                        )
                        Handler(Looper.getMainLooper()).post {
                            date.setYesterdayDate(todayTextView, tomorrowTextView)
                        }
                    } else {
                        isComplete = false
                    }
                }

                if (isComplete) {
                    Handler(Looper.getMainLooper()).post {
                        currencyAdapter.currencies = currency
                        currencyAdapter.notifyItemChanged(0)
                        Log.d("adapter", currencyAdapter.currencies.toString())
                        setVisibility(progressBar, false)
                        setVisibility(progressText, false)
                        setVisibility(todayTextView, true)
                        setVisibility(tomorrowTextView, true)
                    }
                }
                Log.d("SETCURRENCY", currency.toString())
            }
            job.join()
            return currency
        }

    }
}