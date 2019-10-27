package com.example.atry;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class TravelerProfile {

    private  static  boolean done;
    private String mAgencyName, mAddress,mNumber,mEmail,mDescription,mName;
    private String mAge,mgender;
    private boolean UIDexists=false;


    public TravelerProfile() {


    }
    public TravelerProfile(String mEmail){

        this.mEmail=mEmail;
        UIDexists=false;
    }
    public TravelerProfile(String mName, String mAge, String mAddress, String mNumber, String mgender, String mEmail) {
        this.mName = mName;
        this.mAddress = mAddress;
        this.mNumber = mNumber;
        this.mEmail = mEmail;
        this.mgender=mgender;
        this.mAge=mAge;
    }
    public Map getMapofTravelerProfile(){

        Map userInfo = new HashMap();
        userInfo.put("name",mName);
        userInfo.put("age",mAge);
        userInfo.put("address",mAddress);
        userInfo.put("number",mNumber);
        userInfo.put("gender",mgender);
        userInfo.put("email",mEmail);

        return userInfo;

    }
    public void isIDexits(final Context context){


        done=false;
        ///System.out.println("00001111"+" LL ");

        ValueEventListener responseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot i: dataSnapshot.getChildren()){

                    if(done) break;
                    HashMap M=(HashMap) i.getValue();

                    if(M.get("email").toString().compareTo(MainActivity.getCrrentUserEmail())==0){

                        SignInAsTraveler.toastShow("Verfication email sent",mEmail,1,context);
                        done=true;
                    }

                    /// System.out.println("00001111"+M.values().toString());
                }
                if(!done){
                    SignInAsTraveler.toastShow("ID doesn't exists",mEmail,0,context);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child("traveler");
        ref.addValueEventListener(responseListener);

    }
    public static String getHashedEmail(String nMail){

        return nMail.replace(".","-").replace("[","-").replace("]","-")
                .replace("$","-").replace("#","-");
    }


    public void saveUserInfo()
    {

        Map userInfo = getMapofTravelerProfile();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("temp_traveler")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.setValue(userInfo);
    }

}
