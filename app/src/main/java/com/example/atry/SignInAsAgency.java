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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInAsAgency extends AppCompatActivity implements View.OnClickListener {

    private EditText sinEmailET, sinPassET;
    private Button Bsin;
    private TextView pothik,siaa;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinas_agency);

        if(MainActivity.getUser()!=null){

            Intent a = new Intent(SignInAsAgency.this.getApplicationContext(), EmptyPage.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(a);
            finishAndRemoveTask();
        }

        sinEmailET = findViewById(R.id.ETsInEmaila);
        sinPassET = findViewById(R.id.ETsInPassa);
        Bsin = findViewById(R.id.BsIna);
        pothik = findViewById(R.id.tv);
        siaa = findViewById(R.id.tva);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SignInAsAgency.this, FrontPage.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
    }

    @Override
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


        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = mAuth/*.getInstance()*/.getCurrentUser();
                            Intent a = new Intent(getApplicationContext(),FrontPage.class);
                            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(a);
                        }
                        else
                            {
                            Toast.makeText(getApplicationContext(),"Log in unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                    }
                });
    }
}