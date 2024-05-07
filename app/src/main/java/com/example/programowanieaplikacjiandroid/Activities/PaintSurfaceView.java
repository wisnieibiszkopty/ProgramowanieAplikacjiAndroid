package com.example.programowanieaplikacjiandroid.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.programowanieaplikacjiandroid.Fragments.placeholder.PaintingContent;
import com.example.programowanieaplikacjiandroid.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PaintSurfaceView  extends SurfaceView implements SurfaceHolder.Callback {
    private static final int cirRad = 24;
    private int color = R.color.red;
    private Paint paint;
    private Paint dotPaint;
    private Path path;
    private Path dotPath;
    public ArrayList<Pair<Path,Paint>> paths;

    public PaintSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        getHolder().addCallback(this);
        
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(18);

        dotPaint = new Paint(paint);
        dotPaint.setStyle(Paint.Style.FILL);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        path = new Path();
        dotPath = new Path();
        paths = new ArrayList<>();

        setOnTouchListener((v, event) -> {
            synchronized (getHolder()){
                float X = event.getX();
                float Y = event.getY();
                paint.setColor(getResources().getColor(color));
                dotPaint.setColor(getResources().getColor(color));
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        path.reset();
                        path.moveTo(X, Y);
                        makeDot(X,Y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        path.lineTo(X, Y);
                        break;
                    case MotionEvent.ACTION_UP:
                        makeDot(X,Y);
                        paths.add(new Pair<>(new Path(path), new Paint(paint)));
                        break;
                }
                drawCanva(null);
                return true;
            }
        });

    }

    private void makeDot(float X, float Y) {
        dotPath.reset();
        dotPath.moveTo(X,Y);
        dotPath.addCircle(X,Y,cirRad,Path.Direction.CW);
        paths.add(new Pair<>(new Path(dotPath),new Paint(dotPaint)));
    }

    public void drawCanva(Canvas temp){
        synchronized (getHolder()){
            Canvas canvas = temp != null ? temp : getHolder().lockCanvas();
            if (canvas != null) {
                try {
                    // clearing screen
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    // drawing paths on new screen
                    for (Pair<Path,Paint> p : paths) {
                        canvas.drawPath(p.first, p.second);
                    }
                    // drawing current path
                    if (!path.isEmpty()){
                        canvas.drawPath(path,paint);
                    }
                } finally {
                    if (temp == null) {
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }

    public void setStrokeColor(int color) {
        this.color = color;
    }

    public void clearCanva() {
        synchronized (getHolder()) {
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    paths.clear();
                } finally {
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    Pair<Integer, Integer> getSize(){
        return new Pair<>(this.getWidth(), this.getHeight());
    }

    /**
     * Zapisuje canve do pliku w formacie JPEG w katalogu Pictures urządzenia
     * @param filename nazwa pliku jak zostanie zapisana canva, funkcja dodaje na koniec ".jpg"
     * @return czy zapisywanie sie powiodło
     */
    public boolean saveCanva(String filename){
        String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
//        robimy folder LAB5 na rysunki
        File dir = new File(imagesDir + File.separator + "LAB5");
        if (!dir.exists()){
            if (!dir.mkdirs()) {
                Log.e("ERROR", dir.toString());
                return false;
            }
        }
//        dodanie folderu do sciezki zapisu pliku
        imagesDir += File.separator + "LAB5";

//        co odpalenie apki bez zmiany parametru filename w Lab5Activity będą się nadpisywac, chyba spoko mniej kasowania śmeici z telefonu
        filename +=  "_" + PaintingContent.getPaintingItems().size() + ".jpg";
//        filename +=  "rysunek_" + UUID.randomUUID().toString() + ".jpg";
        Bitmap bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas temp = new Canvas(bitmap);
        drawCanva(temp);
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
