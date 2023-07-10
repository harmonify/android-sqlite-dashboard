package com.example.sqlite_dashboard.dto;

import org.jetbrains.annotations.Nullable;

public class UpdateBukuDto extends BukuDto {
    @Nullable
    private String kode;

    public UpdateBukuDto(String judul, String pengarang, String penerbit, String isbn) {
        super(null, judul, pengarang, penerbit, isbn);
    }
}
