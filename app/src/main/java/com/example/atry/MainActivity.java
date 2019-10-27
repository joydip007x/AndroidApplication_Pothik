package com.example.atry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{

    private Button BsignIn,BsignUp,BsignInTreveler,BsignInAgency;


    public static boolean isAgencyuser() {
        return agencyuser;
    }

    public static void setAgencyuser(boolean agencyuser) {
        MainActivity.agencyuser = agencyuser;
    }

    public static boolean isTraveleruser() {
        return traveleruser;
    }

    public static void setTraveleruser(boolean traveleruser) {
        MainActivity.traveleruser = traveleruser;
    }
    public static boolean agencyuser = false, traveleruser = false;
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance(),
            mAuth2 = FirebaseAuth.getInstance();
    public static FirebaseUser user;
    public static String userID;

    public static String getUserID() {
        return userID;
    }

    public static String getUserID2() {
        return getUserID();
    }

    public  static  String getCrrentUserEmail(){

        return  FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }



    public static void setUserID() {

        setUser();
        if (user == null) return;
        MainActivity.userID = mAuth.getCurrentUser().getUid();
    }

    public static void setUserID2() {
        setUserID();
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
        return getUser();
    }

    public static void setUser() {
        MainActivity.user = mAuth.getCurrentUser();
    }

    public static void setUser2() {
        setUser();
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


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), SignUpAs.class);
                a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
            }
        });
        BsignInAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(getApplicationContext(), SignInAsAgency.class);
                b.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(b);
            }
        });
        BsignInTreveler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(getApplicationContext(), SignInAsTraveler.class);
                c.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c);
            }
        });


    }
    public  static  void startAftSignUp(Context context){

        Intent a = new Intent( context,MainActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(a);
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
