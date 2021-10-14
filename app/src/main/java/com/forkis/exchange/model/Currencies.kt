package com.forkis.exchange.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

class Currencies() : ArrayList<CurrenciesItem>(), Parcelable{
    constructor(parcel: Parcel) : this() {
        readFromParcel(parcel)
    }

    private fun readFromParcel(parcel: Parcel) {
        this.clear()


        val size = parcel.readInt()
        for (i in 0 until size) {

            val r = CurrenciesItem(parcel.readString()!!,
                parcel.readInt(),
                parcel.readString()!!,
                parcel.readDouble(),
                parcel.readInt(),
                parcel.readString()!!)

            this.add(r)
        }
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        val size = this.size
        parcel.writeInt(size)

        for (i in 0 until size) {
            val r: CurrenciesItem = this[i]
            parcel.writeString(r.curAbbreviation)
            parcel.writeInt(r.curID)
            parcel.writeString(r.curName)
            parcel.writeDouble(r.curOfficialRate)
            parcel.writeInt(r.curScale)
            parcel.writeString(r.date)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Currencies> {
        override fun createFromParcel(parcel: Parcel): Currencies {
            return Currencies(parcel)
        }

        override fun newArray(size: Int): Array<Currencies?> {
            return arrayOfNulls(size)
        }
    }
}