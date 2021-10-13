package com.forkis.exchange.presenter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forkis.exchange.R
import com.forkis.exchange.model.Currencies

class CurrencyAdapter(var currencies: MutableList<Currencies>): RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.list_of_currencies, parent, false)
        return CurrencyHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        val currency = currencies[0][position]
        holder.currencyName.text = currency.curAbbreviation
        holder.amountOfValue.text = currency.curScale.toString()
        holder.currencyFullName.text = currency.curName
        holder.todayCurrency.text = currency.curOfficialRate.toString()
        holder.tomorrowCurrency.text = currencies[1][position].curOfficialRate.toString()
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    class CurrencyHolder(view: View): RecyclerView.ViewHolder(view){
        val currencyName = view.findViewById<TextView>(R.id.currency_name)
        val amountOfValue = view.findViewById<TextView>(R.id.amount_of_value)
        val currencyFullName = view.findViewById<TextView>(R.id.currency_full_name)
        val todayCurrency = view.findViewById<TextView>(R.id.today_currency)
        val tomorrowCurrency = view.findViewById<TextView>(R.id.tomorrow_currency)
    }
}