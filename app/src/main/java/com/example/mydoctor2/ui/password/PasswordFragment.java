package com.example.mydoctor2.ui.password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.mydoctor2.R;
import com.example.mydoctor2.other.SharedPref;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PasswordFragment extends Fragment {

    private AppBarConfiguration mAppBarConfiguration;

    private EditText oldPass, newPass, newPassAgain;

    public PasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPref sharedPref = SharedPref.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false);
    }
}