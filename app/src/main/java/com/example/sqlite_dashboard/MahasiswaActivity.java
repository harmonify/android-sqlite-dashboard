package com.example.sqlite_dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite_dashboard.dto.MahasiswaDto;
import com.example.sqlite_dashboard.dto.UpdateMahasiswaDto;
import com.example.sqlite_dashboard.repository.MahasiswaRepository;

public class MahasiswaActivity extends AppCompatActivity {
    EditText formNim, formNama, formJK, formAlamat, formEmail;
    Button buttonCreate, buttonShow, buttonUpdate, buttonDelete, buttonClearForms;
    MahasiswaRepository mahasiswaRepository;

    public MahasiswaActivity() {
        this.mahasiswaRepository = new MahasiswaRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        formNim = findViewById(R.id.form_nim);
        formNama = findViewById(R.id.form_nama);
        formJK = findViewById(R.id.form_jenis_kelamin);
        formAlamat = findViewById(R.id.form_alamat);
        formEmail = findViewById(R.id.form_email);
        buttonCreate = findViewById(R.id.button_create);
        buttonShow = findViewById(R.id.button_show);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);
        buttonClearForms = findViewById(R.id.button_clear_forms);


        buttonCreate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nim = formNim.getText().toString();
                        String nama = formNama.getText().toString();
                        String jenisKelamin = formJK.getText().toString();
                        String alamat = formAlamat.getText().toString();
                        String email = formEmail.getText().toString();

                        if (!MahasiswaActivity.checkStringsNotEmpty(nim, nama, jenisKelamin, alamat, email)) {
                            Toast.makeText(MahasiswaActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (mahasiswaRepository.existsByNim(nim)) {
                            Toast.makeText(MahasiswaActivity.this, "NIM already exists!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        MahasiswaDto mahasiswaDto = new MahasiswaDto(
                                nim,
                                nama,
                                jenisKelamin,
                                alamat,
                                email
                        );

                        Boolean isCreationSuccess = mahasiswaRepository.create(mahasiswaDto);

                        if (!isCreationSuccess) {
                            Toast.makeText(MahasiswaActivity.this, "Failed to create!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(MahasiswaActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), BiodataActivity.class);
//                        startActivity(intent);
                    }
                }
        );
        
        buttonDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nim = formNim.getText().toString();


                        if (!MahasiswaActivity.checkStringsNotEmpty(nim)) {
                            Toast.makeText(MahasiswaActivity.this, "NIM field must be filled", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Boolean isDeleteSuccess = mahasiswaRepository.deleteByNim(nim);
                        if (isDeleteSuccess) {
                            Toast.makeText(MahasiswaActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            MahasiswaActivity.clearForms(formNim, formNama, formJK, formAlamat, formEmail);
                        } else {
                            Toast.makeText(MahasiswaActivity.this, "Failed to delete!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        buttonUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nim = formNim.getText().toString();
                        String nama = formNama.getText().toString();
                        String jenisKelamin = formJK.getText().toString();
                        String alamat = formAlamat.getText().toString();
                        String email = formEmail.getText().toString();

                        if (!MahasiswaActivity.checkStringsNotEmpty(nim, nama, jenisKelamin, alamat, email)) {
                            Toast.makeText(MahasiswaActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!mahasiswaRepository.existsByNim(nim)) {
                            Toast.makeText(MahasiswaActivity.this, "Mahasiswa not found!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        UpdateMahasiswaDto updateMahasiswaDto = new UpdateMahasiswaDto(
                                nama,
                                jenisKelamin,
                                alamat,
                                email
                        );

                        Boolean isUpdateSuccess = mahasiswaRepository.updateByNim(nim, updateMahasiswaDto);

                        if (!isUpdateSuccess) {
                            Toast.makeText(MahasiswaActivity.this, "Failed to update!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(MahasiswaActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        buttonShow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nim = formNim.getText().toString();

                        if (!MahasiswaActivity.checkStringsNotEmpty(nim)) {
                            Toast.makeText(MahasiswaActivity.this, "NIM field must be filled", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        MahasiswaDto mahasiswaDto = mahasiswaRepository.getByNim(nim);

                        if (mahasiswaDto == null) {
                            Toast.makeText(MahasiswaActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        formNama.setText(mahasiswaDto.nama);
                        formJK.setText(mahasiswaDto.jenisKelamin);
                        formAlamat.setText(mahasiswaDto.alamat);
                        formEmail.setText(mahasiswaDto.email);
                    }
                }
        );

        buttonClearForms.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            clearForms(formNim, formNama, formJK, formAlamat, formEmail);
                            Toast.makeText(MahasiswaActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(MahasiswaActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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