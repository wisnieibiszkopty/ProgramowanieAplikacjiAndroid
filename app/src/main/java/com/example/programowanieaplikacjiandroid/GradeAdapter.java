package com.example.programowanieaplikacjiandroid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeHolder> {
    public List<Grade> grades;
    private LayoutInflater pump;

    public GradeAdapter(Activity context, List<Grade> grades){
        this.grades = grades;
        pump = context.getLayoutInflater();
    }

    public static class GradeHolder extends RecyclerView.ViewHolder {
        private String name = "test";
        private Integer grade = 2;

        public GradeHolder(View view){
            super(view);

            Map<RadioButton, Integer> radioButtons = new HashMap<>();
            radioButtons.put(view.findViewById(R.id.grade_2), 2);
            radioButtons.put(view.findViewById(R.id.grade_3), 3);
            radioButtons.put(view.findViewById(R.id.grade_4), 4);
            radioButtons.put(view.findViewById(R.id.grade_5), 5);

            for (Map.Entry<RadioButton, Integer> radioButton : radioButtons.entrySet()){
                if(radioButton.getKey().isChecked()){
                    grade = radioButton.getValue();
                }
            }
        }

        public void bindData(Grade g){
            g.setName(name);
            g.setGrade(grade);
        }
//
//        public TextView getProductName() {
//            return productName;
//        }
//
//        public TextView getProductDescription() {
//            return productDescription;
//        }
    }

    @NonNull
    @Override
    public GradeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grade_component, parent, false);
        return new GradeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeHolder holder, int position) {
        Grade grade = grades.get(position);
        holder.bindData(grade);
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

}
