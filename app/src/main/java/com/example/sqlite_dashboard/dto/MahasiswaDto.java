package com.example.sqlite_dashboard.dto;

public class MahasiswaDto {
    public String nim;
    public String nama;
    public String jenisKelamin;
    public String alamat;
    public String email;

    public MahasiswaDto(String nim, String nama, String jenisKelamin, String alamat, String email) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.email = email;
    }
}
