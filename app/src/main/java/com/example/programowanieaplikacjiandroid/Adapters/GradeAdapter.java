package com.example.programowanieaplikacjiandroid.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programowanieaplikacjiandroid.Models.Grade;
import com.example.programowanieaplikacjiandroid.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeHolder> {
    private List<Grade> gradesList;
    private final Activity activity;

    // inicjalizacja zmiennych
    public GradeAdapter(Activity context, List<Grade> grades){
        this.gradesList = grades;
        activity = context;
    }

    // wywołane gdy tworzony jest nowy wiersz
    @NonNull
    @Override
    public GradeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowRootView = activity.getLayoutInflater()
                .inflate(R.layout.grade_component, parent, false);
        return new GradeHolder(rowRootView);
    }

    // wywołane gdy wiersz zostaje wyświetlony
    @Override
    public void onBindViewHolder(@NonNull GradeHolder holder, int position) {
        Grade grade = gradesList.get(position);
        holder.bindData(grade);
    }

    @Override
    public int getItemCount() {
        return gradesList.size();
    }

    public class GradeHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
        private TextView name;
        private Integer grade = 2;
        Map<Integer, Integer> radioButtons;

        public GradeHolder(View view){
            super(view);

            // TODO napraw layout

            name = view.findViewById(R.id.name);

            RadioGroup buttons = view.findViewById(R.id.buttons);
            buttons.setOnCheckedChangeListener(this);

            radioButtons = new HashMap<>();
            radioButtons.put(R.id.grade_2, 2);
            radioButtons.put(R.id.grade_3, 3);
            radioButtons.put(R.id.grade_4, 4);
            radioButtons.put(R.id.grade_5, 5);
        }

        public void bindData(Grade g){
            name.setText(g.getName());
            grade = g.getGrade();
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Integer value = radioButtons.get(checkedId);
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                gradesList.get(position).setGrade(value);
            }
        }
    }

}
