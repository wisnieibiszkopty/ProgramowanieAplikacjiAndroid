package com.example.programowanieaplikacjiandroid.Activities.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.programowanieaplikacjiandroid.Data.Models.Phone;
import com.example.programowanieaplikacjiandroid.Data.Repositories.PhoneRepository;

import java.util.List;

// TODO
// Edycja rekordu
// kliknięcia w adapterze
// przekaż dane do InsertPhoneActivity
// zwracanie zmodyfikowanych danych
// update w DAO

// walidacja pola tekstowego

// kasowanie pojedyńczych rekordów, użyc ItemTouchHelper

public class Lab3ViewModel extends AndroidViewModel {
    private final PhoneRepository repository;
    private final LiveData<List<Phone>> phones;

    public Lab3ViewModel(@NonNull Application application) {
        super(application);
        repository = new PhoneRepository(application);
        this.phones = repository.getAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones(){
        return phones;
    }

    // co za id??????
    // tworzy się z 0, ale później się samo generuje
    public void insertPhone(Phone phone){
        Log.i("phone", phone.toStringAllArgs());
        repository.addPhone(phone);
    }

    public void deleteAllPhones(){
        repository.deleteAllPhones();
    }

}
