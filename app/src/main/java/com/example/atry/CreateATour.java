package com.example.atry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.StringValue;

import java.util.HashMap;
import java.util.Objects;

import javax.annotation.RegEx;

public class CreateATour extends AppCompatActivity implements CancelCreateATour.CancelCreateATourLis {


    private  EditText tourname, tourtime, tourtk,tourdetails,tourloc;
    private ImageView  dp1,dp2,dp3;

    private Uri resultUri;
    private StorageReference storageReference=null;
    public static  final  int IMAGE_REQ=12 ;
    private Uri imageUri;

    private Button Postit=null;
    private  String postUID=null;
    private  HashMap baseinfo=new HashMap();

    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    private boolean clickedDP;
    private boolean okpost=false,clicked2nd=false;
    private DatabaseReference mAgencyDatabase=null,mPostdatabase;

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public Context getApplicationContext(){
        return CreateATour.this;
    }
    private String curUser(){

        return  FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_atour);



        mAgencyDatabase = FirebaseDatabase.getInstance().getReference().child("user").child("agency").child(curUser());

        postUID=FirebaseDatabase.getInstance().getReference("post").push().getKey().toString();

        mPostdatabase=FirebaseDatabase.getInstance().getReference().child("post");
        tourname=findViewById(R.id.editTextCATTourName);
        tourtime=findViewById(R.id.editTextCATTourDate);
        tourloc=findViewById(R.id.editTextCATTourLocation);
        tourtk=findViewById(R.id.editTextCATmoney);
        tourdetails=findViewById(R.id.editTextCATdetails);
        dp1=findViewById(R.id.imageViewCATDP);
        dp2=findViewById(R.id.imageViewCATDP2);
        dp3=findViewById(R.id.imageViewCATDP3);

        Postit=findViewById(R.id.buttonPOSTIT);


        loadbaseinfo();
        dp1.setOnClickListener(v -> {

            openImage();
            clickedDP=true;

        });
        dp2.setOnClickListener(v -> {
            openImage();
            clicked2nd=true;
        });
        dp3.setOnClickListener(v->{
            openImage();
        });


