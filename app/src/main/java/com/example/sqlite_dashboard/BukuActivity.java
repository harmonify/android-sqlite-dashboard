package com.example.sqlite_dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite_dashboard.dto.BukuDto;
import com.example.sqlite_dashboard.dto.UpdateBukuDto;
import com.example.sqlite_dashboard.repository.BukuRepository;

public class BukuActivity extends AppCompatActivity {
    EditText formKode, formJudul, formPengarang, formPenerbit, formIsbn;
    Button buttonCreate, buttonShow, buttonUpdate, buttonDelete, buttonClearForms;
    BukuRepository bukuRepository;

    public BukuActivity() {
        this.bukuRepository = new BukuRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku);

        formKode = findViewById(R.id.form_kode);
        formJudul = findViewById(R.id.form_judul);
        formPengarang = findViewById(R.id.form_pengarang);
        formPenerbit = findViewById(R.id.form_penerbit);
        formIsbn = findViewById(R.id.form_isbn);
        buttonCreate = findViewById(R.id.button_create);
        buttonShow = findViewById(R.id.button_show);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);
        buttonClearForms = findViewById(R.id.button_clear_forms);


        buttonCreate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String kode = formKode.getText().toString();
                        String judul = formJudul.getText().toString();
                        String pengarang = formPengarang.getText().toString();
                        String penerbit = formPenerbit.getText().toString();
                        String isbn = formIsbn.getText().toString();

                        if (!BukuActivity.checkStringsNotEmpty(kode, judul, pengarang, penerbit, isbn)) {
                            Toast.makeText(BukuActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (bukuRepository.existsByKode(kode)) {
                            Toast.makeText(BukuActivity.this, "Book code already exists!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        BukuDto bukuDto = new BukuDto(
                                kode,
                                judul,
                                pengarang,
                                penerbit,
                                isbn
                        );

                        Boolean isCreationSuccess = bukuRepository.create(bukuDto);

                        if (!isCreationSuccess) {
                            Toast.makeText(BukuActivity.this, "Failed to create!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(BukuActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        buttonDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String kode = formKode.getText().toString();


                        if (!BukuActivity.checkStringsNotEmpty(kode)) {
                            Toast.makeText(BukuActivity.this, "Book code field must be filled", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Boolean isDeleteSuccess = bukuRepository.deleteByKode(kode);
                        if (isDeleteSuccess) {
                            Toast.makeText(BukuActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            BukuActivity.clearForms(formKode, formJudul, formPengarang, formPenerbit, formIsbn);
                        } else {
                            Toast.makeText(BukuActivity.this, "Failed to delete!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        buttonUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String kode = formKode.getText().toString();
                        String judul = formJudul.getText().toString();
                        String pengarang = formPengarang.getText().toString();
                        String penerbit = formPenerbit.getText().toString();
                        String isbn = formIsbn.getText().toString();

                        if (!BukuActivity.checkStringsNotEmpty(kode, judul, pengarang, penerbit, isbn)) {
                            Toast.makeText(BukuActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!bukuRepository.existsByKode(kode)) {
                            Toast.makeText(BukuActivity.this, "Book not found!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        UpdateBukuDto updateBukuDto = new UpdateBukuDto(
                                judul,
                                pengarang,
                                penerbit,
                                isbn
                        );

                        Boolean isUpdateSuccess = bukuRepository.updateByKode(kode, updateBukuDto);

                        if (!isUpdateSuccess) {
                            Toast.makeText(BukuActivity.this, "Failed to update!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(BukuActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        buttonShow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String kode = formKode.getText().toString();

                        if (!BukuActivity.checkStringsNotEmpty(kode)) {
                            Toast.makeText(BukuActivity.this, "Book code field must be filled", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        BukuDto buku = bukuRepository.getByKode(kode);

                        if (buku == null) {
                            Toast.makeText(BukuActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        formJudul.setText(buku.judul);
                        formPengarang.setText(buku.pengarang);
                        formPenerbit.setText(buku.penerbit);
                        formIsbn.setText(buku.isbn);
                    }
                }
        );

        buttonClearForms.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            clearForms(formKode, formJudul, formPengarang, formPenerbit, formIsbn);
                            Toast.makeText(BukuActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(BukuActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                            System.out.println(e);
                        }
                    }
                }
        );
    }

    public static void clearForms(EditText... forms) {
        for (EditText form: forms) {
            form.setText("");
        }
    }

    public static boolean checkStringsNotEmpty(String... strings) {
        for (String str : strings) {
            if (str == null || TextUtils.isEmpty(str)) {
                return false;
            }
        }
        return true;
    }
}