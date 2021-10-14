package com.forkis.exchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.forkis.exchange.model.Currencies
import com.forkis.exchange.model.CurrenciesItem
import com.forkis.exchange.presenter.Listener.SettingsAdapterListener
import com.forkis.exchange.presenter.viewHolder.SettingsAdapter
import com.google.android.material.appbar.MaterialToolbar

class SettingsActivity : AppCompatActivity(), SettingsAdapterListener {

    lateinit var settingsActionBar: MaterialToolbar
    lateinit var grayLine: ConstraintLayout
    lateinit var settingsList: RecyclerView
    private lateinit var settingsAdapter: SettingsAdapter
    private lateinit var currency: Pair<ArrayList<CurrenciesItem>, ArrayList<CurrenciesItem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        currency = Pair(Currencies(), Currencies())
        val bundle = intent.extras
        val currenciesFirst: ArrayList<CurrenciesItem>
        val currenciesSecond: ArrayList<CurrenciesItem>
        if (bundle!=null) {
            currenciesFirst = intent.getSerializableExtra("first") as ArrayList<CurrenciesItem>
            currenciesSecond = intent.getSerializableExtra("second") as ArrayList<CurrenciesItem>
            currency = Pair(currenciesFirst, currenciesSecond)
            Log.d("SETTINGS", currency.toString())
        }

        initialise()



    }


    fun initialise() {
        settingsActionBar = findViewById(R.id.settings_action_bar)
        grayLine = findViewById(R.id.gray_line)
        settingsList = findViewById(R.id.settings_list)
        settingsAdapter = SettingsAdapter(currency, this)
        settingsList.adapter = settingsAdapter
        setActionBar()
    }


    fun setActionBar(actionBar: MaterialToolbar = settingsActionBar) {
        setSupportActionBar(actionBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)

                intent.putExtra("First", currency.first)
                intent.putExtra("Second", currency.second)
                setResult(RESULT_OK, intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun settingsAdapterItemClicked(adapterPosition: Int, isChecked: Boolean) {
        when (isChecked) {
            false -> {
                currency.second[adapterPosition].curID = 0
            }
            true -> {
                Log.d("SWITCH", currency.first[adapterPosition].curID.toString())
                currency.second[adapterPosition].curID = currency.first[adapterPosition].curID
                Log.d("SWITCH", currency.second[adapterPosition].curID.toString())
            }
        }
        Log.d("SETTINGS", currency.toString())
    }
}