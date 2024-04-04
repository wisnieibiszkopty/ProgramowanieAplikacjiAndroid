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

    public void insertPhone(Phone phone){
        repository.addPhone(phone);
    }

    public void updatePhone(Phone phone){
        repository.updatePhone(phone);
    }

    public void deleteAllPhones(){
        repository.deleteAllPhones();
    }

}
