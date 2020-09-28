package com.example.MyBook.Database
import androidx.room.*
import com.example.MyBook.Clases.Book
import com.example.MyBook.Clases.User

@Dao
public interface bookDao {

    @Query("SELECT * FROM book ORDER BY idbook")
    fun loadAllBooks(): MutableList<Book?>?

    @Query("SELECT MAX(id) AS maxid FROM user")
    fun getMaxIndex(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book?)

    @Update
    fun updateBook(book: Book?)

    @Delete
    fun delete(book: Book?)
}
