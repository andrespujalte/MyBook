package com.example.MyBook.Clases

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

@Entity(tableName = "book")
class Book(
    idbook: Int,
    bookname: String,
    authorname: String,
    publisher : String,
    isbn: String,
    category: String,
    imageurl: String,
    synopsys: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idbook")
    var idbook: Int
    @ColumnInfo(name = "bookname")
    var bookname: String
    @ColumnInfo(name = "authorname")
    var authorname: String
    @ColumnInfo(name = "publisher")
    var publisher: String
    @ColumnInfo(name = "isbn")
    var isbn: String
    @ColumnInfo(name = "category")
    var category: String
    @ColumnInfo(name = "imageurl")
    var imageurl: String
    @ColumnInfo(name = "synopsys")
    var synopsys: String
    init {
        this.idbook = idbook
        this.bookname = bookname
        this.authorname = authorname
        this.publisher = publisher
        this.isbn = isbn
        this.category = category
        this.imageurl = imageurl
        this.synopsys = synopsys
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(idbook)
        writeString(bookname)
        writeString(authorname)
        writeString(publisher)
        writeString(isbn)
        writeString(category)
        writeString(imageurl)
        writeString(synopsys)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}