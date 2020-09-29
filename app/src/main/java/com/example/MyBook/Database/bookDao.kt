package com.example.MyBook.Database
import androidx.room.*
import com.example.MyBook.Clases.Book
import com.example.MyBook.Clases.User

@Dao
public interface bookDao {

    @Query("SELECT * FROM book ORDER BY idbook")
    fun loadAllBooks(): MutableList<Book?>?

    @Query("SELECT * FROM book WHERE category = :category ORDER BY idbook")
    fun loadBookbyCategory(category: String): MutableList<Book?>?

    @Query("SELECT MAX(idbook) AS maxid FROM book")
    fun getMaxIndex(): Int?

    @Query("DELETE FROM book WHERE idbook = :id")
    fun deleteById(id: Int?)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book?)

    @Update
    fun updateBook(book: Book?)

    @Delete
    fun delete(book: Book?)
}
