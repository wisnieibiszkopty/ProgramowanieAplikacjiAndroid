package com.example.programowanieaplikacjiandroid.Data.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.programowanieaplikacjiandroid.Data.Models.Phone;

import java.util.List;

@Dao
public interface PhoneDao {
    @Insert
    void addPhone(Phone phone);

    @Insert
    void insertPhones(Phone... phones);

    @Update
    void updatePhone(Phone phone);

    @Query("SELECT * FROM phone")
    LiveData<List<Phone>> selectAll();

    @Delete
    void deletePhone(Phone phone);

    @Query("DELETE FROM phone")
    void deleteAll();

}
