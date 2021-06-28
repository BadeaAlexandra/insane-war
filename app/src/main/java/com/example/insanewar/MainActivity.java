package com.example.insanewar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //region declarari

    private DatabaseReference reff;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView signUpTextView;
    private ImageView cardImageView;
    private Button loginButton;
    private RelativeLayout relativeLayout;

    private String username;
    private String password;
    private int ok = 0;

    //endregion

    //region dezactivare keyboard
    public void onClick (View view) {
        if (view.getId() == R.id.signUpTextView) { //sign up
            Log.i("Log in", "Sign up option selected");
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.cardImageView || view.getId() == R.id.relativeLayout) {  //dismiss the keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region initializare elemente activitate
        relativeLayout = findViewById(R.id.relativeLayout);
        cardImageView = findViewById(R.id.cardImageView);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.signUpTextView);
        //endregion

        //region dezactivare keyboard
        signUpTextView.setOnClickListener(this);
        cardImageView.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        //endregion

        //region loginButton
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                username = usernameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty())
                    Toast.makeText(MainActivity.this,"Trebuie completate ambele campuri",Toast.LENGTH_LONG).show();

                else {
                    reff = FirebaseDatabase.getInstance().getReference().child("Users");
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot iUser : dataSnapshot.getChildren()) {
                                String user = iUser.child("username").getValue().toString();
                                String pass = iUser.child("password").getValue().toString();

                                if (username.equals(user) && password.equals(pass)) {
                                    Toast.makeText(MainActivity.this, "Autentificare realizata cu succes " , Toast.LENGTH_LONG).show();
                                    ok = 1;
                                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                }
                            }

                            if (ok == 0)
                                Toast.makeText(MainActivity.this, "Datele introduse sunt incorecte", Toast.LENGTH_LONG).show();

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
