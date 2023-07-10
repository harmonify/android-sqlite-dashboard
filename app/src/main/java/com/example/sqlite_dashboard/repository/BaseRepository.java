package com.example.sqlite_dashboard.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseRepository extends SQLiteOpenHelper {
    private static final String DBNAME = "android_6A3.db";
    private static final int DATABASE_VERSION = 3;

    public BaseRepository(Context context) {
        super(context, BaseRepository.DBNAME, null, BaseRepository.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS mahasiswa(id INTEGER PRIMARY KEY AUTOINCREMENT, nim TEXT, nama TEXT, jenis_kelamin TEXT, alamat TEXT, email TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS buku(id INTEGER PRIMARY KEY AUTOINCREMENT, kode TEXT, judul TEXT, pengarang TEXT, penerbit TEXT, isbn TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int j) {
        sqLiteDatabase.execSQL("DROP TABLE users");
        sqLiteDatabase.execSQL("DROP TABLE mahasiswa");
        sqLiteDatabase.execSQL("DROP TABLE buku");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }
}
