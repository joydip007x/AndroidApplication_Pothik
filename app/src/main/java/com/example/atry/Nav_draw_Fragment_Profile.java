package com.example.atry;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class Nav_draw_Fragment_Profile extends Fragment
{
    @SuppressLint("all")

    private Uri resultUri;
    private ImageView /*IVProfile_Pic,*/  IVedit;
    private TextView ETagencyName,ETownerName,ETnumber,ETemail,ETdescription,ETaddress;
    private TextView TVownerName,TVnumber,TVemail,TVdescription,TVaddress;

    private String userID,mOwnerName,mProfilePic,mAgencyName,mAddress,mNumber,mDescription,mEmail;
    private DatabaseReference mAgencyDatabase;
    private FirebaseAuth mAuth;
    private Bitmap bitmap ;

    public static ImageView imageView;
    public static  ImageView dpstr;

    public static ImageView getImageView() {
        return imageView;
    }

    public static ImageView imageView2,createTour;

    private StorageReference storageReference=null;
    public static  final  int IMAGE_REQ=1;
    private Uri imageUri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;


    private String curUser(){

        return  FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        ///DataBaseOpOfAgency.LoadDP();
        View view = inflater.inflate(R.layout.nav_a_fragment_profile,container,false);

        mAuth= FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getEmail();
        mAgencyDatabase = FirebaseDatabase.getInstance().getReference().child("user").child("agency").child(curUser());
        storageReference= FirebaseStorage.getInstance().getReference("agencyDP").child(curUser());


       //// System.out.println("OOOOO"+storageReference.child("1571818519620").getDownloadUrl().toString());

        imageView=view.findViewById(R.id.IVadp);
        imageView2=view.findViewById(R.id.IVadp2);
        createTour=view.findViewById(R.id.imageViewAddATour);


        ETagencyName = view.findViewById(R.id.TVaAgencyName);
        ETownerName = view.findViewById(R.id.TVaOwnerName);
        ETnumber = view.findViewById(R.id.TVaNumber);
        ETemail = view.findViewById(R.id.TVaEmail);
        ETdescription = view.findViewById(R.id.TVaDescription);
        ETaddress = view.findViewById(R.id.TVaAddress);

        TVownerName = view.findViewById(R.id.TVaOwnerName1);
        TVnumber = view.findViewById(R.id.TVaNumber1);
        TVemail = view.findViewById(R.id.TVaEmail1);
        TVdescription = view.findViewById(R.id.TVaDescription1);
        TVaddress = view.findViewById(R.id.TVaAddress1);


        loadUserInfo();

        //IVProfile_Pic = view.findViewById(R.id.IVadp);
        //IVedit =  = view.findViewById(R.id.);



       /* IVProfile_Pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(Intent.createChooser(gallery,"Select Picture"), 101);
            }
        });*/


        createTour.setOnClickListener(v -> startActivity(new Intent(getContext(),CreateATour.class)));
        imageView.setOnClickListener(v -> {

            Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Intent intent=new Intent(getContext(),PopUpDp.class);

            intent.putExtra("picture", byteArray);
            startActivity(intent);

            /// String dpup=DataBaseOpOfAgency.getDPurl();
        });

        imageView2.setOnClickListener(v -> {


            openImage();
            //System.out.println("XXXXXXXX"+ 1111);
        });


        return view;
    }

    private  String getFileExtension(Uri uri){

        ContentResolver contentResolver= getContext().getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void openImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,IMAGE_REQ);

    }

    private  void  uploadImage(){

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading ...");
        pd.show();
        if(imageUri!=null){

            final StorageReference fileRef=storageReference.child(System.currentTimeMillis()+"."
                    +getFileExtension(imageUri));

            uploadTask= fileRef.putFile(imageUri);
            uploadTask.continueWithTask(task -> {

                if(!task.isSuccessful()) throw Objects.requireNonNull(task.getException());

                return fileRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {


                if(task.isSuccessful()){

                    Uri downloadUri=task.getResult();
                    String mUri=downloadUri.toString();

                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user/agency")
                            .child(curUser());

                    databaseReference.child(curUser());

                    HashMap map= new HashMap();
                    map.put("DP",mUri);
                    databaseReference.updateChildren(map);

                   /// DataBaseOpOfAgency.LoadDP();
                    pd.dismiss();
                }
                else{

                    Toast.makeText(getContext(),"failed", Toast.LENGTH_LONG).show();
                    pd.dismiss();;
                }
            }).addOnFailureListener(e -> {

                Toast.makeText(getContext(),"failed", Toast.LENGTH_LONG).show();
                pd.dismiss();
            });
        }
        else{

            Toast.makeText(getContext(),"SELECT A IMAGE", Toast.LENGTH_LONG).show();
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

                Toast.makeText(getContext(),"IN PROGRESS", Toast.LENGTH_LONG).show();

            }
            else{
                uploadImage();
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()== null)
        {
            return;
        }
    }

    private void finish() {
    }
    private void loadUserInfo() {

        mAgencyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    //// = dataSnapshot.child("profilePic").getValue().toString();
                    mAgencyName = dataSnapshot.child("agencyName").getValue().toString();
                    mOwnerName = dataSnapshot.child("name").getValue().toString();
                    mAddress = dataSnapshot.child("address").getValue().toString();
                    mNumber = dataSnapshot.child("number").getValue().toString();
                    mDescription = dataSnapshot.child("description").getValue().toString();
                    mEmail = dataSnapshot.child("email").getValue().toString();

                    ETagencyName.setText(mAgencyName);
                    ETownerName.setText(mOwnerName);
                    ETaddress.setText(mAddress);
                    ETnumber.setText(mNumber);
                    ETemail.setText(mEmail);
                    ETdescription.setText(mDescription);



                    ///StorageReference i =  storageReference.child("1571818519620");

                    DatabaseReference d=FirebaseDatabase.getInstance().getReference("user/agency")
                            .child(curUser());

                    d.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            HashMap M= (HashMap) dataSnapshot.getValue();
                        //    System.out.println("OOOOO"+M.values().toString());


                            Glide.with(getContext())
                                    .load(M.get("DP"))
                                    .into(imageView);
                          /*  Glide.with(getContext())
                                    .load(M.get("DP"))
                                    .into(Nav_Draw_Agency.userDp);*/
                         /*   Glide.with(getContext())
                                    .load(M.get("DP"))
                                    .into(dpstr);
*/
                            ///scaleImage(imageView);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                /*    System.out.println("OOOOO"+FirebaseDatabase.getInstance().getReference("user/agency")
                            .child(curUser()).child("DP").toString());
*/
                   /* Glide.with(getContext())
                            .load(FirebaseDatabase.getInstance().getReference("user/agency")
                                    .child(curUser()).child("DP").toString())
                            .into(imageView);
*/
                 //   Glide.with(Nav_draw_Fragment_Profile.this).load(mProfilePic).into(IVProfile_Pic);

                    //Picasso.with(Nav_draw_Fragment_Profile.this).load(mProfilePic).placeholder(R.drawable.ic_add_image).into(IVProfile_Pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

 }
    private void scaleImage(ImageView view) throws NoSuchElementException {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = null;

        try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            // Check bitmap is Ion drawable
            bitmap = Ion.with(view).getBitmap();
        }

        // Get current dimensions AND the desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = dpToPx(250);
        Log.i("Test", "original width = " + Integer.toString(width));
        Log.i("Test", "original height = " + Integer.toString(height));
        Log.i("Test", "bounding = " + Integer.toString(bounding));

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Log.i("Test", "xScale = " + Float.toString(xScale));
        Log.i("Test", "yScale = " + Float.toString(yScale));
        Log.i("Test", "scale = " + Float.toString(scale));

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        Log.i("Test", "scaled width = " + Integer.toString(width));
        Log.i("Test", "scaled height = " + Integer.toString(height));

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);

        Log.i("Test", "done");
    }

    private int dpToPx(int dp) {
        float density = getView().getContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }


  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_pic").child(AgencyProfile.getHashedEmail(userID));

            final Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri);
                IVProfile_Pic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] dt = baos.toByteArray();
            UploadTask uploadTask = filePath.putBytes(dt);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                    return;
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Uri downloadUrl = taskSnapshot.get();

                    *//*Map newImage = new HashMap();
                    newImage.put("profileImageUrl",downloadUrl.toString());
                    mAgencyDatabase.updateChildren(newImage);
                    finish();
                    return;*//*
                }
            });
            *//*resultUri = imageUri;
            IVProfile_Pic.setImageURI(resultUri);

            if(resultUri != null)
            {
                 Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),resultUri);
                    IVProfile_Pic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayInputStream baos = new ByteArrayInputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,20,baos);
                byte[] dt = baos.to;
                
            }*//*
        }
    }*/


}
