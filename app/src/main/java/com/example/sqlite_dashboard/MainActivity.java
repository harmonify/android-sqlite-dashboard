package com.example.sqlite_dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite_dashboard.repository.UserRepository;

public class MainActivity extends AppCompatActivity {
    private EditText formUsername, formPassword, formRepassword;
    private Button buttonSignUp, buttonLoginPage;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formUsername = findViewById(R.id.form_username);
        formPassword = findViewById(R.id.form_password);
        formRepassword = findViewById(R.id.form_confirm_password);

        buttonSignUp = findViewById(R.id.button_sign_up);
        buttonLoginPage = findViewById(R.id.button_login_page);

        userRepository = new UserRepository(this);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = formUsername.getText().toString();
                String password = formPassword.getText().toString();
                String repassword = formRepassword.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
                    Toast.makeText(MainActivity.this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(MainActivity.this, "Password tidak sesuai dengan konfirmasi password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userRepository.checkUsername(username)) {
                    Toast.makeText(MainActivity.this, "Data user sudah ada", Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean insertResult = userRepository.createUser(username, password);
                if (!insertResult) {
                    Toast.makeText(MainActivity.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(MainActivity.this, "Registrasi sukses", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        buttonLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}