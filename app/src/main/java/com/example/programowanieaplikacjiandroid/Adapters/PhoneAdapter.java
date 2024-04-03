package com.example.programowanieaplikacjiandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programowanieaplikacjiandroid.Data.Models.Phone;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneHolder> {
    private LayoutInflater inflater;
    private List<Phone> phoneList;

    public PhoneAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        phoneList = null;
    }

    @NonNull
    @Override
    public PhoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return phoneList != null ? phoneList.size() : 0;
    }

    public void setPhoneList(List<Phone> list){
        phoneList = list;
        // last resort 😭😭😭
        notifyDataSetChanged();
    }

    public class PhoneHolder extends RecyclerView.ViewHolder{

        public PhoneHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
