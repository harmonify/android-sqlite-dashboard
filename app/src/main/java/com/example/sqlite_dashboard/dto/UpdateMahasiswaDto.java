package com.example.sqlite_dashboard.dto;

import org.jetbrains.annotations.Nullable;

public class UpdateMahasiswaDto extends MahasiswaDto {
    @Nullable
    private String nim;

    public UpdateMahasiswaDto(String nama, String jenisKelamin, String alamat, String email) {
        super(null, nama, jenisKelamin, alamat, email);
    }
}
