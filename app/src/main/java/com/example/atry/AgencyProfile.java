package com.example.atry;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AgencyProfile{

    static  boolean done=false;
    static ImageView userdp;
    public AgencyProfile() {


    }
    public  AgencyProfile(String mEmail){

         this.mEmail=mEmail;
         UIDexists=false;
    }
    public AgencyProfile(String mAgencyName, String mAddress, String mNumber, String mEmail, String mDescription, String mName) {
        this.mAgencyName = mAgencyName;
        this.mAddress = mAddress;
        this.mNumber = mNumber;
        this.mEmail = mEmail;
        this.mDescription = mDescription;
        this.mName = mName;
    }
    public Map getMapofAgencyProfile(){

        Map userInfo = new HashMap();
        userInfo.put("name",mName);
        userInfo.put("agencyName",mAgencyName);
        userInfo.put("address",mAddress);
        userInfo.put("number",mNumber);
        userInfo.put("description",mDescription);
        userInfo.put("email",mEmail);

        return userInfo;

    }
    public void  isIDexits(final Context context){


        done=false;
        ///System.out.println("00001111"+" LL ");

        ValueEventListener responseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot i: dataSnapshot.getChildren()){

                     if(done) break;
                     HashMap M=(HashMap) i.getValue();

                     if(M.get("email").toString().compareTo(MainActivity.getCrrentUserEmail())==0){

                         SignInAsAgency.toastShow("Verfication email sent",mEmail,1,context);
                         done=true;
                     }

                   /// System.out.println("00001111"+M.values().toString());
                }
                if(!done){
                    SignInAsAgency.toastShow("ID doesn't exists",mEmail,0,context);
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child("agency");
        ref.addValueEventListener(responseListener);

    }
    public static String getHashedEmail(String nMail){

        return nMail.replace(".","-").replace("[","-").replace("]","-")
                .replace("$","-").replace("#","-");
    }
    private String mAgencyName, mAddress,mNumber,mEmail,mDescription,mName;

    public boolean isUIDexists() {
        return UIDexists;
    }

    private boolean UIDexists;

    public void saveUserInfo()
    {
        Map userInfo = getMapofAgencyProfile();

        ///System.out.println("LALALA "+mEmail );

        DatabaseReference mAgencyDatabase = FirebaseDatabase.getInstance().getReference()
                .child("temp_agency")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mAgencyDatabase.setValue(userInfo);

    }

    public  static  ImageView getdp(){

        return userdp;
    }
    /*public void saveUserInfo(String uuid)
    {
        Map userInfo = getMapofAgencyProfile();

        System.out.println("LALALA "+mEmail );

        DatabaseReference mAgencyDatabase = FirebaseDatabase.getInstance().getReference()
                .child("temp_agency")
               /// .child(uuid);
        mAgencyDatabase.setValue(userInfo);

    }*/



}
