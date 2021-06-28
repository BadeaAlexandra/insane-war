package com.example.insanewar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public void goBack(View view) {
        finish();
    }

    //region declarari

    private RelativeLayout relativeLayout;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private ImageView cardImageView;
    private Button signUpButton;
    private String firstName, lastName, username, password, email;
    private DatabaseReference reff;
    private Boolean success = true;
    private String msg = null;
    private long count = 0;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +         //cel putin o cifra
            "(?=.*[a-z])" +         //cel putin o litera mica
            "(?=.*[A-Z])" +         //cel putin o litera mare
            "(?=\\S+$)" +           //niciun spatiu
            ".{4,}" +               //cel putin 4 caractere
            "$");

    DatePickerDialog.OnDateSetListener dateSetListener;

    //endregion

    //region dezactivare keyboard

    public void onClick(View v) {
        if (v.getId() == R.id.relativeLayout || v.getId() == R.id.cardImageView) {  //dismiss the keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //endregion

    private boolean validateUsername() {
        if (username.length() > 15) {
            return false;
        } else return true;
    }

    private boolean validatePassword() {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return false;
        } else return true;
    }

    private boolean validateEmail() {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        } else return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();

        //region initializari obiecte
        relativeLayout = findViewById(R.id.relativeLayout);
        cardImageView = findViewById(R.id.cardImageView);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        signUpButton = findViewById(R.id.signUpButton);
        //endregion

        //region dismiss keyboard
        relativeLayout.setOnClickListener(SignUpActivity.this);
        cardImageView.setOnClickListener(SignUpActivity.this);
        firstNameEditText.setOnClickListener(SignUpActivity.this);
        lastNameEditText.setOnClickListener(SignUpActivity.this);
        usernameEditText.setOnClickListener(SignUpActivity.this);
        passwordEditText.setOnClickListener(SignUpActivity.this);
        emailEditText.setOnClickListener(SignUpActivity.this);
        //endregion

        //region butonul de SIGN UP

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = firstNameEditText.getText().toString();
                lastName = lastNameEditText.getText().toString();
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                email = emailEditText.getText().toString();

                final User newUser = new User(firstName,lastName,username,password,email);

                if (firstName.trim().equals("") || lastName.trim().equals("") || username.trim().equals("") || password.trim().equals("") || email.trim().equals("")) {
                    success = false;
                    msg = "Toate campurile sunt obligatorii";

                } else if (validateUsername() == false) { //validarea fiecarui camp pe rand cu else if
                    success = false;
                    msg = "Username invalid";
                } else if (validatePassword() == false) {
                    success = false;
                    msg = "Parola prea slaba";
                } else if (validateEmail() == false) {
                    success = false;
                    msg = "E-mail invalid";
                } else {

                    reff = FirebaseDatabase.getInstance().getReference().child("Users");
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            count = dataSnapshot.getChildrenCount();

                            for (DataSnapshot iUser : dataSnapshot.getChildren()) {
                                String user = iUser.child("username").getValue().toString();
                                if (user.equals(username)) {
                                    success = false;
                                    msg = "Username deja existent";
                                }
                            }

                            if (success == true) {

                                try {
                                    msg = "Inregistrare finalizata cu succes";
                                    count++;

                                    //region inserare utilizator in Firebase

                                    String noUsers = String.valueOf (count);
                                    reff.child(noUsers).setValue(newUser);

                                    //endregion

                                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        //endregion

    }

}
