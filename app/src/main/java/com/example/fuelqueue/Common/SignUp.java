package com.example.fuelqueue.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.example.fuelqueue.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    TextInputEditText fullNameField;
    TextInputEditText emailField;
    TextInputEditText phoneNumberField;
    TextInputEditText passwordField;
    TextInputEditText confirmPasswordField;
    AutoCompleteTextView userTypeField;
    LinearLayout signUpTopLinearLayout;

    ArrayList<String> userTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameField = findViewById(R.id.signUpFullName);
        emailField = findViewById(R.id.signUpEmail);
        phoneNumberField = findViewById(R.id.signUpPhoneNumber);
        passwordField= findViewById(R.id.signUpPassword);
        confirmPasswordField= findViewById(R.id.signUpConfirmPassword);
        userTypeField = findViewById(R.id.signUpUserType);
        signUpTopLinearLayout = findViewById(R.id.signUpTopLinearLayout);

        // set user type field
        userTypeField.setInputType(0);
        userTypes.add("User");
        userTypes.add("Station Owner");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.select_field_list_item, userTypes);
        userTypeField.setAdapter(arrayAdapter);

        userTypeField.setOnKeyListener(this);
        confirmPasswordField.setOnKeyListener(this);

        // set click on listener to root linear layout
        signUpTopLinearLayout.setOnClickListener(this);
    }

    public void signUpUser(View view) {
        Log.i("full name", fullNameField.getText().toString());
        Log.i("username", emailField.getText().toString());
        Log.i("phone number", phoneNumberField.getText().toString());
        Log.i("password", passwordField.getText().toString());
        Log.i("confirm password", confirmPasswordField.getText().toString());
        Log.i("user type", userTypeField.getText().toString());

    }

    public void toLogin(View view) {
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signUpTopLinearLayout) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(view.getId() == R.id.signUpConfirmPassword && i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            signUpUser(findViewById(R.id.signUpBtn));
        }

//        if(view.getId() == R.id.signUpUserType && i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//            passwordField.requestFocus();
//        }

        return false;
    }
}