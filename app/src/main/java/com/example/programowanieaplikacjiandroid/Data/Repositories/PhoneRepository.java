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

    /*
        Nie wiem czy tak powinineme to robić
        żeby wszędzie dawać executora
     */

    public PhoneRepository(Application app) {
        Database db = Database.getDatabase(app);
        dao = db.phoneDao();
        phones = dao.selectAll();
    }

    public void addPhone(Phone phone){
        Database.databaseWriteExecutor.execute(() -> {
            dao.addPhone(phone);
        });
    }

    public LiveData<List<Phone>> getAllPhones(){
        return phones;
    }

    public void updatePhone(Phone phone){
        Database.databaseWriteExecutor.execute(() -> {
            dao.updatePhone(phone);
        });
    }

    public void deleteAllPhones(){
        // błąd - za dużo czasu zajmuje operacja i trzeba wrzucić do osobnego wątku
        // dlatego używam executora
        Database.databaseWriteExecutor.execute(() -> {
            dao.deleteAll();
        });
    }

}
