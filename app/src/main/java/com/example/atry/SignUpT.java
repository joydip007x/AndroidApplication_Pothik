package com.example.atry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.atry.MainActivity.mAuth;
import static com.example.atry.MainActivity.setUser;
import static com.example.atry.MainActivity.setUserID;
import static com.example.atry.MainActivity.setUserID2;

public class SignUpT extends AppCompatActivity {

    private EditText emailET, passET ,conPassET, nameET,ageET,addressET,numberET;
    private Button Bsup;
    private RadioButton male,female;
    private TextView genderTV,tv;
    private String age,email,pass,Conpass;
    private static ProgressBar progressBar;
    private RadioGroup rg;
    private  String mName,mAge,mAddress,mgender,mEmail,mNumber;

    static  TravelerProfile TP=null;
    private DatabaseReference mTravelerDatabase;


    static void setPBInv(){

        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupas_t);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        emailET = findViewById(R.id.ETemail);
        passET = findViewById(R.id.ETpass);
        conPassET = findViewById(R.id.ETconfirmPass);
        Bsup = findViewById(R.id.Bsup);
        genderTV = findViewById(R.id.TVgender);
        nameET = findViewById(R.id.ETname);
        ageET = findViewById(R.id.ETage);
        addressET = findViewById(R.id.ETaddress);
        tv = findViewById(R.id.tv1);
        numberET = findViewById(R.id.ETnumber);
        male = findViewById(R.id.RBmale);
        female = findViewById(R.id.RBfemale);
        progressBar=findViewById(R.id.suaaprogressBar1);


        male=findViewById(R.id.RBmale);
        female=findViewById(R.id.RBfemale);

        progressBar.setVisibility(View.INVISIBLE);
        MainActivity.mAuth = FirebaseAuth.getInstance();

        rg=findViewById(R.id.RG1);


        Bsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                numberET.clearFocus();

                 mName=nameET.getText().toString();
                 email = emailET.getText().toString();
                 pass = passET.getText().toString();
                 Conpass = conPassET.getText().toString();
                 age=ageET.getText().toString();
                 mNumber=numberET.getText().toString();
                 mAddress=addressET.getText().toString();
                 mAge=age;
                 mEmail=email;

                 if(male.isChecked()) mgender="Male";
                 else mgender="Female";


                if(mNumber.length()!=11){

                    numberET.setError("Provide a correct phone number Consisting 11 Digit");
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
                if(   age.isEmpty() || Integer.parseInt(age) <=0 || Integer.parseInt(age)>100 ){

                    ageET.setError("Enter a valid age between 1-100");
                    ageET.requestFocus();
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

                TP=new TravelerProfile(mName,mAge,mAddress,mNumber,mgender,mEmail);
                UniqnessCheck.checkPhone(mNumber, SignUpT.this,email,pass,"T");


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

                                                TP.saveUserInfo();
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


