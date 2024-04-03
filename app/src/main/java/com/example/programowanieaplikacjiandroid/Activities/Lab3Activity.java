package com.example.programowanieaplikacjiandroid.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programowanieaplikacjiandroid.Activities.ViewModels.Lab3ViewModel;
import com.example.programowanieaplikacjiandroid.Adapters.PhoneAdapter;
import com.example.programowanieaplikacjiandroid.Data.Models.Phone;
import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityLab3Binding;

public class Lab3Activity extends AppCompatActivity {
    private ActivityLab3Binding binding;
    private Lab3ViewModel viewModel;
    private PhoneAdapter phoneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab3);

        //nie wiem co to ???
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding  = ActivityLab3Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.phoneToolbar);
        getSupportActionBar().setTitle("Laboratorium 3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RecyclerView recyclerView = binding.phoneList;
        //phoneAdapter = new PhoneAdapter(this);
        //recyclerView.setAdapter(phoneAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(Lab3ViewModel.class);
        viewModel.getAllPhones().observe(this, phones -> {
            for(Phone phone: phones){
                Log.i("phone", phone.toString());
            }
            //phoneAdapter.setPhoneList(phones);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.phone_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // don't work 😒
        if (item.getItemId() == 1000004) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllPhones(){

    }
}