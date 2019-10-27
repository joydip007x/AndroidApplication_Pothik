package com.example.atry;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;




public class UniqnessCheck {

    private  static int numberok=-1;

     static void checkPhone(final String mNumber, final Context context, final String email, final String pass, final String From){


         numberok=0;
         DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
         Query applesQuery2 =ref2.child("user").orderByKey();


         applesQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public  void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 Map<String, Map<String, Map<String, String>>> userInfo;
                 userInfo = (Map<String, Map<String, Map<String, String>>>) dataSnapshot.getValue();

                 if (userInfo == null) {

                     if(From=="A")SignUpA.doRegister(context,email,pass);
                     else SignUpT.doRegister(context,email,pass);
                     return;
                 }
                 Map<String, Map<String, String>> pMap = null;
                 Map<String, String> ui = null;

                 for (Object X : userInfo.keySet()) {

                    pMap=userInfo.get(X.toString());
                     if (pMap == null) continue;

                     for (Object name : pMap.keySet()) {

                         ui = pMap.get(name.toString());

                         if (ui == null) continue;
                         if (ui.get("number").equals(mNumber)) {


                             numberok++;
                             if (From == "A") SignUpA.toashShow(context, "A User ID Exists with same phone number", 0);
                             else             SignUpT.toashShow(context, "A User ID Exists with same phone number", 0);
                             return;
                         }
                     }

                 }
                 if(numberok==0){

                     if(From=="A")SignUpA.doRegister(context,email,pass);
                     else SignUpT.doRegister(context,email,pass);
                     return;
                 }
             }
             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }

         });

     }
}
/*
            System.out.println("baler bal "+SignUpA
            .witness);
*/
/*  for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {

                         SignUpA.witness=1;
                         System.out.println("baler bal "+appleSnapshot.toString()+"\n\n"+SignUpA.witness);

}*/
