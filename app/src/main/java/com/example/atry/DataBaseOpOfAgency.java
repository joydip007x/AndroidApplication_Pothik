package com.example.atry;

import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataBaseOpOfAgency {


    public  static  HashMap M;
    public Image dp;
    public static  void delForFirstLogIn(String X,String Y, String toSearch) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child(X).orderByChild(Y).equalTo(toSearch);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Map<String, String>> userInfo;
                userInfo = (Map<String, Map<String, String>>) dataSnapshot.getValue();

                if(userInfo==null ) return;
                Map ui = null;
                for (Object name : userInfo.keySet()) ui = userInfo.get(name.toString());

                DatabaseReference mAgencyDatabase = FirebaseDatabase.getInstance().getReference().child("user").child("agency")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                mAgencyDatabase.updateChildren(ui);
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DELETE", "onCancelled", databaseError.toException());
            }
        });

    }
    public static void LoadDP(){

        DatabaseReference d= FirebaseDatabase.getInstance().getReference("user/agency")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 M= (HashMap) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public  static String getDPurl(){

        return M.get("DP").toString();
    }
}
