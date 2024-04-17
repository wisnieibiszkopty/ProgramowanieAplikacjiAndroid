package com.example.programowanieaplikacjiandroid.Activities;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private final SurfaceHolder surfaceHolder;
    private Thread drawingThread;
    private boolean isThreadWorking = false;
    private final Object blockade = new Object();

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public void startDrawing(){
        drawingThread = new Thread(this);
        isThreadWorking = true;
        drawingThread.start();
    }

    public void stopDrawing(){
        isThreadWorking = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();

        synchronized (blockade){

        }

        return true;
    }

    public boolean performClick(){
        return super.performClick();
    }

    @Override
    public void run() {

        while (isThreadWorking) {
            Canvas canva = null;
            try {
                // sekcja krytyczna - żaden inny wątek nie może używać pojemnika
                synchronized (surfaceHolder) {
                    // czy powierzchnia jest prawidłowa
                    if (!surfaceHolder.getSurface().isValid()) {
                        continue;
                    }
                    // zwraca kanwę, na której można rysować, każdy piksel
                    // kanwy w prostokącie przekazanym jako parametr musi być
                    // narysowany od nowa inaczej: rozpoczęcie edycji
                    // zawartości kanwy
                    canva = surfaceHolder.lockCanvas(null);
                    //sekcja krytyczna – dostęp do rysunku na wyłączność
                    synchronized (blockade) {
                        if (isThreadWorking) {
                            //rysowanie na lokalnej kanwie...
                        }
                    }
                }
            } finally {
                // w bloku finally - gdyby wystąpił wyjątek w powyższym                 
                // powierzchnia zostanie zostawiona w spójnym stanie
                if (canva != null) {
                    // koniec edycji kanwy i wyświetlenie rysunku na ekranie
                    surfaceHolder.unlockCanvasAndPost(canva);
                }
            }
            try {
                Thread.sleep(1000 / 25); // 25
            } catch (InterruptedException e) { 
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        isThreadWorking = false;
    }
}
