package com.example.atry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.example.atry.MainActivity.getUser;
import static com.example.atry.MainActivity.mAuth;
import static com.example.atry.MainActivity.setUser;
import static com.example.atry.MainActivity.setUserID;
import static com.example.atry.MainActivity.userID;
import static java.lang.Thread.*;

public class SignUpA extends AppCompatActivity {



    private EditText emailET, passET ,conPassET, nameET,agencyNameET,addressET,numberET,descriptionET;
    private Button Bsup;
    private TextView tv;
    private  static ProgressBar progressBar;
    private  String email,pass,Conpass;
    private  DatabaseReference mAgencyDatabase;
    private String mAgencyName, mAddress,mNumber,mEmail,mDescription,mName;
    static AgencyProfile AP=null;


    static void setPBInv(){

        progressBar.setVisibility(View.INVISIBLE);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupas_a);

        emailET = findViewById(R.id.ETemail2);
        passET = findViewById(R.id.ETpass2);
        conPassET = findViewById(R.id.ETconfirmPass2);
        Bsup = findViewById(R.id.Bsup2);
        nameET = findViewById(R.id.ETownerName2);
        agencyNameET = findViewById(R.id.ETcomName2);
        addressET = findViewById(R.id.ETaddress2);
        numberET = findViewById(R.id.ETnumber2);
        descriptionET = findViewById(R.id.ETdescription2);
        progressBar=findViewById(R.id.suaaprogressBar2);
        progressBar.setVisibility(View.INVISIBLE);



        MainActivity.mAuth=FirebaseAuth.getInstance();
        MainActivity.setUser();


        Bsup.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                if(numberET.hasFocus()) numberET.clearFocus();


                mName=nameET.getText().toString();
                mAddress=addressET.getText().toString();
                mDescription=descriptionET.getText().toString();
                mAgencyName = agencyNameET.getText().toString();
                mNumber=numberET.getText().toString();
                mEmail = emailET.getText().toString();
                pass = passET.getText().toString();
                Conpass = conPassET.getText().toString().trim();
                email=mEmail;

                if(mNumber.length()!=11){

                    numberET.setError("Give a correct phone number Consisting 11 Digit");
                    numberET.requestFocus();
                    return;
                }
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

                AP=new AgencyProfile(mAgencyName,mAddress,mNumber,mEmail,mDescription,mName);

                UniqnessCheck.checkPhone(mNumber, SignUpA.this,email,pass,"A");

            }
        });
    }
        static void doRegister(final Context context, final String email, String pass){


        MainActivity.mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            setPBInv();

                            Toast.makeText(context, "Register is successfull ", Toast.LENGTH_SHORT).show();

                            mAuth.getCurrentUser().sendEmailVerification().
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(context, " Verification-Email Sent ",
                                                        Toast.LENGTH_LONG).show();

                                               AP.saveUserInfo();
                                               MainActivity.startAftSignUp(context);

                                            } else {

                                                Toast.makeText(context, task.getException().getMessage().toString()
                                                        , Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            setPBInv();
                            Toast.makeText(context,task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }


                });

    }
    static void toashShow(Context context, String msg ,int success){

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        setPBInv();

    }

}

