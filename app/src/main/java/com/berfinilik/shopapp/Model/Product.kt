package com.berfinilik.shopapp.Model

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val id: Int,
    val description: String,
    val picUrl: String,
    val price: Int,
    val rating: Double,
    val size: List<String>,
    val title: String,
    var favorite: Boolean,
    var numberInCart: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readDouble(),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(description)
        parcel.writeString(picUrl)
        parcel.writeInt(price)
        parcel.writeDouble(rating)
        parcel.writeStringList(size)
        parcel.writeString(title)
        parcel.writeByte(if (favorite) 1 else 0)
        parcel.writeInt(numberInCart)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}


