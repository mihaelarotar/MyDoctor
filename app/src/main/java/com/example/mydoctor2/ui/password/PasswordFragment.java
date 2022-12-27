package com.example.mydoctor2.ui.password;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mydoctor2.MainActivity;
import com.example.mydoctor2.R;
import com.example.mydoctor2.activities.PasswordActivity;
import com.example.mydoctor2.data.User;
import com.example.mydoctor2.data.UserDatabase;
import com.example.mydoctor2.data.UserDatabaseClient;
import com.example.mydoctor2.other.SharedPref;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PasswordFragment extends Fragment {

    private EditText oldPass, newPass, newPassAgain;

    public PasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_password, container, false);
        User user = SharedPref.getInstance().getUser(getActivity());

        oldPass  = rootView.findViewById(R.id.old_pass);
        newPass = rootView.findViewById(R.id.new_pass);
        newPassAgain = rootView.findViewById(R.id.new_pass_again);

        Button buttonChangePass = (Button) rootView.findViewById(R.id.change_pass_button);
        buttonChangePass.setOnClickListener(view -> {
            String oldPassword = oldPass.getText().toString();
            String newPassword = newPass.getText().toString();
            String newPasswordAgain = newPassAgain.getText().toString();

            if(validaInputs(oldPassword, newPassword, newPasswordAgain))
            {
                String newEncryptedPass = "";
                String dbPass = user.getPassword();
                PasswordActivity passwordActivity = new PasswordActivity();
                try {
                    newEncryptedPass = passwordActivity.encrypt(newPassword);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                user.setPassword(newEncryptedPass);
                UpdatePasswordTask updatePasswordTask = new UpdatePasswordTask(user, oldPassword, newPassword, dbPass);
                updatePasswordTask.execute();
            }
        });

        return rootView;
    }

    private boolean validaInputs(String oldPass, String newPass, String newPassAgain) {

        if (oldPass.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (newPass.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (newPassAgain.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!newPass.equals(newPassAgain)) {
            Toast.makeText(getActivity(), getString(R.string.wrong_new_pass), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (newPass.length() < 8) {
            Toast.makeText(getActivity(), getString(R.string.password_too_short), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (newPassAgain.length() < 8) {
            Toast.makeText(getActivity(), getString(R.string.password_too_short), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    //cu ajutorul ei salvam in db
    class UpdatePasswordTask extends AsyncTask<Void, Void, Void> {

        private final User user;
        private final String oldPass;
        private final String newPass;
        private final String dbPass;

        UpdatePasswordTask(User user, String oldPass, String newPass, String dbPass) {
            this.user = user;
            this.oldPass = oldPass;
            this.newPass = newPass;
            this.dbPass = dbPass;
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

            PasswordActivity passwordActivity = new PasswordActivity();
            String oldEncryptedPass = "";
            String newEncryptedPass = "";
            try {
                oldEncryptedPass = passwordActivity.encrypt(oldPass);
                newEncryptedPass = passwordActivity.encrypt(newPass);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(oldEncryptedPass.equals(dbPass)) {

                user.setPassword(newEncryptedPass);
                SharedPref sharedPref = SharedPref.getInstance();
                sharedPref.setUser(getActivity(), user);
                startActivity(new Intent(getActivity(), MainActivity.class));
                Toast.makeText(getActivity(), getString(R.string.changed), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), getString(R.string.wrong_pass), Toast.LENGTH_SHORT).show();
            }
        }

    }
}