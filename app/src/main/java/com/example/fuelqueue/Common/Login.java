package com.example.fuelqueue.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.fuelqueue.Config;
import com.example.fuelqueue.Database.Backend.UserHttp;
import com.example.fuelqueue.Database.SQLite.UserSQLite;
import com.example.fuelqueue.HelperClasses.StationModel;
import com.example.fuelqueue.HelperClasses.UserModel;
import com.example.fuelqueue.R;
import com.example.fuelqueue.StationOwner.StationOwnerHome;
import com.example.fuelqueue.User.UserHome;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout mobileNumberField;
    TextInputLayout passwordField;
    TextInputLayout userTypeField;
    AutoCompleteTextView userTypeFieldTextView;
    LinearLayout loginTopLinearLayout;

    ArrayList<String> userTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobileNumberField = findViewById(R.id.loginMobileNumber);
        passwordField = findViewById(R.id.loginPassword);
        userTypeField = findViewById(R.id.loginUserType);
        userTypeFieldTextView = findViewById(R.id.loginUserTypeTextView);
        loginTopLinearLayout = findViewById(R.id.loginTopLinearLayout);

        // set user type field
        userTypeFieldTextView.setInputType(0);
        for (String t : Config.USER_TYPES.keySet()) {
            userTypes.add(t);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.select_field_list_item, userTypes);
        userTypeFieldTextView.setAdapter(arrayAdapter);

        // set click on listener to root linear layout
        loginTopLinearLayout.setOnClickListener(this);

    }

    public void loginUser(View view) {
        if (!validateAll()) {
            return;
        }

        closeKeyboard();

        UserModel user = new UserModel(
                mobileNumberField.getEditText().getText().toString(),
                Config.USER_TYPES.get(userTypeFieldTextView.getText().toString()),
                passwordField.getEditText().getText().toString()
        );

        user.display();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserHttp userHttp = retrofit.create(UserHttp.class);

        Call<UserModel> call = userHttp.login(user);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                System.out.println(response.code());
                if (response.code() == 200) {
                    Toast.makeText(Login.this, "Logged in successfully!", Toast.LENGTH_LONG).show();
                    clearForm();
                    UserModel user = response.body();

                    // save user info to sqlite database
                    UserSQLite userSQLite = new UserSQLite(Login.this);
                    userSQLite.addUserInfo(user);

//                    UserModel u = userSQLite.getUserInfo();
//                    u.display();
//
//                    if(u.getStation() != null) {
//                        StationModel s = u.getStation();
//                        s.display();
//                    }
//
//                    String token = userSQLite.getToken();
//
//                    System.out.println(token);
//
//                    userSQLite.logout();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (user.getType().equals(Config.USER_TYPES.get("User"))) {
                                Intent intent = new Intent(Login.this, UserHome.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(Login.this, StationOwnerHome.class);
                                startActivity(intent);
                            }
                            finish();
                        }
                    }, 3000);
                } else if (response.code() == 400 || response.code() == 401) {
                    Log.e("Failed!", response.message());
                    Toast.makeText(Login.this, "Invalid credentials. Please try again!", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("Failed!", response.message());
                    Toast.makeText(Login.this, "Oh! Logging failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("Internal Server Error!", t.getMessage());
                Toast.makeText(Login.this, "Oh! Internal Server Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void clearForm() {
        mobileNumberField.getEditText().setText("");
        passwordField.getEditText().setText("");
        userTypeField.getEditText().setText("");

    }

    public void toRegister(View view) {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

    /*
     * validations
     * */
    private boolean validateAll() {
        boolean isValid1 = validateMobileNumber();
        boolean isValid2 = validatePassword();
        boolean isValid3 = validateUserType();

        if (isValid1 && isValid2 && isValid3) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateMobileNumber() {
        String value = mobileNumberField.getEditText().getText().toString().trim();
        String checkMobile = "^0[0-9]{9,9}$";

        if (value.isEmpty()) {
            mobileNumberField.setError("Mobile number is required.");
            return false;
        } else if (value.length() != 10) {
            mobileNumberField.setError("Should be 10 digit number.");
            return false;
        } else if (!value.matches(checkMobile)) {
            mobileNumberField.setError("Invalid mobile number.");
            return false;
        } else {
            mobileNumberField.setError(null);
            mobileNumberField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String value = passwordField.getEditText().getText().toString().trim();
        String checkPassword = "^\\S{8,8}\\S*$";

        if (value.isEmpty()) {
            passwordField.setError("Password is required.");
            return false;
        } else if (value.length() < 8) {
            passwordField.setError("Password is too short.");
            return false;
        } else if (!value.matches(checkPassword)) {
            passwordField.setError("Password is invalid.");
            return false;
        } else {
            passwordField.setError(null);
            passwordField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserType() {
        String value = userTypeField.getEditText().getText().toString().trim();

        if (value.isEmpty()) {
            userTypeField.setError("User type is required.");
            return false;
        } else {
            userTypeField.setError(null);
            userTypeField.setErrorEnabled(false);
            return true;
        }
    }

    private void closeKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        // close keyboard when touch outside
        if (view.getId() == R.id.loginTopLinearLayout) {
            closeKeyboard();
        }
    }

}