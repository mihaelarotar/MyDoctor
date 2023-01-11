package com.example.mydoctor2.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydoctor2.R;
import com.example.mydoctor2.data.User;
import com.example.mydoctor2.data.UserDatabase;
import com.example.mydoctor2.data.UserDatabaseClient;
import com.example.mydoctor2.other.SharedPref;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.register_user_name);
        password = findViewById(R.id.registerPassword);
        email = findViewById(R.id.register_email);
        ImageView btnBack = findViewById(R.id.backToLogin);
        Button btnRegister = findViewById(R.id.register_button);

        btnBack.setOnClickListener(view -> finish());

        btnRegister.setOnClickListener(view -> {

            String username = userName.getText().toString();
            String email = RegisterActivity.this.email.getText().toString();
            String pass = password.getText().toString();

            PasswordActivity passwordActivity = new PasswordActivity();
            String encryptedPass = "";
            try {
                 encryptedPass = passwordActivity.encrypt(pass);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (validateInputs(username, email, pass))
            {
                RegisterUserTask registerUserTask = new RegisterUserTask(username, email, encryptedPass);
                registerUserTask.execute();
            }

        });
    }

    private boolean validateInputs(String username, String email, String password) {



        if (username.isEmpty()) {
            Toast.makeText(this, getString(R.string.username_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, getString(R.string.email_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }


        if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 8) {
            Toast.makeText(this, getString(R.string.password_too_short), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @SuppressLint("StaticFieldLeak")
    class RegisterUserTask extends AsyncTask<Void, Void, Void> {

        private final String username;
        private final String email;
        private final String password;
        private User user;
        private boolean isOkay = true;

        public RegisterUserTask(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());

            user = new User(
                    username,
                    email,
                    password
            );

            try {
                databaseClient.userDao().insertUser(user);
            } catch (SQLiteConstraintException e) {
                isOkay = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (isOkay) {
                Toast.makeText(RegisterActivity.this, "User creat!", Toast.LENGTH_SHORT).show();
                SharedPref sharedPref = SharedPref.getInstance();
                sharedPref.setUser(RegisterActivity.this, user);
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Username-ul e folosit de cineva deja", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
