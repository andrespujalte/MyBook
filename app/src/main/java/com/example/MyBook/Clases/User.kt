package com.example.MyBook.Clases

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User(
    id: Int,
    userid: String,
    firstname: String,
    lastname: String,
    password: String,
    email: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int

    @ColumnInfo(name = "userid")
    var userid: String

    @ColumnInfo(name = "firstname")
    var firstname: String?

    @ColumnInfo(name = "lastname")
    var lastname: String?

    @ColumnInfo(name = "password")
    var password: String

    @ColumnInfo(name = "email")
    var email: String?

    init {
        this.id = id
        this.userid = userid
        this.firstname = firstname
        this.lastname = lastname
        this.password = password
        this.email = email
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(userid)
        writeString(firstname)
        writeString(lastname)
        writeString(password)
        writeString(email)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}