package com.example.atry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.atry.MainActivity.setUser;
import static com.example.atry.MainActivity.setUserID;
import static com.example.atry.MainActivity.setUserID2;

public class SignUpT extends AppCompatActivity {

    private EditText emailET, passET ,conPassET, nameET,ageET,addressET,numberET;
    private Button Bsup;
    private RadioButton male,female;
    private TextView genderTV,tv;
    private String age,email,pass,Conpass;
    private ProgressBar progressBar;
    private RadioGroup rg;
    private  String mName,mAge,mAddress,mgender,mEmail,mNumber;

    private DatabaseReference mTravelerDatabase;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupas_t);
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
                 email = emailET.getText().toString().trim();
                 pass = passET.getText().toString().trim();
                 Conpass = conPassET.getText().toString().trim();
                 age=ageET.getText().toString();

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
                MainActivity.mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(SignUpT.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.INVISIBLE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpT.this.getApplicationContext(), "Register is successfull",
                                            Toast.LENGTH_SHORT).show();

                                    setUserID2();
                                    mTravelerDatabase = FirebaseDatabase.getInstance().getReference().child("user").child("traveler").push();
                                    saveUserInfo();
                                    Intent a = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(a);
                                } else {

                                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }

    private void saveUserInfo()
    {
        mName = nameET.getText().toString().trim();
        mAge=ageET.getText().toString();
        mAddress = addressET.getText().toString().trim();
        mNumber = numberET.getText().toString();
        mEmail = emailET.getText().toString();
        if(male.isChecked()) mgender="Male";
        else mgender="Female";

        Map userInfo = new HashMap();
        userInfo.put("name",mName);
        userInfo.put("age",mAge);
        userInfo.put("address",mAddress);
        userInfo.put("number",mNumber);
        userInfo.put("gender",mgender);
        userInfo.put("email",mEmail);
        mTravelerDatabase.setValue(userInfo);

    }

}


