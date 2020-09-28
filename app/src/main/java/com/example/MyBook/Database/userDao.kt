package com.example.MyBook.Database
import androidx.room.*
import com.example.MyBook.Clases.User

@Dao
public interface userDao {

    @Query("SELECT * FROM user ORDER BY id")
    fun loadAllPersons(): MutableList<User?>?

    @Query("SELECT * FROM user WHERE userid = :userid ORDER BY id")
    fun getPasswordfromUserid(userid: String): User?

    @Query("SELECT * FROM user WHERE id = :id")
    fun loadPersonById(id: Int): User?

    @Query("SELECT * FROM user WHERE userid = :userid")
    fun loadPersonByUserId(userid: String): User?

    @Query("SELECT * FROM user WHERE userid = :email")
    fun loadPersonByemail(email: String): User?

    @Query("SELECT MAX(id) AS maxid FROM user")
    fun getMaxIndex(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(user: User?)

    @Update
    fun updatePerson(user: User?)

    @Delete
    fun delete(user: User?)



}
