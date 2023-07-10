package com.example.sqlite_dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite_dashboard.repository.UserRepository;

public class LoginActivity extends AppCompatActivity {
    private EditText formUsername, formPassword;
    private Button buttonSignIn, buttonRegisterPage;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        formUsername = findViewById(R.id.form_username);
        formPassword = findViewById(R.id.form_password);
        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonRegisterPage = findViewById(R.id.button_register_page);

        userRepository = new UserRepository(this);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = formUsername.getText().toString();
                String password = formPassword.getText().toString();

                if (!userRepository.checkUserPassword(username, password)) {
                    Toast.makeText(LoginActivity.this, "Login gagal, username atau password Anda salah", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        buttonRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}