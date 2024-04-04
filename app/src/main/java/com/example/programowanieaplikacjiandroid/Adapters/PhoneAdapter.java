package com.example.programowanieaplikacjiandroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programowanieaplikacjiandroid.Data.Models.Phone;
import com.example.programowanieaplikacjiandroid.R;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneHolder> {
    //private LayoutInflater inflater;
    private final Activity activity;
    private List<Phone> phoneList;

    public PhoneAdapter(Activity context) {
        //inflater = LayoutInflater.from(context);
        activity = context;
        phoneList = null;
    }

    @NonNull
    @Override
    public PhoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowRootView = activity.getLayoutInflater()
                .inflate(R.layout.phone_component, parent, false);
        return new PhoneAdapter.PhoneHolder(rowRootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneHolder holder, int position) {
        Phone phone = phoneList.get(position);
        holder.bindData(phone.getModel(), phone.getProducer());
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
        private final TextView modelText;
        private final TextView producerText;


        public PhoneHolder(View itemView) {
            super(itemView);
            modelText = itemView.findViewById(R.id.phone_model);
            producerText = itemView.findViewById(R.id.phone_producer);

        }

        public void bindData(String model, String producer){
            modelText.setText(model);
            producerText.setText(producer);
        }
    }

}
