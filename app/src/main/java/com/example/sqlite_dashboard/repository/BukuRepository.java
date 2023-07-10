package com.example.sqlite_dashboard.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlite_dashboard.dto.BukuDto;
import com.example.sqlite_dashboard.dto.UpdateBukuDto;

public class BukuRepository extends BaseRepository {
    public static final String TABLE_NAME = "buku";

    public BukuRepository(Context context) {
        super(context);
    }

    public Boolean create(BukuDto buku) {
        SQLiteDatabase db = null;
        Boolean isCreationSuccess = false;
        ContentValues values = new ContentValues();

        values.put("kode", buku.kode);
        values.put("judul", buku.judul);
        values.put("pengarang", buku.pengarang);
        values.put("penerbit", buku.penerbit);
        values.put("isbn", buku.isbn);

        try {
            db = this.getWritableDatabase();
            long newRowId = db.insert(BukuRepository.TABLE_NAME, null, values);
            isCreationSuccess = newRowId > 0;
        } finally {
            // Close the database
            if (db != null) {
                db.close();
            }
        }

        return isCreationSuccess;
    }

    public Boolean existsByKode(String kode) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Boolean isExist = false;

        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM buku WHERE kode=?", new String[]{kode});
            isExist = cursor.getCount() > 0;
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }

        return isExist;
    }

    public BukuDto getByKode(String kode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        BukuDto buku = null;

        try {
            String[] columns = {"kode", "judul", "pengarang", "penerbit", "isbn"};
            String selection = "kode = ?";
            String[] selectionArgs = {kode};

            cursor = db.query(BukuRepository.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

            // Get the column indices
            int kodeIndex = cursor.getColumnIndex("kode");
            int judulIndex = cursor.getColumnIndex("judul");
            int pengarangIndex = cursor.getColumnIndex("pengarang");
            int penerbitIndex = cursor.getColumnIndex("penerbit");
            int isbnIndex = cursor.getColumnIndex("isbn");

            // Check if the cursor has a result
            if (cursor.moveToFirst()) {
                // Retrieve values of the specified fields
                String judul = cursor.getString(judulIndex);
                String pengarang = cursor.getString(pengarangIndex);
                String penerbit = cursor.getString(penerbitIndex);
                String index = cursor.getString(isbnIndex);

                buku = new BukuDto(kode, judul, pengarang, penerbit, index);
            }
        } finally {
            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }

            // Close the database
            db.close();
        }

        return buku;
    }

    public Boolean updateByKode(String kode, UpdateBukuDto updateBukuDto) {
        SQLiteDatabase db = null;
        Boolean isUpdateSuccess = false;

        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("judul", updateBukuDto.judul);
            values.put("pengarang", updateBukuDto.pengarang);
            values.put("penerbit", updateBukuDto.penerbit);
            values.put("isbn", updateBukuDto.isbn);

            String whereClause = "kode = ?";
            String[] whereArgs = {kode};

            int affectedRows = db.update(BukuRepository.TABLE_NAME, values, whereClause, whereArgs);
            isUpdateSuccess = affectedRows > 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return isUpdateSuccess;
    }

    public Boolean deleteByKode(String kode) {
        SQLiteDatabase db = null;
        Boolean isDeleteSuccess = false;

        try {
            db = this.getWritableDatabase();

            String whereClause = "kode = ?";
            String[] whereArgs = {kode};

            int affectedRows = db.delete(BukuRepository.TABLE_NAME, whereClause, whereArgs);
            isDeleteSuccess = affectedRows > 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return isDeleteSuccess;
    }
}
