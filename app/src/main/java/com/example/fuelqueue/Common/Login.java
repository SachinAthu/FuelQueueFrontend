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
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.fuelqueue.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    public static String[] USER_TYPES = {"user", "station_owner"};

    TextInputEditText emailField;
    TextInputEditText passwordField;
    AutoCompleteTextView userTypeField;
    LinearLayout loginTopLinearLayout;

    ArrayList<String> userTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.loginEmail);
        passwordField= findViewById(R.id.loginPassword);
        userTypeField = findViewById(R.id.loginUserType);
        loginTopLinearLayout = findViewById(R.id.loginTopLinearLayout);

        // set user type field
        userTypeField.setInputType(0);
        userTypes.add("User");
        userTypes.add("Station Owner");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.select_field_list_item, userTypes);
        userTypeField.setAdapter(arrayAdapter);

        userTypeField.setOnKeyListener(this);

        // set click on listener to root linear layout
        loginTopLinearLayout.setOnClickListener(this);

    }

    public void loginUser(View view) {
        Log.i("username", emailField.getText().toString());
        Log.i("password", passwordField.getText().toString());
        Log.i("user type", userTypeField.getText().toString());

    }

    public void toRegister(View view) {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginTopLinearLayout) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(view.getId() == R.id.loginUserType && i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            loginUser(findViewById(R.id.loginBtn));
        }

        return false;
    }

}