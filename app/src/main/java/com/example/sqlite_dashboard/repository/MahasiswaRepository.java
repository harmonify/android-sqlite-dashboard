package com.example.sqlite_dashboard.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlite_dashboard.dto.MahasiswaDto;
import com.example.sqlite_dashboard.dto.UpdateMahasiswaDto;

import java.util.ArrayList;
import java.util.List;

public class MahasiswaRepository extends BaseRepository {
    public static final String TABLE_NAME = "mahasiswa";

    public MahasiswaRepository(Context context) {
        super(context);
    }

    public Boolean create(MahasiswaDto mahasiswa) {
        SQLiteDatabase db = null;
        Boolean isCreationSuccess = false;
        ContentValues values = new ContentValues();

        values.put("nim", mahasiswa.nim);
        values.put("nama", mahasiswa.nama);
        values.put("jenis_kelamin", mahasiswa.jenisKelamin);
        values.put("alamat", mahasiswa.alamat);
        values.put("email", mahasiswa.email);

        try {
            db = this.getWritableDatabase();
            long newRowId = db.insert(MahasiswaRepository.TABLE_NAME, null, values);
            isCreationSuccess = newRowId > 0;
        } finally {
            // Close the database
            if (db != null) {
                db.close();
            }
        }

        return isCreationSuccess;
    }

    public Boolean existsByNim(String nim) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Boolean isExist = false;

        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM mahasiswa WHERE nim=?", new String[]{nim});
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

    public List<MahasiswaDto> getAll() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<MahasiswaDto> mahasiswaList = new ArrayList<>();

        try {
            db = this.getReadableDatabase();
            String[] columns = {"nim", "nama", "jenis_kelamin", "alamat", "email"};
            cursor = db.query(MahasiswaRepository.TABLE_NAME, columns, null, null, null, null, null);

            // Get the column indices
            int nimIndex = cursor.getColumnIndex("nim");
            int namaIndex = cursor.getColumnIndex("nama");
            int jenisKelaminIndex = cursor.getColumnIndex("jenis_kelamin");
            int alamatIndex = cursor.getColumnIndex("alamat");
            int emailIndex = cursor.getColumnIndex("email");

            // Check if any required columns are missing in the cursor
            if (nimIndex == -1 || namaIndex == -1 || jenisKelaminIndex == -1 || alamatIndex == -1 || emailIndex == -1) {
                throw new IllegalStateException("One or more required columns are missing in the cursor");
            }

            // Iterate over the cursor to access the retrieved records
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve values of the specified fields
                    String nim = cursor.getString(nimIndex);
                    String nama = cursor.getString(namaIndex);
                    String jenisKelamin = cursor.getString(jenisKelaminIndex);
                    String alamat = cursor.getString(alamatIndex);
                    String email = cursor.getString(emailIndex);

                    MahasiswaDto mahasiswa = new MahasiswaDto(nim, nama, jenisKelamin, alamat, email);
                    mahasiswaList.add(mahasiswa);
                } while (cursor.moveToNext());
            }
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }

        return mahasiswaList;
    }

    public MahasiswaDto getByNim(String nim) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        MahasiswaDto mahasiswa = null;

        try {
            String[] columns = {"nim", "nama", "jenis_kelamin", "alamat", "email"};
            String selection = "nim = ?";
            String[] selectionArgs = {nim};

            cursor = db.query(MahasiswaRepository.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

            // Get the column indices
            int nimIndex = cursor.getColumnIndex("nim");
            int namaIndex = cursor.getColumnIndex("nama");
            int jenisKelaminIndex = cursor.getColumnIndex("jenis_kelamin");
            int alamatIndex = cursor.getColumnIndex("alamat");
            int emailIndex = cursor.getColumnIndex("email");

            // Check if the cursor has a result
            if (cursor.moveToFirst()) {
                // Retrieve values of the specified fields
                String nama = cursor.getString(namaIndex);
                String jenisKelamin = cursor.getString(jenisKelaminIndex);
                String alamat = cursor.getString(alamatIndex);
                String email = cursor.getString(emailIndex);

                mahasiswa = new MahasiswaDto(nim, nama, jenisKelamin, alamat, email);
            }
        } finally {
            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }

            // Close the database
            db.close();
        }

        return mahasiswa;
    }

    public Boolean updateByNim(String nim, UpdateMahasiswaDto updateMahasiswaDto) {
        SQLiteDatabase db = null;
        Boolean isUpdateSuccess = false;

        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("nama", updateMahasiswaDto.nama);
            values.put("jenis_kelamin", updateMahasiswaDto.jenisKelamin);
            values.put("alamat", updateMahasiswaDto.alamat);
            values.put("email", updateMahasiswaDto.email);

            String whereClause = "nim = ?";
            String[] whereArgs = {nim};

            int affectedRows = db.update(MahasiswaRepository.TABLE_NAME, values, whereClause, whereArgs);
            isUpdateSuccess = affectedRows > 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return isUpdateSuccess;
    }

    public Boolean deleteByNim(String nim) {
        SQLiteDatabase db = null;
        Boolean isDeleteSuccess = false;

        try {
            db = this.getWritableDatabase();

            String whereClause = "nim = ?";
            String[] whereArgs = {nim};

            int affectedRows = db.delete(MahasiswaRepository.TABLE_NAME, whereClause, whereArgs);
            isDeleteSuccess = affectedRows > 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return isDeleteSuccess;
    }
}
