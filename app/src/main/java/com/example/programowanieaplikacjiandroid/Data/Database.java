package com.example.programowanieaplikacjiandroid.Data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.programowanieaplikacjiandroid.Data.Dao.PhoneDao;
import com.example.programowanieaplikacjiandroid.Data.Models.Phone;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {
        Phone.class
}, version=1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract PhoneDao phoneDao();
    private static volatile Database INSTANCE;

    public static Database getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (Database.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        Database.class,
                        "phone_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return  INSTANCE;
    }

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                PhoneDao phoneDao = INSTANCE.phoneDao();
                // add phones
                phoneDao.insertPhones(
                    new Phone("Samsung", "Galaxy", "S24", "cs.pollub.pl"),
                    new Phone("Xiaomi", "Redmi Note", "13 Pro", "cs.pollub.pl"),
                    new Phone("Apple", "iPhone", "15", "cs.pollub.pl")
                );
            });
        }
    };
}
