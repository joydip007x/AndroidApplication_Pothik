package com.example.atry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity /*implements View.OnClickListener*/{

    private Button BsignIn,BsignUp,BsignInTreveler,BsignInAgency;
    public static FirebaseAuth mAuth=FirebaseAuth.getInstance(),
                                mAuth2=FirebaseAuth.getInstance();
    public static  FirebaseUser user,user2;
    public  static String userID, userID2;

    public static String getUserID() {
        return userID;
    }
    public static void setUserID( ) {

        setUser();
        if(user==null) return;
        MainActivity.userID= mAuth.getCurrentUser().getUid();
    }
    public static void setUserID2( ) {

        setUser2();
        if(user2==null) return;
        MainActivity.userID2= mAuth2.getCurrentUser().getUid();
    }
    public static FirebaseAuth getmAuth() {
        return mAuth;
    }
    public static void setmAuth(FirebaseAuth mAuth) {
        MainActivity.mAuth = mAuth;
    }
    public static FirebaseUser getUser() {
        setUser();
        return user;
    }
    public static FirebaseUser getUser2() {

        setUser2();
        return user2;
    }
    public static void setUser( ) {
        MainActivity.user=mAuth.getCurrentUser();
    }
    public static void setUser2( ) {
        MainActivity.user2=mAuth2.getCurrentUser();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUserID();

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

/*BsignUp.setOnClickListener(this);
        BsignInAgency.setOnClickListener(this);
        BsignInTreveler.setOnClickListener(this);*/
/*
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.BsignUp:
                Intent i = new Intent(getApplicationContext(), SignUpAs.class);
                startActivity(i);
                break;

            case R.id.BsignInAgency:
                Intent b = new Intent(getApplicationContext(), SignInAsAgency.class);
                startActivity(b);
                break;
            case R.id.BsignInTreveler:
                Intent c = new Intent(getApplicationContext(), SignInAsTraveler.class);
                startActivity(c);
                break;
        }


    }*/
