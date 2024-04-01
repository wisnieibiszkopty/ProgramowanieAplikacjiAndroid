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
import com.example.programowanieaplikacjiandroid.Utils;

import java.util.ArrayList;
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
        private final Map<Integer, Integer> radioButtons;
        private final List<RadioButton> radioButtonsReferences;

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

            radioButtonsReferences = new ArrayList<>();
            radioButtonsReferences.add(view.findViewById(R.id.grade_2));
            radioButtonsReferences.add(view.findViewById(R.id.grade_3));
            radioButtonsReferences.add(view.findViewById(R.id.grade_4));
            radioButtonsReferences.add(view.findViewById(R.id.grade_5));
        }

        public void bindData(Grade g){
            name.setText(g.getName());
            grade = g.getGrade();

            // setting radio button checked depending on grade value
            Integer position = Utils.getKeyByValue(radioButtons, grade);
            for(RadioButton rb : radioButtonsReferences){
                if(rb.getId() == position){
                    rb.setChecked(true);
                    break;
                }
            }
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
