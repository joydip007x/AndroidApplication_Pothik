package com.example.atry;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Nav_draw_Fragment_Settings extends Fragment {

    private  Button buttoncfu,buttoncad, buttonrap;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){


         buttoncfu=getView().findViewById(R.id.fab_nav_a_setting_cfu);
         buttoncad=getView().findViewById(R.id.fab_nav_a_setting_devs);
         buttonrap=getView().findViewById(R.id.fab_nav_a_setting_rap);

         buttoncfu.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 CheckForUpdate cFU = new CheckForUpdate();
                 cFU.show(getFragmentManager(), "XXX");
             }
         });

         buttoncad.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 ViewAppDev vad=new ViewAppDev();
                 vad.show(getFragmentManager(),"XXX");

             }
         });
         buttonrap.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 report_A_Bug r=new report_A_Bug();
                 r.show(getFragmentManager(),"XXX");
             }
         });

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.nav_a_fragment_settings,container,false);
    }



}
    /*button=inflater.findViewById(R.id.button_nav_a_setting_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent c = new Intent(getContext(), MainActivity.class);
                c.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                c.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c);
              ///  startActivity( new Intent(getContext(), MainActivity.class));
            }
        });*/