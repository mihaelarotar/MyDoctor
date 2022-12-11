package com.example.mydoctor2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
import com.example.mydoctor2.databinding.ActivityMainBinding;
import com.example.mydoctor2.other.SharedPref;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private EditText name, kg, height, temp, puls, blood, bmi;
    private Spinner gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPref sharedPref = SharedPref.getInstance();
//        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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

        navigationView.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(menuItem -> {
            sharedPref.clearSharedPref(MainActivity.this);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        });

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

            }
        });

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameInput2 = name.getText().toString();
                String kgInput2 = kg.getText().toString();
                String heightInput2 = height.getText().toString();
                String tempInput2 = temp.getText().toString();
                String pulsInput2 = puls.getText().toString();
                String bloodInput2 = blood.getText().toString();

                if (validateInputs(heightInput2, tempInput2, pulsInput2))
                {
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

                    Toast.makeText(MainActivity.this, getString(R.string.saved), Toast.LENGTH_SHORT).show();

                    //save into database

                    //colorare
                }
            }
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

        if (Integer.parseInt(puls) < 30 || Integer.parseInt(puls)  > 150) {
            Toast.makeText(this, getString(R.string.wrong_puls), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}