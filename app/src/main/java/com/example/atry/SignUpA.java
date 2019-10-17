package com.example.atry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignUpA extends AppCompatActivity {

    private EditText emailET, passET, conPassET, nameET, agencyNameET, addressET, numberET, descriptionET;
    private Button Bsup;
    private TextView tv;
    private FirebaseAuth mAuth;
    private DatabaseReference mAgencyDatabase;
    private String userID;
    private String mName,mAgencyName,mAddress,mNumber,mDescription,mEmail;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupas_a);
        nameET = findViewById(R.id.ETownerName);
        agencyNameET = findViewById(R.id.ETcomName);
        addressET = findViewById(R.id.ETaddress);
        numberET = findViewById(R.id.ETnumber);
        descriptionET = findViewById(R.id.ETdescription);
        emailET = findViewById(R.id.ETemail);
        passET = findViewById(R.id.ETpass);
        conPassET = findViewById(R.id.ETconfirmPass);

        Bsup = findViewById(R.id.Bsup);
        tv = findViewById(R.id.tv1);

        userID = mAuth.getCurrentUser().getUid();
        mAgencyDatabase = FirebaseDatabase.getInstance().getReference().child("user").child("agency").child(userID);

       /* firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SignUpA.this, FrontPage.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };*/

        //profile e info show koranor function
        //getUserInfo();

        Bsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
                String email = emailET.getText().toString().trim();
                String pass = passET.getText().toString().trim();
                String Conpass = conPassET.getText().toString().trim();


                if (email.isEmpty()) {
                    emailET.setError("Enter an email address");
                    emailET.requestFocus();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailET.setError("Enter a valid email address");
                    emailET.requestFocus();
                    return;
                }


                if (pass != Conpass) {
                    passET.setError("Password doesn't match");
                    passET.requestFocus();
                    return;
                }

                MainActivity.mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(SignUpA.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpA.this.getApplicationContext(), "Register is successfull", Toast.LENGTH_SHORT).show();

                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_userDB = FirebaseDatabase.getInstance().getReference().child("user").child("agency").child(user_id);
                                    current_userDB.setValue(true);

                                    /*Intent a = new Intent(getApplicationContext(), FrontPage.class);
                                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(a);*/

                                } else {

                                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                /*Intent a = new Intent(getApplicationContext(), SignInAsAgency.class);
                startActivity(a);*/
            }
        });
    }
    private void saveUserInfo()
    {
        mName = nameET.getText().toString().trim();
        mAgencyName = agencyNameET.getText().toString();
        mAddress = addressET.getText().toString().trim();
        mNumber = numberET.getText().toString();
        mDescription = descriptionET.getText().toString().trim();
        mEmail = emailET.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("name",mName);
        userInfo.put("agencyName",mAgencyName);
        userInfo.put("address",mAddress);
        userInfo.put("number",mNumber);
        userInfo.put("description",mDescription);
        userInfo.put("email",mEmail);
        mAgencyDatabase.setValue(userInfo);

    }


    private void saveUserInfo()
    {
        mName = nameET.getText().toString().trim();
        mAgencyName = agencyNameET.getText().toString();
        mAddress = addressET.getText().toString().trim();
        mNumber = numberET.getText().toString();
        mDescription = descriptionET.getText().toString().trim();
        mEmail = emailET.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("name",mName);
        userInfo.put("agencyName",mAgencyName);
        userInfo.put("address",mAddress);
        userInfo.put("number",mNumber);
        userInfo.put("description",mDescription);
        userInfo.put("email",mEmail);

        mAgencyDatabase.updateChildren(userInfo);

        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    /*
       // Profile e info show koranor code


   private void getUserInfo()
    {
        mAgencyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0)
                {
                    Map<String,Object> map = (Map<String,Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null)
                    {
                        mName = map.get("name").toString();
                        nameET.setText(mName);
                    }
                    if(map.get("agencyName")!=null)
                    {
                        mAgencyName = map.get("name").toString();
                        agencyNameET.setText(mName);
                    }
                    if(map.get("address")!=null)
                    {
                        mAddress = map.get("name").toString();
                        addressET.setText(mName);
                    }
                    if(map.get("number")!=null)
                    {
                        mNumber = map.get("name").toString();
                        numberET.setText(mName);
                    }
                    if(map.get("description")!=null)
                    {
                        mDescription = map.get("name").toString();
                        descriptionET.setText(mName);
                    }
                    if(map.get("email")!=null)
                    {
                        mEmail = map.get("name").toString();
                        emailET.setText(mName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/


}