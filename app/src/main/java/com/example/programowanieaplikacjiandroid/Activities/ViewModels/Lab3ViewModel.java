package com.example.programowanieaplikacjiandroid.Activities.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.programowanieaplikacjiandroid.Data.Models.Phone;
import com.example.programowanieaplikacjiandroid.Data.Repositories.PhoneRepository;

import java.util.List;

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

    public void deleteAll(){

    }

}
