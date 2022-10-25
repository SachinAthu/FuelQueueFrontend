package com.example.fuelqueue.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fuelqueue.Config;
import com.example.fuelqueue.Database.Backend.UserHttp;
import com.example.fuelqueue.HelperClasses.StationModel;
import com.example.fuelqueue.HelperClasses.UserModel;
import com.example.fuelqueue.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    LinearLayout signUpTopLinearLayout;
    LinearLayout signUpStationDetailsLinearLayout;

    TextInputLayout fullNameField;
    TextInputLayout mobileNumberField;
    TextInputLayout passwordField;
    TextInputLayout confirmPasswordField;
    TextInputEditText confirmPasswordFieldTextView;
    TextInputLayout userTypeField;
    AutoCompleteTextView userTypeFieldTextView;
    TextInputLayout vehicleTypeField;
    AutoCompleteTextView vehicleTypeFieldTextView;

    TextInputLayout stationNameField;
    TextInputLayout stationAddressField;
    TextInputLayout stationTypeField;
    AutoCompleteTextView stationTypeFieldTextView;

    ArrayList<String> userTypes = new ArrayList<>();
    ArrayList<String> stationTypes = new ArrayList<>();
    ArrayList<String> vehicleTypes = new ArrayList<>();
    boolean isTypeUser = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpTopLinearLayout = findViewById(R.id.signUpTopLinearLayout);
        signUpStationDetailsLinearLayout = findViewById(R.id.signUpStationDetailsLinearLayout);

        fullNameField = findViewById(R.id.signUpFullName);
        mobileNumberField = findViewById(R.id.signUpMobileNumber);
        passwordField = findViewById(R.id.signUpPassword);
        confirmPasswordField = findViewById(R.id.signUpConfirmPassword);
        confirmPasswordFieldTextView = findViewById(R.id.signUpConfirmPasswordTextView);
        userTypeField = findViewById(R.id.signUpUserType);
        userTypeFieldTextView = findViewById(R.id.signUpUserTypeTextView);
        vehicleTypeField = findViewById(R.id.signUpVehicleType);
        vehicleTypeFieldTextView = findViewById(R.id.signUpVehicleTypeTextView);

        stationNameField = findViewById(R.id.signUpStationName);
        stationAddressField = findViewById(R.id.signUpStationAddress);
        stationTypeField = findViewById(R.id.signUpStationType);
        stationTypeFieldTextView = findViewById(R.id.signUpStationTypeTextView);

        // set user type field
        userTypeFieldTextView.setInputType(0);
        for (String t : Config.USER_TYPES.keySet()) {
            userTypes.add(t);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.select_field_list_item, userTypes);
        userTypeFieldTextView.setAdapter(arrayAdapter);

        userTypeFieldTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = charSequence.toString();

                if (value.equals("User")) {
                    isTypeUser = true;
                    signUpStationDetailsLinearLayout.setVisibility(View.GONE);
                    vehicleTypeField.setVisibility(View.VISIBLE);

                } else {
                    isTypeUser = false;
                    signUpStationDetailsLinearLayout.setVisibility(View.VISIBLE);
                    vehicleTypeField.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // set station type field
        stationTypeFieldTextView.setInputType(0);
        for (String t : Config.STATION_TYPES.keySet()) {
            stationTypes.add(t);
        }

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getApplicationContext(), R.layout.select_field_list_item, stationTypes);
        stationTypeFieldTextView.setAdapter(arrayAdapter2);

        // set vehicle type field
        vehicleTypeFieldTextView.setInputType(0);
        for (String t : Config.VEHICLE_TYPES) {
            vehicleTypes.add(t);
        }

        ArrayAdapter arrayAdapter3 = new ArrayAdapter(getApplicationContext(), R.layout.select_field_list_item, vehicleTypes);
        vehicleTypeFieldTextView.setAdapter(arrayAdapter3);

        // set click on listener to confirm password field to submit the form
        confirmPasswordFieldTextView.setOnKeyListener(this);

        // set click on listener to root linear layout
        signUpTopLinearLayout.setOnClickListener(this);
    }

    public void signUpUser(View view) {
        if (!validateAll()) {
            return;
        }

        closeKeyboard();

        UserModel newUser = new UserModel(
                fullNameField.getEditText().getText().toString(),
                mobileNumberField.getEditText().getText().toString(),
                Config.USER_TYPES.get(userTypeFieldTextView.getText().toString()),
                passwordField.getEditText().getText().toString(),
                vehicleTypeField.getEditText().getText().toString()
        );

        if(!isTypeUser) {
            StationModel station = new StationModel(
                    stationNameField.getEditText().getText().toString(),
                    stationAddressField.getEditText().getText().toString(),
                    Config.STATION_TYPES.get( stationTypeField.getEditText().getText().toString())
            );
            newUser.setStation(station);
            station.display();
        }

        newUser.display();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserHttp userHttp = retrofit.create(UserHttp.class);

        Call<UserModel> call = userHttp.signup(newUser);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.code() == 201 || response.code() == 200) {
                    Toast.makeText(SignUp.this, "Signed up successfully!", Toast.LENGTH_LONG).show();
                    // clearForm();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toLogin(view);
                            finish();
                        }
                    }, 3000);
                } else if(response.code() == 400) {
                    Log.e("Failed!", response.message());
                    Toast.makeText(SignUp.this, "Invalid inputs. Please check the entered values again!", Toast.LENGTH_LONG).show();
                } else if(response.code() == 403) {
                    Log.e("Failed!", response.message());
                    Toast.makeText(SignUp.this, "User already exists. Please check the mobile number!", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("Failed!", response.message());
                    Toast.makeText(SignUp.this, "Oh! Signing up failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("Internal Server Error!", t.getMessage());
                Toast.makeText(SignUp.this, "Oh! Internal Server Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void clearForm() {
        fullNameField.getEditText().setText("");
        mobileNumberField.getEditText().setText("");
        passwordField.getEditText().setText("");
        confirmPasswordField.getEditText().setText("");
        userTypeField.getEditText().setText("");

        if(isTypeUser) {
            stationNameField.getEditText().setText("");
            stationAddressField.getEditText().setText("");
            stationTypeField.getEditText().setText("");
            isTypeUser = true;
        }

    }

    public void toLogin(View view) {
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
    }

    /*
     * validations
     * */
    private boolean validateAll() {
        boolean isValid1 = validateFullName();
        boolean isValid2 = validatePassword(0);
        boolean isValid3 = validateUserType();
        boolean isValid4 = validateMobileNumber();
        boolean isValid5 = validatePassword(1);

        if(isTypeUser) {
            boolean isValid6 = validateVehicleType();

            if (isValid1 && isValid2 && isValid3 && isValid4 && isValid5 && isValid6) {
                return true;
            }
            return false;
        } else {
            boolean isValid7 = validateStationName();
            boolean isValid8 = validateStationAddress();
            boolean isValid9 = validateStationType();

            if (isValid1 && isValid2 && isValid3 && isValid4 && isValid5 && isValid7 && isValid8 && isValid9) {
                return true;
            }
            return false;
        }

    }

    private boolean validateFullName() {
        String value = fullNameField.getEditText().getText().toString().trim();

        if (value.isEmpty()) {
            fullNameField.setError("Full name is required.");
            return false;
        } else {
            fullNameField.setError(null);
            fullNameField.setErrorEnabled(false);
            return true;
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

    private boolean validatePassword(int type) {
        // type = 0, else - password field
        // type = 1 - confirm password field

        TextInputLayout inputField = passwordField;
        String emptyErrorMsg = "Password is required.";

        if (type == 1) {
            // confirm password field
            inputField = confirmPasswordField;
            emptyErrorMsg = "You need to confirm the password.";
        }

        String value = inputField.getEditText().getText().toString().trim();
        String checkPassword = "^\\S{8,8}\\S*$";

        if (value.isEmpty()) {
            inputField.setError(emptyErrorMsg);
            return false;
        } else if (value.length() < 8) {
            inputField.setError("Password is too short.");
            return false;
        } else if (!value.matches(checkPassword)) {
            inputField.setError("Password is invalid.");
            return false;
        } else if (type == 1 && !value.equals(passwordField.getEditText().getText().toString())) {
            inputField.setError("Password does not match.");
            return false;
        } else {
            inputField.setError(null);
            inputField.setErrorEnabled(false);
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

    private boolean validateStationName() {
        String value = stationNameField.getEditText().getText().toString().trim();

        if (value.isEmpty()) {
            stationNameField.setError("Station name is required.");
            return false;
        } else {
            stationNameField.setError(null);
            stationNameField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateStationAddress() {
        String value = stationAddressField.getEditText().getText().toString().trim();

        if (value.isEmpty()) {
            stationAddressField.setError("Station address is required.");
            return false;
        } else {
            stationAddressField.setError(null);
            stationAddressField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateStationType() {
        String value = stationTypeField.getEditText().getText().toString().trim();

        if (value.isEmpty()) {
            stationTypeField.setError("Station type is required.");
            return false;
        } else {
            stationTypeField.setError(null);
            stationTypeField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateVehicleType() {
        String value = vehicleTypeField.getEditText().getText().toString().trim();

        if (value.isEmpty()) {
            vehicleTypeField.setError("Vehicle type is required.");
            return false;
        } else {
            vehicleTypeField.setError(null);
            vehicleTypeField.setErrorEnabled(false);
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
        if (view.getId() == R.id.signUpTopLinearLayout) {
            closeKeyboard();
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (isTypeUser) {
            // submit form when touch enter confirm password field keyboard
            if (view.getId() == R.id.signUpConfirmPasswordTextView && i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                signUpUser(findViewById(R.id.signUpBtn));
            }
        }

        return false;
    }
}