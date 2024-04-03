package com.example.programowanieaplikacjiandroid.Data.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.programowanieaplikacjiandroid.Data.Models.Phone;

import java.util.List;

@Dao
public interface PhoneDao {

    @Query("SELECT p.id, p.producer, p.model FROM phone p")
    List<Phone> selectAll();

    @Delete
    void deleteAll(Phone... phones);

}
