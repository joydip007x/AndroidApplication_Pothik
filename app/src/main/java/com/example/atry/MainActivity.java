package com.example.atry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button BsignIn,BsignUp,BsignInTreveler,BsignInAgency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BsignIn = findViewById(R.id.BsignIn);
        BsignUp = findViewById(R.id.BsignUp);
        BsignInAgency = findViewById(R.id.BsignInAgency);
        BsignInTreveler = findViewById(R.id.BsignInTreveler);

        BsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), SignUpAs.class);
                startActivity(a);
            }
        });
        BsignInAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(getApplicationContext(), SignInAsAgency.class);
                startActivity(b);
            }
        });
        BsignInTreveler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(getApplicationContext(), SignInAsTraveler.class);
                startActivity(c);
            }
        });

    }

}
