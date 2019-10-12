package com.example.atry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpAs extends AppCompatActivity {

    Button BsignIn,BsignUp,BsignUpTreveler,BsignUpAgency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupas);

        BsignIn = findViewById(R.id.BsignIn);
        BsignUp = findViewById(R.id.BsignUp);
        BsignUpAgency = findViewById(R.id.BsignUpAgency);
        BsignUpTreveler = findViewById(R.id.BsignUpTreveler);

        BsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(a);
            }
        });
        BsignUpAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), SignUpA.class);
                startActivity(a);
            }
        });
        BsignUpTreveler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), SignUpT.class);
                startActivity(a);
            }
        });
    }
}
