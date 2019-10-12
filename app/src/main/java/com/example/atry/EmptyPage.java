package com.example.atry;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmptyPage extends AppCompatActivity {


    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.empty_page);

        textView= findViewById(R.id.textViewEmpty);
        textView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }
}
