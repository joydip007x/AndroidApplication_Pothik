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

public class SignUpT extends AppCompatActivity {

    private EditText emailET, passET ,conPassET, nameET,ageET,addressET,numberET;
    private Button Bsup;
    private RadioGroup radioGroup;
    private RadioButton genderRB,maleRB,femaleRB;
    private TextView genderTV,tv;

    private FirebaseAuth mAuth;
    private DatabaseReference mTravelerDatabase;
    private String userID;
    private String mName,mAge,mAddress,mNumber,mgender,mEmail;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupas_t);

        nameET = findViewById(R.id.ETname);
        ageET = findViewById(R.id.ETage);
        addressET = findViewById(R.id.ETaddress);
        emailET = findViewById(R.id.ETemail);
        passET = findViewById(R.id.ETpass);
        conPassET = findViewById(R.id.ETconfirmPass);
        numberET = findViewById(R.id.ETnumber);

        radioGroup = findViewById(R.id.RGid);
        maleRB = findViewById(R.id.RBmale);
        femaleRB = findViewById(R.id.RBfemale);
        Bsup = findViewById(R.id.Bsup);
        genderTV = findViewById(R.id.TVgender);
        tv = findViewById(R.id.tv1);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mTravelerDatabase = FirebaseDatabase.getInstance().getReference().child("user").child("traveler").child(userID);


        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderRB = findViewById(selectedId);

        Bsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();

                String email = emailET.getText().toString().trim();
                String pass = passET.getText().toString().trim();
                String Conpass = conPassET.getText().toString().trim();
                String age=ageET.getText().toString();

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

                if( Integer.parseInt(age) <=0 || Integer.parseInt(age)>=100 ){

                    ageET.setError("Enter a valid age between 1-100");
                    ageET.requestFocus();
                    return;
                }
                if(pass.isEmpty())
                {
                    passET.setError("Enter a password");
                    passET.requestFocus();
                    return;
                }

                if(pass!=Conpass)
                {
                    passET.setError("Password doesn't match");
                    passET.requestFocus();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, pass)

                        .addOnCompleteListener(SignUpT.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(SignUpT.this.getApplicationContext(), "Register is successfull", Toast.LENGTH_SHORT).show();
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_userDB = FirebaseDatabase.getInstance().getReference().child("user").child("traveler").child(user_id);
                                    current_userDB.setValue(true);
                                    /*Intent a = new Intent(getApplicationContext(), FrontPage.class);
                                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(a);*/
                                } else {
                                    Toast.makeText(getApplicationContext(),task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                /*Intent a = new Intent(getApplicationContext(), SignInAsTraveler.class);
                startActivity(a);*/
            }
        });
    }
  
    private void saveUserInfo()
    {
        mName = nameET.getText().toString().trim();
        mAge = ageET.getText().toString();
        mAddress = addressET.getText().toString().trim();
        mNumber = numberET.getText().toString();
        mgender = genderRB.getText().toString();
        mEmail = emailET.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("name",mName);
        userInfo.put("age",mAge);
        userInfo.put("address",mAddress);
        userInfo.put("number",mNumber);
        userInfo.put("gender",mgender);
        userInfo.put("email",mEmail);

        mTravelerDatabase.updateChildren(userInfo);

        finish();
    }

}
