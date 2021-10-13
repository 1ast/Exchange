package com.forkis.exchange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class SettingsActivity : AppCompatActivity() {

    lateinit var settingsActionBar: MaterialToolbar
    lateinit var grayLine: ConstraintLayout
    lateinit var settingsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initialise()
    }

    fun initialise() {
        settingsActionBar = findViewById(R.id.settings_action_bar)
        grayLine = findViewById(R.id.gray_line)
        settingsList = findViewById(R.id.settings_list)
        setActionBar()
    }


    fun setActionBar(actionBar: MaterialToolbar = settingsActionBar) {
        setSupportActionBar(actionBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}