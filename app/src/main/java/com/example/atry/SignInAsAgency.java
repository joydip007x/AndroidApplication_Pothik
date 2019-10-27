package com.example.atry;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInAsAgency extends AppCompatActivity  implements ForgotPassDialog.ForgotPassDListerner {

    private EditText sinEmailET, sinPassET;
    private Button Bsin, forgot;
    private TextView pothik,siaa;
    private String email,pass;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signinas_agency);

        if(MainActivity.getUser()!=null && MainActivity.getUser().isEmailVerified()){

            Intent a = new Intent(SignInAsAgency.this.getApplicationContext(), Nav_Draw_Agency.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(a);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAndRemoveTask();
            }
        }

        sinEmailET = findViewById(R.id.ETsInEmaila);
        sinPassET = findViewById(R.id.ETsInPassa);
        Bsin = findViewById(R.id.BsIna);
        pothik = findViewById(R.id.tv);
        siaa = findViewById(R.id.tva);
        forgot=findViewById(R.id.buttonForgotPassA);

        progressBar=findViewById(R.id.progressBar3);

        progressBar.setVisibility(View.INVISIBLE);
        MainActivity.mAuth = FirebaseAuth.getInstance();

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  openForgotPassDialog();
            }
        });
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

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
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
                MainActivity.mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(SignInAsAgency.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.INVISIBLE);
                                if (task.isSuccessful()) {

                                  if(!MainActivity.getUser().isEmailVerified()){

                                      Toast.makeText(SignInAsAgency.this.getApplicationContext(), "Verify Email first !",
                                              Toast.LENGTH_SHORT).show();
                                      return;
                                  }
                                    MainActivity.setAgencyuser(true);MainActivity.setTraveleruser(false);
                                    MainActivity.user = MainActivity.getUser();
                                    DataBaseOpOfAgency.delForFirstLogIn("temp_agency","email",email);
                                    Toast.makeText(SignInAsAgency.this.getApplicationContext(), "Logged in ",
                                            Toast.LENGTH_SHORT).show();

                                    Intent a = new Intent(SignInAsAgency.this.getApplicationContext(), Nav_Draw_Agency.class);
                                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(a);
                                } else {

                                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    private void openForgotPassDialog() {

        ForgotPassDialog forgotPassDialog=new ForgotPassDialog();
        forgotPassDialog.show(getSupportFragmentManager(),"Forgot password?");
    }

    @Override
    public void applyTexts(final String Email) {

        AgencyProfile ag=new AgencyProfile(Email);
        ag.isIDexits(SignInAsAgency.this);

    }
    public  static  void toastShow(final String msg, String Email, int success, final Context context){

        if(success==0){
            Toast.makeText(context,
                    msg, Toast.LENGTH_LONG).show();
            return;
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(context,
                            msg, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,
                            task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
