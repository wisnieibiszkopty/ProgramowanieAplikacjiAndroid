package com.example.programowanieaplikacjiandroid.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.programowanieaplikacjiandroid.R;
import com.example.programowanieaplikacjiandroid.Fragments.placeholder.PaintingContent;

import java.util.List;

public class PaintingFragmentList extends Fragment {

    public PaintingFragmentList() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
//        wczytanie listy obrazkow
        List<PaintingContent.PaintingItem> paintingItemList = PaintingContent.getPaintingItems();
//        ustawienie adaptera
        PaintingAdapter paintingAdapter = new PaintingAdapter(paintingItemList, getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.paintingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(paintingAdapter);

        paintingAdapter.setItemClickListener(position -> {
            PaintingFragmentDetails fragmentList = PaintingFragmentDetails.newInstance(position);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, fragmentList)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}