package com.example.programowanieaplikacjiandroid.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.programowanieaplikacjiandroid.Data.Dao.PhoneDao;
import com.example.programowanieaplikacjiandroid.Data.Database;
import com.example.programowanieaplikacjiandroid.Data.Models.Phone;

import java.util.List;

public class PhoneRepository {
    private PhoneDao dao;
    LiveData<List<Phone>> phones;

    public PhoneRepository(Application app) {
        Database db = Database.getDatabase(app);
        dao = db.phoneDao();
        phones = dao.selectAll();
    }

    public LiveData<List<Phone>> getAllPhones(){
        return phones;
    }


}
