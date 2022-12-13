package com.example.mydoctor2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mydoctor2.activities.LoginActivity;
import com.example.mydoctor2.data.Sex;
import com.example.mydoctor2.data.User;
import com.example.mydoctor2.data.UserDatabase;
import com.example.mydoctor2.data.UserDatabaseClient;
import com.example.mydoctor2.databinding.ActivityMainBinding;
import com.example.mydoctor2.other.SharedPref;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private EditText name, kg, height, temp, puls, blood, bmi;
    private Spinner gender;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPref sharedPref = SharedPref.getInstance();
        //aici iau userul logat
        User user = SharedPref.getInstance().getUser(this);

        System.out.println(user);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_detalii, R.id.nav_gallery, R.id.nav_calendar)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Spinner mySpinner = (Spinner) findViewById(R.id.genInput);
        mySpinner.setAdapter(new ArrayAdapter<Sex>(this, android.R.layout.simple_spinner_item, Sex.values()));

        //logout button
        navigationView.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(menuItem -> {
            sharedPref.clearSharedPref(MainActivity.this);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        });

        name = findViewById(R.id.numeInput);
        if(!user.getName().matches(""))
            name.setText(user.getName());

        kg = findViewById(R.id.kgInput);
        if(user.getKg() != 0)
            kg.setText(""+user.getKg());

        height = findViewById(R.id.heightInput);
        if(user.getHeight() != 0)
            height.setText(""+user.getHeight());

        gender = mySpinner;

        temp = findViewById(R.id.tempInput);
        if(user.getTemperature() != 0)
            temp.setText(""+user.getTemperature());

        puls = findViewById(R.id.pulsInput);
        if(user.getPulse() != 0)
            puls.setText(""+user.getPulse());

        blood = findViewById(R.id.bloodInput);
        if(user.getBloodPressure() != 0)
            blood.setText(""+user.getBloodPressure());

        bmi = findViewById(R.id.bmiInput);
        if(user.getBodyIndex() != 0)
            bmi.setText("33");//+user.getBodyIndex()

        Button updateData = findViewById(R.id.updateData);
        Button saveData = findViewById(R.id.saveData);

        //deblocam filedurile pt update
        updateData.setOnClickListener(view -> {
            name.setEnabled(true);
            kg.setEnabled(true);
            height.setEnabled(true);
            gender.setEnabled(true);
            temp.setEnabled(true);
            puls.setEnabled(true);
            blood.setEnabled(true);

            name.setClickable(true);
            kg.setClickable(true);
            height.setClickable(true);
            gender.setClickable(true);
            temp.setClickable(true);
            puls.setClickable(true);
            blood.setClickable(true);

            name.setFocusable(true);
            kg.setFocusable(true);
            height.setFocusable(true);
            gender.setFocusable(true);
            temp.setFocusable(true);
            puls.setFocusable(true);
            blood.setFocusable(true);

            name.setFocusableInTouchMode(true);
            kg.setFocusableInTouchMode(true);
            height.setFocusableInTouchMode(true);
            gender.setFocusableInTouchMode(true);
            temp.setFocusableInTouchMode(true);
            puls.setFocusableInTouchMode(true);
            blood.setFocusableInTouchMode(true);

            Toast.makeText(MainActivity.this, getString(R.string.editable), Toast.LENGTH_SHORT).show();

        });

        saveData.setOnClickListener(view -> {

            String nameInput2 = name.getText().toString();
            String kgInput2 = kg.getText().toString();
            String heightInput2 = height.getText().toString();
            String tempInput2 = temp.getText().toString();
            String pulsInput2 = puls.getText().toString();
            String bloodInput2 = blood.getText().toString();

            //if (validateInputs(heightInput2, tempInput2, pulsInput2))
            // {
            name.setEnabled(false);
            kg.setEnabled(false);
            height.setEnabled(false);
            gender.setEnabled(false);
            temp.setEnabled(false);
            puls.setEnabled(false);
            blood.setEnabled(false);
            bmi.setEnabled(false);

            name.setClickable(false);
            kg.setClickable(false);
            height.setClickable(false);
            gender.setClickable(false);
            temp.setClickable(false);
            puls.setClickable(false);
            blood.setClickable(false);
            bmi.setClickable(false);

            name.setFocusable(false);
            kg.setFocusable(false);
            height.setFocusable(false);
            gender.setFocusable(false);
            temp.setFocusable(false);
            puls.setFocusable(false);
            blood.setFocusable(false);
            bmi.setFocusable(false);

            name.setFocusableInTouchMode(false);
            kg.setFocusableInTouchMode(false);
            height.setFocusableInTouchMode(false);
            gender.setFocusableInTouchMode(false);
            temp.setFocusableInTouchMode(false);
            puls.setFocusableInTouchMode(false);
            blood.setFocusableInTouchMode(false);
            bmi.setFocusableInTouchMode(false);

            //save into database
            String nameToUpdate;
            int kgToUpdate;
            int heightToUpdate;
            Sex genToUpdate;
            int tempToUpdate;
            int pulsToUpdate;
            int bloodToUpdate;

            nameToUpdate = name.getText().toString();

            if(kgInput2.matches(""))
                kgToUpdate = 0;
            else
                kgToUpdate = Integer.parseInt(kg.getText().toString());

            if(heightInput2.matches(""))
                heightToUpdate = 0;
            else
                heightToUpdate = Integer.parseInt(heightInput2);

            if(tempInput2.matches(""))
                tempToUpdate = 0;
            else
                tempToUpdate = Integer.parseInt(tempInput2);

            if(pulsInput2.matches(""))
                pulsToUpdate = 0;
            else
                pulsToUpdate = Integer.parseInt(pulsInput2);

            if(bloodInput2.matches(""))
                bloodToUpdate = 0;
            else
                bloodToUpdate = Integer.parseInt(bloodInput2);

            System.out.println(user + "  " + nameToUpdate + "  " + kgToUpdate + "  " +
                    heightToUpdate+ "  " + gender.getSelectedItem()+ "  " +
                    tempToUpdate+ "  " +pulsToUpdate+ "  " + bloodToUpdate);


            saveData(user, nameToUpdate, kgToUpdate, heightToUpdate, (Sex) gender.getSelectedItem(),
                    tempToUpdate, pulsToUpdate, bloodToUpdate);

            if(user.getBodyIndex() != 0)
                bmi.setText(""+user.getBodyIndex());
            //colorare

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private boolean validateInputs(String height, String temp, String puls) {

        if (Integer.parseInt(height) < 30 || Integer.parseInt(height) > 250) {
            Toast.makeText(this, getString(R.string.wrong_height), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Double.parseDouble(temp) < 36.0 || Double.parseDouble(temp) > 42.0) {
            Toast.makeText(this, getString(R.string.wrong_temp), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Integer.parseInt(puls) < 30 || Integer.parseInt(puls) > 150) {
            Toast.makeText(this, getString(R.string.wrong_puls), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveData(User user, String name, int kg, int height, Sex gen, int temp, int puls, int blood) {
        user.setName(name);
        user.setKg(kg);
        user.setHeight(height);
        user.setSex(gen);
        user.setTemperature(temp);
        user.setPulse(puls);
        user.setBloodPressure(blood);
        if (kg != 0 && height != 0) {
            user.setBodyIndex2(kg, height);
        } else {
            user.setBodyIndex3();
        }
        UpdateDetailsTask updateDetailsTask = new UpdateDetailsTask(user);
        updateDetailsTask.execute();
    }

        class UpdateDetailsTask extends AsyncTask<Void, Void, Void> {

        private User user;

        UpdateDetailsTask(User user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());
            databaseClient.userDao().updateUser(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        }
    }



}