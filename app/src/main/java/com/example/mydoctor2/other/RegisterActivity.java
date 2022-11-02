package com.example.mydoctor2.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydoctor2.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, password, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        userName = findViewById(R.id.UserNameLogin);
        password = findViewById(R.id.PasswordLogin);
        etEmail = findViewById(R.id.register_email);
        ImageView btnBack = findViewById(R.id.imageView);
        Button btnRegister = findViewById(R.id.register_button);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = userName.getText().toString();
                String email = etEmail.getText().toString();
                String pass = password.getText().toString();

//                if (!validateInputs(username, email, password)) return;
//
//                RegisterUserTask registerUserTask = new RegisterUserTask(username, email, password);
//                registerUserTask.execute();
            }
        });
    }

    public void back(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
