package com.example.atry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.CollationElementIterator;
import java.util.HashMap;
import java.util.Objects;

public class Nav_Draw_Agency extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private  Toolbar toolbar;
    private NavigationView navigationView;
    private  View headerLayout ;
    private TextView userEmail,userName;
    public static ImageView userDp;
    private TextView userPhone;

    private String curUser(){

        return  FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_draw_a);

        //DataBaseOpOfAgency.LoadDP();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout= findViewById(R.id.nav_draw_agencyid);
        navigationView=findViewById(R.id.nav_draw_a_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        headerLayout= navigationView.getHeaderView(0);
        toolbar= findViewById(R.id.nav_draw_a_toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toolbar.setVisibility(View.VISIBLE);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_draw_a_frag,
                new Nav_draw_Fragment_Profile()).commit();

            navigationView.setCheckedItem(R.id.nav_a_profile);

        userEmail=headerLayout.findViewById(R.id.Nav_agency_header_email);
        userName=headerLayout.findViewById(R.id.Nav_agency_header_name);
        userDp=headerLayout.findViewById(R.id.Nav_agency_header_dp);
        userPhone=headerLayout.findViewById(R.id.Nav_agency_header_phone);

        loadUserDetails();
        userDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //userDPShow();
            }
        });
    }


   
    private void userDPShow() {

        Bitmap bmp = ((BitmapDrawable) userDp.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent=new Intent(getApplicationContext(),PopUpDp.class);

        intent.putExtra("picture", byteArray);
        startActivity(intent);

    }

    private void loadUserDetails() {


        FirebaseUser U =FirebaseAuth.getInstance().getCurrentUser();
        assert U != null;
        userEmail.setText(U.getEmail());

        DatabaseReference d= FirebaseDatabase.getInstance().getReference("user/agency").child(U.getUid());
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap M= (HashMap) dataSnapshot.getValue();
                userName.setText(Objects.requireNonNull(M.get("name")).toString());
                userPhone.setText(Objects.requireNonNull(M.get("number")).toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*
        d=FirebaseDatabase.getInstance().getReference("user/agency")
                .child(curUser());

        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                HashMap M= (HashMap) dataSnapshot.getValue();
                Glide.with(getApplicationContext())
                        .load(M.get("DP"))
                        .into(userDp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.animate();
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        toolbar.setVisibility(View.VISIBLE);

        switch (menuItem.getItemId()){

            case R.id.nav_a_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_draw_a_frag,
                        new Nav_draw_Fragment_Profile()).commit();
                toolbar.setVisibility(View.VISIBLE);
                navigationView.setCheckedItem(R.id.nav_a_profile);

                break;
                case R.id.nav_a_up_tour:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_draw_a_frag,
                        new Nav_draw_Fragment_Up_Tour()).commit();
                    toolbar.setVisibility(View.VISIBLE);
                    navigationView.setCheckedItem(R.id.nav_a_up_tour);

                    break;
                case R.id.nav_a_cur_tour:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_draw_a_frag,
                        new Nav_draw_Fragment_Cur_Tour()).commit();
                    toolbar.setVisibility(View.VISIBLE);
                    navigationView.setCheckedItem(R.id.nav_a_cur_tour);

                    break;
                case R.id.nav_a_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_draw_a_frag,
                        new Nav_draw_Fragment_Settings()).commit();
                toolbar.animate();
                toolbar.setVisibility(View.GONE);
                navigationView.setCheckedItem(R.id.nav_a_setting);
                    break;
                case R.id.nav_a_log:

                    DLogOut dLogOut = new DLogOut();
                    dLogOut.show(getSupportFragmentManager(), "XXX");

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        //toolbar.setVisibility(View.GONE);

        return true;
    }
}
