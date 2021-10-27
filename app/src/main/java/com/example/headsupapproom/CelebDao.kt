package com.example.headsupapproom

import androidx.room.*

//step 5: create Dao interface (Data Access Objects interface)
//this interface is used to access database in better and modular way as compared to query builders or direct queries.

// 1) in this Dao interface you should use @Dao annotation to define the interface as Dao
@Dao
interface CelebDao {
    //2) define your methods to interact with the data in your app's database.

    //method to get all data:
    @Query("SELECT * FROM Celebrities")
    fun getAll(): List<CelebTable>

    //method to get all data:
    @Query("SELECT * FROM Celebrities  where ID=:id")
    fun getByID(id:Int): CelebTable

    //insert a row into the table
    @Insert
    fun insertCeleb(celeb :CelebTable)

    //update note
    @Update
    fun updateCeleb(celeb: CelebTable)

    //delete note row
    @Delete
    fun delCeleb(celeb: CelebTable)

}