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

public class CancelCreateATour extends AppCompatDialogFragment  {


    private EditText ed;
    private String email;
    private CancelCreateATourLis listerner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        listerner=(CancelCreateATourLis) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        builder.setTitle("Going back? Changes will be lost")
                .setMessage("Are you sure you want to go Back to Profile menu ?")
                .setIcon(R.drawable.ic_sentiment_dissatisfied_black_24dp)
                .setNegativeButton("cancel", (dialog, which) -> {

                }).setPositiveButton("Confirm", (dialog, which) -> {

                    email="Y";
                    listerner.applyTexts(email) ;
                });

        return builder.create();
    }

    public interface CancelCreateATourLis{

        void applyTexts(String Email);
    }
}
