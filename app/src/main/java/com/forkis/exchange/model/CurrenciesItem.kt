package com.forkis.exchange.model
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CurrenciesItem(
    @SerializedName("Cur_Abbreviation")
    val curAbbreviation: String,
    @SerializedName("Cur_ID")
    var curID: Int,
    @SerializedName("Cur_Name")
    val curName: String,
    @SerializedName("Cur_OfficialRate")
    val curOfficialRate: Double,
    @SerializedName("Cur_Scale")
    val curScale: Int,
    @SerializedName("Date")
    val date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(curAbbreviation)
        p0?.writeInt(curID)
        p0?.writeString(curName)
        p0?.writeDouble(curOfficialRate)
        p0?.writeInt(curScale)
        p0?.writeString(date)
    }

    companion object CREATOR : Parcelable.Creator<CurrenciesItem> {
        override fun createFromParcel(parcel: Parcel): CurrenciesItem {
            return CurrenciesItem(parcel)
        }

        override fun newArray(size: Int): Array<CurrenciesItem?> {
            return arrayOfNulls(size)
        }
    }
}