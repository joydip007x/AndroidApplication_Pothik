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

public class ForgotPassDialog extends AppCompatDialogFragment  {


    private EditText ed;
    private String email;
    private ForgotPassDListerner listerner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        listerner=(ForgotPassDListerner) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.forgot_pass_dialog,null);

        ed=view.findViewById(R.id.editTextForgotPassDialouge);

        builder.setView(view)
                .setTitle("Password Reset")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                email=ed.getText().toString();
                listerner.applyTexts(email) ;
            }
        });

        return builder.create();
    }

    public interface ForgotPassDListerner{

        void applyTexts(String Email);
    }
}
