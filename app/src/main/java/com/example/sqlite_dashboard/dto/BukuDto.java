package com.example.sqlite_dashboard.dto;

public class BukuDto {
    public String kode;
    public String judul;
    public String pengarang;
    public String penerbit;
    public String isbn;

    public BukuDto(String kode, String judul, String pengarang, String penerbit, String isbn) {
        this.kode = kode;
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.isbn = isbn;
    }
}
