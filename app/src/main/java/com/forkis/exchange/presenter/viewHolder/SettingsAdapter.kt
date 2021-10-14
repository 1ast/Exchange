package com.forkis.exchange.presenter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forkis.exchange.R
import com.forkis.exchange.model.CurrenciesItem
import com.forkis.exchange.presenter.Listener.SettingsAdapterListener
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsAdapter(var currencies: Pair<ArrayList<CurrenciesItem>, ArrayList<CurrenciesItem>>, val settingsAdapterListener: SettingsAdapterListener): RecyclerView.Adapter<SettingsAdapter.SettingsHolder>() {
    class SettingsHolder(view: View, val settingsAdapterListener: SettingsAdapterListener): RecyclerView.ViewHolder(view), View.OnClickListener{
        val currencyName = view.findViewById<TextView>(R.id.settings_currency_name)
        val amountOfValue = view.findViewById<TextView>(R.id.settings_amount_of_value)
        val currencyFullName = view.findViewById<TextView>(R.id.setting_currency_full_name)
        val currencySwitcher = view.findViewById<SwitchMaterial>(R.id.settings_currency_switcher)
        val currencyReorder = view.findViewById<ImageView>(R.id.settings_currency_reorder)
        init {
            currencySwitcher.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            settingsAdapterListener.settingsAdapterItemClicked(adapterPosition, currencySwitcher.isChecked)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.list_of_settings, parent, false)
        return SettingsHolder(layoutInflater, settingsAdapterListener)
    }

    override fun onBindViewHolder(holder: SettingsHolder, position: Int) {
        holder.currencyName.text = currencies.first[position].curAbbreviation
        holder.amountOfValue.text = currencies.first[position].curScale.toString()
        holder.currencyFullName.text = currencies.first[position].curName
        if (currencies.second[position].curID == 0){
            holder.currencySwitcher.isChecked = false
        }
    }

    override fun getItemCount(): Int {
        return currencies.first.size
    }
}