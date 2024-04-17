package com.example.programowanieaplikacjiandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programowanieaplikacjiandroid.Activities.ViewModels.Lab3ViewModel;
import com.example.programowanieaplikacjiandroid.Adapters.PhoneAdapter;
import com.example.programowanieaplikacjiandroid.Data.Models.Phone;
import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.databinding.ActivityLab3Binding;

public class Lab3Activity extends AppCompatActivity implements PhoneAdapter.OnItemClickListener{
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

        // basic setup
        binding  = ActivityLab3Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // actionbar configuration
        setSupportActionBar(binding.phoneToolbar);
        getSupportActionBar().setTitle("Laboratorium 3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // creating recycler view for phones
        RecyclerView recyclerView = binding.phoneList;
        phoneAdapter = new PhoneAdapter(this);
        recyclerView.setAdapter(phoneAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.SimpleCallback callback =
            new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    viewModel.deletePhone(position);
                }
        };

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        // providing view model and using it to show all phones
        viewModel = new ViewModelProvider(this).get(Lab3ViewModel.class);
        viewModel.getAllPhones().observe(this, phones -> phoneAdapter.setPhoneList(phones));

        binding.fabAddPhone.setOnClickListener(v ->
            activityResultLauncher.launch(new Intent(this, InsertPhoneActivity.class))
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.phone_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_phones) {
            viewModel.deleteAllPhones();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // handling new activity for creating phones
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    handleActivityResult(data);
                }
            }
    );

    public void handleActivityResult(Intent data){
        if(data != null && data.getExtras().getBoolean("hasPhone")){
            Phone phone = data.getParcelableExtra("phone");

            if(data.getBooleanExtra("edited", false)){
                viewModel.updatePhone(phone);
            } else {
                viewModel.insertPhone(phone);
            }
        }
    }

    @Override
    public void onItemClickListener(Phone phone) {
        Intent intent = new Intent(this, InsertPhoneActivity.class);
        intent.putExtra("phone", phone);
        activityResultLauncher.launch(intent);
    }
}