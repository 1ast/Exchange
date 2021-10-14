package com.forkis.exchange.presenter.Listener

interface SettingsAdapterListener {
    /**
     * Listener to SettingsAdapter for switch
     */
    fun settingsAdapterItemClicked(adapterPosition: Int, isChecked: Boolean)

}