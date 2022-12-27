package com.example.mydoctor2.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mydoctor2.R;
import com.example.mydoctor2.data.Sex;
import com.example.mydoctor2.data.User;
import com.example.mydoctor2.data.UserDatabase;
import com.example.mydoctor2.data.UserDatabaseClient;
import com.example.mydoctor2.databinding.FragmentHomeBinding;
import com.example.mydoctor2.other.SharedPref;

public class HomeFragment extends Fragment {

    private EditText name, kg, height, temp, puls, blood, bmi;
    private Spinner gender;

    private FragmentHomeBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        User user = SharedPref.getInstance().getUser(getActivity());
        Spinner mySpinner = (Spinner) rootView.findViewById(R.id.genInput);
        mySpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Sex.values()));

        name = rootView.findViewById(R.id.numeInput);
        if(user.getName() != null)
            name.setText(user.getName());

        kg = rootView.findViewById(R.id.kgInput);
        if(user.getKg() != 0){
            kg.setText(""+user.getKg());
            if(user.getHeight() != 0){
                if((user.getHeight()%100 - 10) > user.getKg())
                    kg.setBackgroundColor(Color.parseColor("#ff5a5a"));
                else if((user.getHeight()%100 + 10) < user.getKg())
                    kg.setBackgroundColor(Color.parseColor("#ff5a5a"));
                else
                    kg.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }

        height = rootView.findViewById(R.id.heightInput);
        if(user.getHeight() != 0)
            height.setText(""+user.getHeight());

        gender = mySpinner;

        temp = rootView.findViewById(R.id.tempInput);
        if(user.getTemperature() != 0){
            temp.setText(""+user.getTemperature());
            if(user.getTemperature() > 37)
                temp.setBackgroundColor(Color.parseColor("#51d24e"));
            else
                temp.setBackgroundColor(Color.parseColor("#ca66ee"));
        }


        puls = rootView.findViewById(R.id.pulsInput);
        if(user.getPulse() != 0){
            puls.setText(""+user.getPulse());
            if(110 <= user.getPulse() && user.getPulse() <= 130)
                puls.setBackgroundColor(Color.parseColor("#ffc84c"));
            else if( user.getPulse() >= 131)
                puls.setBackgroundColor(Color.parseColor("#ff5a5a"));
            else
                puls.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        blood = rootView.findViewById(R.id.bloodInput);
        if(user.getBloodPressure() != 0)
        {
            blood.setText(""+user.getBloodPressure());
            if(120 <= user.getBloodPressure() && user.getBloodPressure() <= 129)
                blood.setBackgroundColor(Color.parseColor("#f8fc95"));
            else if(130 <= user.getBloodPressure() && user.getBloodPressure() <= 139)
                blood.setBackgroundColor(Color.parseColor("#ffc84c"));
            else if(user.getBloodPressure() >= 140 && user.getBloodPressure() < 180)
                blood.setBackgroundColor(Color.parseColor("#ff5a5a"));
            else if(user.getBloodPressure() >= 180)
                blood.setBackgroundColor(Color.parseColor("#cc1717"));
            else
                blood.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        bmi = rootView.findViewById(R.id.bmiInput);
        if(user.getKg() != 0 && user.getHeight() != 0){
            float BMI;
            BMI = (user.getKg()*10000)/(user.getHeight()*user.getHeight());
            bmi.setText("" + BMI);
        }

        Button updateData = rootView.findViewById(R.id.updateData);
        Button saveData = rootView.findViewById(R.id.saveData);

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

            Toast.makeText(getActivity(), getString(R.string.editable), Toast.LENGTH_SHORT).show();

        });

        saveData.setOnClickListener(view -> {

            String kgInput2 = kg.getText().toString();
            String heightInput2 = height.getText().toString();
            String tempInput2 = temp.getText().toString();
            String pulsInput2 = puls.getText().toString();
            String bloodInput2 = blood.getText().toString();

            if (validateInputs(heightInput2, tempInput2, pulsInput2)) {
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
                float kgToUpdate;
                float heightToUpdate;
                int tempToUpdate;
                int pulsToUpdate;
                int bloodToUpdate;

                nameToUpdate = name.getText().toString();

                if(kgInput2.matches(""))
                    kgToUpdate = 0;
                else
                    kgToUpdate = Float.parseFloat(kg.getText().toString());

                if(heightInput2.matches(""))
                    heightToUpdate = 0;
                else
                    heightToUpdate = Float.parseFloat(heightInput2);

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

                saveData(user, nameToUpdate, kgToUpdate, heightToUpdate, (Sex) gender.getSelectedItem(),
                        tempToUpdate, pulsToUpdate, bloodToUpdate);

                if(user.getKg() != 0 && user.getHeight() != 0){
                    float BMI;
                    BMI = (user.getKg()*10000)/(user.getHeight()*user.getHeight());
                    bmi.setText("" + BMI);
                }
                else
                {
                    bmi.setText("");
                }

                if((heightToUpdate%100 - 10) > kgToUpdate)
                    kg.setBackgroundColor(Color.parseColor("#ff5a5a"));
                else if((heightToUpdate%100 + 10) < kgToUpdate)
                    kg.setBackgroundColor(Color.parseColor("#ff5a5a"));
                else
                    kg.setBackgroundColor(Color.parseColor("#ffffff"));

                if(110 <= pulsToUpdate && pulsToUpdate <= 130)
                    puls.setBackgroundColor(Color.parseColor("#ffc84c"));
                else if(pulsToUpdate >= 131)
                    puls.setBackgroundColor(Color.parseColor("#ff5a5a"));
                else
                    puls.setBackgroundColor(Color.parseColor("#ffffff"));

                if(120 <= bloodToUpdate && bloodToUpdate <= 129)
                    blood.setBackgroundColor(Color.parseColor("#f8fc95"));
                else if(130 <= bloodToUpdate && bloodToUpdate <= 139)
                    blood.setBackgroundColor(Color.parseColor("#ffc84c"));
                else if(bloodToUpdate >= 140 && bloodToUpdate < 180)
                    blood.setBackgroundColor(Color.parseColor("#ff5a5a"));
                else if(bloodToUpdate >= 180)
                    blood.setBackgroundColor(Color.parseColor("#cc1717"));
                else
                    blood.setBackgroundColor(Color.parseColor("#ffffff"));


                if(tempToUpdate > 37)
                    temp.setBackgroundColor(Color.parseColor("#51d24e"));
                else
                    temp.setBackgroundColor(Color.parseColor("#ca66ee"));


             }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean validateInputs(String height, String temp, String puls) {
        if(!height.isEmpty()){
            if (Float.parseFloat(height) < 30 || Float.parseFloat(height) > 250) {
                Toast.makeText(getActivity(), getString(R.string.wrong_height), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(!temp.isEmpty()){
            if (Double.parseDouble(temp) < 36.0 || Double.parseDouble(temp) > 42.0) {
                Toast.makeText(getActivity(), getString(R.string.wrong_temp), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(!puls.isEmpty()){
            if (Integer.parseInt(puls) < 30 || Integer.parseInt(puls) > 150) {
                Toast.makeText(getActivity(), getString(R.string.wrong_puls), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    //functie cu care salvam in db
    private void saveData(User user, String name, float kg, float height, Sex gen, int temp, int puls, int blood) {
        user.setName(name);
        user.setKg(kg);
        user.setHeight(height);
        user.setSex(gen);
        user.setTemperature(temp);
        user.setPulse(puls);
        user.setBloodPressure(blood);
        HomeFragment.UpdateDetailsTask updateDetailsTask = new HomeFragment.UpdateDetailsTask(user);
        updateDetailsTask.execute();
    }

    //cu ajutorul ei salvam in db
    @SuppressLint("StaticFieldLeak")
    class UpdateDetailsTask extends AsyncTask<Void, Void, Void> {

        private final User user;

        UpdateDetailsTask(User user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getActivity());
            databaseClient.userDao().updateUser(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
        }
    }

}