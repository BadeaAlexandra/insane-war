package com.example.insanewar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    private TextView helloTextView;
    private Button rulesButton;
    private Button startButtor;
    private String username;

    public void goBack (View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //region caseta username

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        helloTextView = findViewById(R.id.helloTextView);
        if (username == null) helloTextView.setText("Bine ai venit!");
            else helloTextView.setText("Buna, " + username + "!");

        //endregion

        rulesButton = findViewById(R.id.rulesButton);
        startButtor = findViewById(R.id.startButton);

        //region buton Reguli

        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, RulesActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        //endregion

        //region buton Start

        startButtor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, GameActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        //endregion
    }
}
