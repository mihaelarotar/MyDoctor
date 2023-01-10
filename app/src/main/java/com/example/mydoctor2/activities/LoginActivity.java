package com.example.mydoctor2.activities;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydoctor2.MainActivity;
import com.example.mydoctor2.R;
import com.example.mydoctor2.data.User;
import com.example.mydoctor2.data.UserDatabase;
import com.example.mydoctor2.data.UserDatabaseClient;
import com.example.mydoctor2.other.SharedPref;

import java.util.ArrayList;


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

                if (validaInputs(username, pass)){

                    LoginUserTask ut = new LoginUserTask(username, pass);
                    ut.execute();
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
            }
        });

        signUpWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });


    }

    private boolean validaInputs(String username, String password) {

        if (username.isEmpty()) {
            Toast.makeText(this, getString(R.string.username_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

class LoginUserTask extends AsyncTask<Void, Void, Void> {

    private final String username;
    private final String password;
    private ArrayList<User> users = new ArrayList<>();

    public LoginUserTask(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());
        users = (ArrayList<User>) databaseClient.userDao().observeAllUser();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        for (User user : users) {
            if (username.equals(user.getUsername()))
                if(password.equals(user.getPassword())) {

                SharedPref sharedPref = SharedPref.getInstance();
                sharedPref.setUser(LoginActivity.this, user);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                return;
            }
            else
                {
                    Toast.makeText(LoginActivity.this, "Password is wrong!", Toast.LENGTH_SHORT).show();
                }
        }
        Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();

    }
}
}


