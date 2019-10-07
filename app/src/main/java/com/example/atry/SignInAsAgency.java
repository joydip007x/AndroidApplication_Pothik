package com.example.atry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SignInAsAgency extends AppCompatActivity {

    private EditText sinEmailET, sinPassET;
    private Button Bsin;
    private TextView pothik,siaa;
    private FirebaseAuth mAuth;
    private   String email,pass;
    private  FirebaseUser user2;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinas_agency);
        sinEmailET = findViewById(R.id.ETsInEmaila);
        sinPassET = findViewById(R.id.ETsInPassa);
        Bsin = findViewById(R.id.BsIna);
        pothik = findViewById(R.id.tv);
        siaa = findViewById(R.id.tva);


        progressBar=findViewById(R.id.progressBar3);

        progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();

        Bsin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = sinEmailET.getText().toString().trim();
                pass = sinPassET.getText().toString().trim();
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

                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(SignInAsAgency.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.INVISIBLE);
                                if (task.isSuccessful()) {


                                    user2 = mAuth.getCurrentUser();

                                    Toast.makeText(SignInAsAgency.this.getApplicationContext(), "Logged in ",
                                            Toast.LENGTH_SHORT).show();

                                    Intent a = new Intent(SignInAsAgency.this.getApplicationContext(),MainActivity.class);
                                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(a);
                                } else {

                                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }


 /*   @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.BsIn :
                agencyLogin();
                break;
        }
    }
    */


    private void agencyLogin() {

    }
}