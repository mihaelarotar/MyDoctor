package com.example.mydoctor2.activities;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.example.mydoctor2.R;
import com.example.mydoctor2.data.User;
import com.example.mydoctor2.data.UserDatabase;
import com.example.mydoctor2.data.UserDatabaseClient;

public class PersonalDataActivity extends AppCompatActivity {
    private EditText name, kg, height, temp, puls, blood, bmi;
    private Spinner gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
        name = findViewById(R.id.numeInput);
        kg = findViewById(R.id.kgInput);
        height = findViewById(R.id.heightInput);
        gender = findViewById(R.id.genInput);
        temp = findViewById(R.id.tempInput);
        puls = findViewById(R.id.pulsInput);
        blood = findViewById(R.id.bloodInput);
        bmi = findViewById(R.id.bmiInput);
        Button updateData = findViewById(R.id.updateData);
        Button saveData = findViewById(R.id.saveData);

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.isEnabled();
                kg.isEnabled();
                height.isEnabled();
                gender.isEnabled();
                temp.isEnabled();
                puls.isEnabled();
                blood.isEnabled();
                bmi.isEnabled();

                name.isClickable();
                kg.isClickable();
                height.isClickable();
                gender.isClickable();
                temp.isClickable();
                puls.isClickable();
                blood.isClickable();
                bmi.isClickable();

                name.isInEditMode();
                kg.isInEditMode();
                height.isInEditMode();
                gender.isInEditMode();
                temp.isInEditMode();
                puls.isInEditMode();
                blood.isInEditMode();
                bmi.isInEditMode();
            }
        });

//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String username = userName.getText().toString();
//                String email = RegisterActivity.this.email.getText().toString();
//                String pass = password.getText().toString();
//
//                if (validateInputs(username, email, pass))
//                {
//                    RegisterActivity.RegisterUserTask registerUserTask = new RegisterActivity.RegisterUserTask(username, email, pass);
//                    registerUserTask.execute();
//                }
//
//            }
//        });
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

        if (PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, getString(R.string.email_not_valid), Toast.LENGTH_SHORT).show();
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

//            if (isOkay) {
//                Toast.makeText(RegisterActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
//                SharedPref sharedPref = SharedPref.getInstance();
//                sharedPref.setUser(RegisterActivity.this, user);
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//            } else {
//                Toast.makeText(RegisterActivity.this, "This email is already using by someone else", Toast.LENGTH_SHORT).show();
//            }
//
        }
    }
}
