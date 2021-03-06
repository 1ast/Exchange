package com.forkis.exchange.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.forkis.exchange.model.Currencies
import com.forkis.exchange.presenter.setter.SetCurrency
import com.forkis.exchange.presenter.setter.SetDate
import com.forkis.exchange.presenter.viewHolder.CurrencyAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView
import android.net.ConnectivityManager
import com.forkis.exchange.R
import com.forkis.exchange.model.CurrenciesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(){

    private lateinit var mainActionBar: MaterialToolbar
    private lateinit var oopsText: TextView
    private lateinit var todayDate: MaterialTextView
    private lateinit var tomorrowDate: MaterialTextView
    private lateinit var dateLayout: ConstraintLayout
    private lateinit var currencyList: RecyclerView

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView

    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var currency: Pair<Currencies, Currencies>
    private lateinit var currencies: Pair<ArrayList<CurrenciesItem>, ArrayList<CurrenciesItem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialise()

    }

    override fun onResume() {
        super.onResume()

        val date = SetDate()
        date.setTomorrowDate(todayDate, tomorrowDate)
        if (currencies.first.size == 0) {
            GlobalScope.launch (Dispatchers.IO){
                currency = SetCurrency.loadData(date, oopsText, progressBar, progressText, currency, currencyAdapter, todayDate, tomorrowDate)
                currencies = createCurrencies(currency)

            }
        } else {
            val secondaryCurrencies = SetCurrency.setCurrency(currencies)
            currencyAdapter.currencies = secondaryCurrencies
            currencyAdapter.notifyDataSetChanged()
        }

        if (currencies.first.size == 0){
            setVisibility(oopsText, false)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        if (!isOnline()) {
            menu?.findItem(R.id.action_settings)?.isVisible = false

        }
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.putExtra("first", currencies.first)
                intent.putExtra("second", currencies.second)
                resultLauncher.launch(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }



    /**
     * Register value to use in StartActivityForResult
     */
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            if (data != null){
                val currenciesFirst = data.getSerializableExtra("First") as? ArrayList<CurrenciesItem>
                val currenciesSecond = data.getSerializableExtra("Second") as? ArrayList<CurrenciesItem>
                if (currenciesFirst != null && currenciesSecond != null) {
                    currencies = Pair(currenciesFirst, currenciesSecond)
                }
            }
        }
    }


    /**
     * Initialise parts of layout in Activity
     */
    private fun initialise(){
        mainActionBar = findViewById(R.id.main_action_bar)
        oopsText = findViewById(R.id.oops_text)
        todayDate = findViewById(R.id.today_date)
        tomorrowDate = findViewById(R.id.tomorrow_date)
        dateLayout = findViewById(R.id.date_layout)

        progressBar = findViewById(R.id.main_progress_bar)
        progressText = findViewById(R.id.loading)

        currencyList = findViewById(R.id.currency_list)

        currency = Pair(Currencies(), Currencies())
        currencies = createCurrencies(currency)
        currencyAdapter = CurrencyAdapter(currencies)
        currencyList.adapter = currencyAdapter

        setVisibility(todayDate, false)
        setVisibility(tomorrowDate, false)

        val dividerItemDecoration = DividerItemDecoration(currencyList.context, LinearLayoutManager.VERTICAL)

        currencyList.addItemDecoration(dividerItemDecoration)

        setActionBar()
    }


    /**
     * Check if App have connection to the internet
     */
    private fun isOnline(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null &&
                cm.activeNetworkInfo!!.isConnectedOrConnecting
    }



    /**
     * Set action bar to the Activity
     */
    private fun setActionBar(actionBar: MaterialToolbar = mainActionBar){
        setSupportActionBar(actionBar)
    }


    companion object {
        /**
         * Set visibility of object
         * @param view = view, that will be visible/invisible
         * @param isVisible = if true, view will be visible, else invisible
         */
        fun setVisibility(view: View, isVisible: Boolean) {
            view.visibility = if (isVisible) View.VISIBLE
            else View.INVISIBLE
        }


        /**
         * Create Pair<ArrayList<CurrenciesItem>, ArrayList<CurrenciesItem>> from Pair<Currencies, Currencies>
         */
        fun createCurrencies(currency: Pair<Currencies, Currencies>): Pair<ArrayList<CurrenciesItem>, ArrayList<CurrenciesItem>> {
            val currencyFirst = arrayListOf<CurrenciesItem>()
            val currencySecond = arrayListOf<CurrenciesItem>()
            for (i in currency.first) {
                currencyFirst.add(
                    CurrenciesItem(
                        i.curAbbreviation, i.curID,
                        i.curName, i.curOfficialRate, i.curScale, i.date
                    )
                )
            }

            for (i in currency.second) {
                currencySecond.add(
                    CurrenciesItem(
                        i.curAbbreviation, i.curID,
                        i.curName, i.curOfficialRate, i.curScale, i.date
                    )
                )
            }
            return Pair(currencyFirst, currencySecond)
        }

    }


}