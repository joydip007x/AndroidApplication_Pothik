package com.example.atry;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.atry.MainActivity.getUser;
import static com.example.atry.MainActivity.getUser2;
import static com.example.atry.MainActivity.user2;

public class SignInAsTraveler extends AppCompatActivity /*implements View.OnClickListener*/ {

    private EditText sinEmailET, sinPassET;
    private Button Bsin;
    private TextView pothik, siat;
    private  String pass,email;
    private ProgressBar progressBar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinas_traveler);
        sinEmailET = findViewById(R.id.ETsInEmail);
        sinPassET = findViewById(R.id.ETsInPass);
        Bsin = findViewById(R.id.BsIn);
        pothik = findViewById(R.id.tv);
        siat = findViewById(R.id.tv2);
        MainActivity.mAuth = FirebaseAuth.getInstance();

        if(MainActivity.getUser2()!=null){

            Intent a = new Intent(SignInAsTraveler.this.getApplicationContext(), EmptyPage.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(a);
          //  finish();
            finishAndRemoveTask();

        }

        progressBar=findViewById(R.id.progressBar4);

        progressBar.setVisibility(View.INVISIBLE);

        Bsin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               email = sinEmailET.getText().toString().trim();
               pass = sinPassET.getText().toString().trim();
                if (email.isEmpty()) {
                    sinEmailET.setError("Enter an email address");
                    sinEmailET.requestFocus();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    sinEmailET.setError("Enter a valid email address");
                    sinEmailET.requestFocus();
                    return;
                }

                if (pass.isEmpty()) {
                    sinPassET.setError("Enter a password");
                    sinPassET.requestFocus();
                    return;
                }
                if (pass.length() < 6) {
                    sinPassET.setError("Password Length should be minimum 6");
                    sinPassET.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                MainActivity.mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(SignInAsTraveler.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.INVISIBLE);

                                if (task.isSuccessful()) {

                                    MainActivity.user2 = getUser2();
                                    Toast.makeText(SignInAsTraveler.this.getApplicationContext(), "Logged in ",
                                            Toast.LENGTH_LONG).show();

                                    Intent a = new Intent(SignInAsTraveler.this.getApplicationContext(), EmptyPage.class);
                                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(a);

                                } else {

                                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }

}

    /*@Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.BsIn :
                agencyLogin();
                break;
        }
    }

    private void agencyLogin() {
        String email = sinEmailET.getText().toString().trim();
        String pass = sinPassET.getText().toString().trim();
        if(email.isEmpty())
        {
            sinEmailET.setError("Enter an email address");
            sinEmailET.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            sinEmailET.setError("Enter a valid email address");
            sinEmailET.requestFocus();
            return;
        }

        if(pass.isEmpty())
        {
            sinPassET.setError("Enter a password");
            sinPassET.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            sinPassET.setError("Password Length should be minimum 6");
            sinPassET.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            *//*Intent a = new Intent(getApplicationContext(),SignInAsAgency.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);*//*
                        } else {

                            Toast.makeText(getApplicationContext(),"Log in unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}*/