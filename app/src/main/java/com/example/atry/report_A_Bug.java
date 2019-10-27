package com.example.atry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class report_A_Bug extends AppCompatDialogFragment  {


    private EditText ed,emailET;
    private String email,txt;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view= inflater.inflate( R.layout.report_bug,null);

        ed=view.findViewById(R.id.report_bug_rep);
        emailET=view.findViewById(R.id.report_bug_email);

        builder.setView(view)
               /* .setTitle("Password Reset")*/
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                email=emailET.getText().toString();
                txt=ed.getText().toString();
                DatabaseReference mAgencyDatabase = FirebaseDatabase.getInstance().getReference().child("Reported Bugs").push();
                String val=email+" : "+txt;
                mAgencyDatabase.setValue(val);
            }
        });

        return builder.create();
    }

}
