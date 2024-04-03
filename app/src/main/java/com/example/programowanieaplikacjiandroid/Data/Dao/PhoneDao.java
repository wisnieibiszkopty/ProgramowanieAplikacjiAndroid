package com.example.programowanieaplikacjiandroid.Data.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.programowanieaplikacjiandroid.Data.Models.Phone;

import java.util.List;

@Dao
public interface PhoneDao {
    @Insert
    void addPhone(Phone phone);

    @Insert
    void insertPhones(Phone... phones);

    @Query("SELECT p.id, p.producer, p.model FROM phone p")
    LiveData<List<Phone>> selectAll();

    @Delete
    void deleteAll(Phone... phones);

}
