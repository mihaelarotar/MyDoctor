package com.example.mydoctor2.other;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydoctor2.MainActivity;
import com.example.mydoctor2.R;


public class LoginActivity extends AppCompatActivity {


    private EditText userName, password;

    @Override
    protected void onStart() {
        super.onStart();

//        SharedPref sharedPref = SharedPref.getInstance();
//        if (sharedPref.getUser(this) != null) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.splashScreenTheme);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.UserNameLogin);
        password = findViewById(R.id.PasswordLogin);
        TextView signUpWriting = findViewById(R.id.registerYou);
        Button btnLogin = findViewById(R.id.LoginButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = userName.getText().toString();
                String pass = password.getText().toString();

               // if (!validaInputs(username, password)) return;

//                LoginUserTask ut = new LoginUserTask(username, password);
//                ut.execute();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

        signUpWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });


    }
}


