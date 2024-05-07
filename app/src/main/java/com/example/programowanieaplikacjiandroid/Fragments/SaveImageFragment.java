package com.example.programowanieaplikacjiandroid.Fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.programowanieaplikacjiandroid.Fragments.placeholder.PaintingContent;
import com.example.programowanieaplikacjiandroid.R;

import java.io.File;
import java.io.FileOutputStream;

public class SaveImageFragment extends Fragment {
    private int width;
    private int height;
    private EditText filenameElement;

    public SaveImageFragment() {
        // Required empty public constructor
    }

    public static SaveImageFragment newInstance() {
        return new SaveImageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            width = getArguments().getInt("width");
            height = getArguments().getInt("height");
            Log.d("wymiary - ", width + " " + height);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_image, container, false);

        filenameElement = view.findViewById(R.id.image_filename);

        Button saveButton = view.findViewById(R.id.save_image_button);
        saveButton.setOnClickListener(v -> onSaveImage());

        return view;
    }

    public void onSaveImage(){
        String filename = filenameElement.getText().toString();

        if(filename == null){
            Toast.makeText(getContext(), "Enter valid filename", Toast.LENGTH_LONG).show();
        } else {
            saveCanva(filename);
        }
    }

    public boolean saveCanva(String filename){
        String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        // robimy folder LAB5 na rysunki
        File dir = new File(imagesDir + File.separator + "LAB5");
        if (!dir.exists()){
            if (!dir.mkdirs()) {
                Log.e("ERROR", dir.toString());
                return false;
            }
        }
        // dodanie folderu do sciezki zapisu pliku
        imagesDir += File.separator + "LAB5";

        // co odpalenie apki bez zmiany parametru filename w Lab5Activity będą się nadpisywac, chyba spoko mniej kasowania śmeici z telefonu
        filename +=  "_" + PaintingContent.getPaintingItems().size() + ".jpg";
        // filename +=  "rysunek_" + UUID.randomUUID().toString() + ".jpg";
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // Canvas temp = new Canvas(bitmap);
        // drawCanva(temp);
        PaintingContent.PaintingItem paintingItem = new PaintingContent.PaintingItem(filename, imagesDir + "/" + filename);

        File file = new File(imagesDir, filename);
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            PaintingContent.addItem(paintingItem);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}