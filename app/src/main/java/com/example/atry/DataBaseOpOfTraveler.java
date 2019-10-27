package com.example.atry;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DataBaseOpOfTraveler {


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

                DatabaseReference mAgencyDatabase = FirebaseDatabase.getInstance().getReference().child("user").child("traveler")
                        .child(TravelerProfile.getHashedEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
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
}
