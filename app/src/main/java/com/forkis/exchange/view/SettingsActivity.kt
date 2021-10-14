package com.forkis.exchange.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.forkis.exchange.model.Currencies
import com.forkis.exchange.model.CurrenciesItem
import com.forkis.exchange.presenter.Listener.SettingsAdapterListener
import com.forkis.exchange.presenter.viewHolder.SettingsAdapter
import com.google.android.material.appbar.MaterialToolbar

import java.util.Collections

import android.view.Menu
import com.forkis.exchange.R


class SettingsActivity : AppCompatActivity(), SettingsAdapterListener {

    private lateinit var settingsActionBar: MaterialToolbar
    private lateinit var grayLine: ConstraintLayout
    private lateinit var settingsList: RecyclerView
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
        }

        initialise()



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.apply -> {
                val intent = Intent(this, MainActivity::class.java)
                Log.d("APPLY", currency.toString())
                intent.putExtra("First", currency.first)
                intent.putExtra("Second", currency.second)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun settingsAdapterItemClicked(adapterPosition: Int, isChecked: Boolean) {
        when (isChecked) {
            false -> {
                currency.second[adapterPosition].curID = 0
            }
            true -> {
                currency.second[adapterPosition].curID = currency.first[adapterPosition].curID
            }
        }
    }



    /**
     * Initialise parts of layout in Activity
     */
    private fun initialise() {
        settingsActionBar = findViewById(R.id.settings_action_bar)
        grayLine = findViewById(R.id.gray_line)
        settingsList = findViewById(R.id.settings_list)
        settingsAdapter = SettingsAdapter(currency, this)
        settingsList.adapter = settingsAdapter

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(settingsList)

        setActionBar()



    }

    /**
     * Callback, that realises drag&drop function
     */
    private val simpleCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            Collections.swap(currency.first, fromPosition, toPosition)
            Collections.swap(currency.second, fromPosition, toPosition)
            recyclerView.adapter!!.notifyItemMoved(fromPosition, toPosition)
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
    }


    /**
     * Set action bar to the Activity
     */
    private fun setActionBar(actionBar: MaterialToolbar = settingsActionBar) {
        setSupportActionBar(actionBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



}