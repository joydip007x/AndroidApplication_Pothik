package com.example.atry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpA extends AppCompatActivity {

    private EditText emailET, passET ,conPassET, nameET,agencyNameET,addressET,numberET,descriptionET;
    private Button Bsup;
    private TextView tv;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private  String email,pass,Conpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupas_a);

        emailET = findViewById(R.id.ETemail2);
        passET = findViewById(R.id.ETpass2);
        conPassET = findViewById(R.id.ETconfirmPass2);
        Bsup = findViewById(R.id.Bsup2);
        nameET = findViewById(R.id.ETownerName2);
        agencyNameET = findViewById(R.id.ETcomName2);
        addressET = findViewById(R.id.ETaddress);
        numberET = findViewById(R.id.ETnumber);
        descriptionET = findViewById(R.id.ETdescription2);
        tv = findViewById(R.id.tv1);
        progressBar=findViewById(R.id.suaaprogressBar2);

        progressBar.setVisibility(View.INVISIBLE);

        mAuth=FirebaseAuth.getInstance();

        Bsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                email = emailET.getText().toString().trim();
                pass = passET.getText().toString().trim();
                Conpass = conPassET.getText().toString().trim();


                if(email.isEmpty())
                {
                    emailET.setError("Enter an email address");
                    emailET.requestFocus();
                    return;
                }

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    emailET.setError("Enter a valid email address");
                    emailET.requestFocus();
                    return;
                }


                if( pass.compareTo(Conpass)!=0 )
                {
                    passET.setError("Password doesn't match");
                    passET.requestFocus();
                    return;
                }
                if( pass.length()<6){
                    passET.setError("Password too short ");
                    passET.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(SignUpA.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(SignUpA.this.getApplicationContext(), "Register is successfull", Toast.LENGTH_SHORT).show();

                                    new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(3600);

                                            } catch (InterruptedException e) {

                                            }
                                        }
                                    };

                                    progressBar.setVisibility(View.INVISIBLE);
                                    Intent a = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(a);

                                } else {

                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(SignUpA.this.getApplicationContext(),task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }

}