        Postit.setOnClickListener(v -> {
            
            if(tourname.getText().toString().isEmpty()  ) {

                tourname.setError("Give your tour a attractive name");
                tourname.requestFocus();
                return;
            }   
            if(tourtime.getText().toString().isEmpty()  ) {

                tourtime.setError("fix a date for your tour");
                tourtime.requestFocus();
                return;
            }   
            if(tourtk.getText().toString().isEmpty()  ) {

                tourtk.setError("Declare Amount Prerequisite");
                tourtk.requestFocus();
                return;
            }  
            if(tourdetails.getText().toString().isEmpty()  ) {

                tourdetails.setError("Give your tour details");
                tourdetails.requestFocus();
                return;
            }  
            if(tourloc.getText().toString().isEmpty()  ) {

                tourloc.setError("Your tour location ?");
                tourloc.requestFocus();
                return;
            }

            mPostdatabase.child(postUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    HashMap hashMap= (HashMap) dataSnapshot.getValue();

                    if(hashMap==null) {

                        Toast.makeText(getApplicationContext(),"No image Added !!! ",
                                Toast.LENGTH_LONG).show();

                        return;
                    }
                    if(hashMap.get("cover")==null ){

                        Toast.makeText(getApplicationContext(),"Add Cover image ",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(hashMap.get("image1")==null && hashMap.get("image2")==null){


                        Toast.makeText(getApplicationContext(),"You should Add more image ",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    postSuccesfull();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        });


    }

    private void postSuccesfull() {

        String Stourname,Stourloc,Stourtk,Stourdetails,Stourtime;

        Stourname=tourname.getText().toString();
        Stourloc=tourloc.getText().toString();
        Stourtk=tourtk.getText().toString();
        Stourdetails=tourdetails.getText().toString();
        Stourtime=tourtime.getText().toString();

/*
        System.out.println("OOOOO"+baseinfo.values().toString() );
*/


        baseinfo.put("TourName",Stourname);
        baseinfo.put("Tourloc",Stourloc);
        baseinfo.put("Tourtk",Stourtk);
        baseinfo.put("Tourdetails",Stourdetails);
        baseinfo.put("Tourtime",Stourtime);
        baseinfo.put("Like",0);
        baseinfo.put("Dislike",0);
        baseinfo.put("Registered",0);
        baseinfo.put("agencyUID",curUser());

        okpost=true;
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("post")
                .child(postUID);
        databaseReference.updateChildren(baseinfo);

        Toast.makeText(getApplicationContext(),"Post successfull",Toast.LENGTH_LONG).show();
        startNavDrawAgency();

    }

    private void loadbaseinfo() {

        mAgencyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {


                   /// System.out.println("OOOOOOO"+dataSnapshot.child("agencyName").getValue().toString());

                    baseinfo.put("Agency",dataSnapshot.child("agencyName").getValue().toString());
                    baseinfo.put("Phone",dataSnapshot.child("number").getValue().toString());
                    baseinfo.put("Email",dataSnapshot.child("email").getValue().toString());

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private  String getFileExtension(Uri uri){

        ContentResolver contentResolver= getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onBackPressed() {


        CancelCreateATour c=new CancelCreateATour();
        c.show(getSupportFragmentManager(),"WOW");
        //super.onBackPressed();


       /* if(okpost==false){

            System.out.println("OOOOOO"+okpost);

            mPostdatabase.child(postUID);
            HashMap M=new HashMap();

            FirebaseDatabase.getInstance().getReference("post")
                    .child(postUID).setValue(M);
            finish();

            mPostdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    HashMap M= (HashMap) dataSnapshot.getValue();

                   System.out.println("OOOOOOO \n"+M.values().toString());

                    M=new HashMap();

                    FirebaseDatabase.getInstance().getReference("post")
                            .child(postUID).setValue(M);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/

    }

    private void openImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,IMAGE_REQ);

    }

    private  void  uploadImage(){

        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Uploading ...");
        pd.show();

      ///  System.out.println("OOOO"+postUID);
        if(imageUri!=null){

          //  long p=System.currentTimeMillis();
            storageReference= FirebaseStorage.getInstance().getReference("post")
                .child(postUID);
            ;

            final StorageReference fileRef=storageReference.child("cover"+"."
                    +getFileExtension(imageUri));

            uploadTask= fileRef.putFile(imageUri);

            Glide.with(getApplicationContext())
                    .load(imageUri)
                    .into(dp1);

            uploadTask.continueWithTask(task -> {

                if(!task.isSuccessful()) throw Objects.requireNonNull(task.getException());

                return fileRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {


                if(task.isSuccessful()){

                    Uri downloadUri=task.getResult();

                    assert downloadUri != null;
                    String mUri=downloadUri.toString();

                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("post")
                            .child(postUID);

                    HashMap map= new HashMap();
                    map.put("cover",mUri);
                    databaseReference.updateChildren(map);


                    /// DataBaseOpOfAgency.LoadDP();
                    pd.dismiss();
                }
                else{

                    Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_LONG).show();
                    pd.dismiss();;
                }
            }).addOnFailureListener(e -> {

                Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_LONG).show();
                pd.dismiss();
            });
        }
        else{

            Toast.makeText(getApplicationContext(),"SELECT A IMAGE", Toast.LENGTH_LONG).show();
            pd.dismiss();
        }

        clickedDP=false;

    }
    private  void  uploadImagenotDp(){

        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Uploading ...");
        pd.show();

       /// System.out.println("OOOO"+postUID);
        if(imageUri!=null){

          //  long p=System.currentTimeMillis();
            storageReference= FirebaseStorage.getInstance().getReference("post")
                .child(postUID);

            final StorageReference fileRef=storageReference.child(System.currentTimeMillis()+"."
                    +getFileExtension(imageUri));

            uploadTask= fileRef.putFile(imageUri);
            uploadTask.continueWithTask(task -> {

                if(!task.isSuccessful()) throw Objects.requireNonNull(task.getException());

                return fileRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {


                if(task.isSuccessful()){

                    Uri downloadUri=task.getResult();

                    assert downloadUri != null;
                    String mUri=downloadUri.toString();

                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("post")
                            .child(postUID);



                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            HashMap map= (HashMap) dataSnapshot.getValue();

                            if(map==null){

                                HashMap x=new HashMap();


                             ///   System.out.println("WOWW"+clicked2nd);

                               if(clicked2nd) x.put("image1",mUri);
                               else x.put("image2",mUri);
                                databaseReference.updateChildren(x);


                               if(clicked2nd) Glide.with(getApplicationContext()).load(imageUri).into(dp2);
                               else  Glide.with(getApplicationContext()).load(imageUri).into(dp3);
                                clicked2nd=clickedDP=false;
                                return;

                            }
                            if( clicked2nd ){

                                map.put("image1",mUri);

                            }
                            else map.put("image2",mUri);

                            if(clicked2nd) Glide.with(getApplicationContext()).load(imageUri).into(dp2);
                            else  Glide.with(getApplicationContext()).load(imageUri).into(dp3);
                            clicked2nd=clickedDP=false;
                            databaseReference.updateChildren(map);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    /// DataBaseOpOfAgency.LoadDP();
                    pd.dismiss();
                }
                else{

                    clicked2nd=clickedDP=false;
                    Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_LONG).show();
                    pd.dismiss();;
                }
            }).addOnFailureListener(e -> {

                clicked2nd=clickedDP=false;
                Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_LONG).show();
                pd.dismiss();
            });
        }
        else{

            Toast.makeText(getApplicationContext(),"SELECT A IMAGE", Toast.LENGTH_LONG).show();
            clicked2nd=clickedDP=false;
            pd.dismiss();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==IMAGE_REQ && resultCode==RESULT_OK
                && data!=null && data.getData()!=null){


            imageUri=data.getData();

            if(uploadTask!=null && uploadTask.isInProgress()){

                Toast.makeText(getApplicationContext(),"IN PROGRESS", Toast.LENGTH_LONG).show();

            }
            else{
               if(clickedDP) uploadImage();
               else uploadImagenotDp();
            }
        }
        else     clicked2nd=clickedDP=false;

    }


    @Override
    public void applyTexts(String Email) {

      if(Email!="Y") return;

        ////System.out.println("OOOOOO"+okpost);


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("post")
                .child(postUID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                HashMap map= (HashMap) dataSnapshot.getValue();

            ///    System.out.println("OOOO"+map.values().toString());

                if(map==null) return;
                if(map.get("cover")!=null)
                    deleteFireBaseStorageItem(map.get("cover").toString());
                if(map.get("image1")!=null)
                    deleteFireBaseStorageItem(map.get("image1").toString());
                if(map.get("image2")!=null)
                    deleteFireBaseStorageItem(map.get("image2").toString());
                //StorageReference storageReference=FirebaseStorage.getInstance().getReference();


                mPostdatabase.child(postUID);
                HashMap M=new HashMap();
                FirebaseDatabase.getInstance().getReference("post").child(postUID).setValue(M);
                startNavDrawAgency();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public  void startNavDrawAgency(){

        Intent a = new Intent(CreateATour.this.getApplicationContext(), Nav_Draw_Agency.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        finish();

    }
     void deleteFireBaseStorageItem(String fileUrl){


        ///  System.out.println("OOOOO"+fileUrl);

          StorageReference X= FirebaseStorage.getInstance().getReferenceFromUrl(fileUrl);

      ///    System.out.println("OOOOO"+X.toString());

         X.delete();

         FirebaseStorage.getInstance().getReference().child(X.toString()).delete()
                  .addOnCompleteListener(task -> {

                      if(task.isSuccessful()){
                          Toast.makeText(getApplicationContext(),"Add a Tour Canceled",Toast.LENGTH_LONG).show();
                      }

                  });

    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }


}
