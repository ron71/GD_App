package in.ac.kiit.justtalk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ac.kiit.justtalk.adapters.GDSessionAdapter;
import in.ac.kiit.justtalk.models.AppUser;
import in.ac.kiit.justtalk.models.GDEvent;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ImageView avatar;
    TextView mail,name;
    AppUser appuser;
    GDSessionAdapter adapter;
    ArrayList<GDEvent> events = new ArrayList<>();
    FirebaseUser user;
    int exit=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ConnectivityHelper.isConnectedToNetwork(this)) {
            //Show the connected screen
            setContentView(R.layout.activity_home);
        }
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String id = user.getEmail().substring(0,user.getEmail().indexOf("@"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document!=null){
                        Log.e("Document ROHAN", document.toString());
                        appuser = task.getResult().toObject(AppUser.class);
                        getEvents(appuser);

                    }
                }
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit=1;

                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(id);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document!=null){
                                Log.e("Document", document.toString());
                                appuser = task.getResult().toObject(AppUser.class);
                                if(appuser.getType().equals("master")){

                                    startActivity(new Intent(HomeActivity.this, InitiateGDActivity.class));
                                    finish();
                                }
                                else {
                                    Snackbar.make(getCurrentFocus(), "Sorry, you don't have master control to conduct a GD.",Snackbar.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                    }
                });
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        avatar = headerView.findViewById(R.id.profile_image);
        mail = headerView.findViewById(R.id.user_mail);
        name = headerView.findViewById(R.id.user_name);




        if(user!=null) {

            Picasso.with(HomeActivity.this).load(user.getPhotoUrl()).into(avatar);
            mail.setText(user.getEmail());
            name.setText(user.getDisplayName());
        }


        if(events.size()==0){
            Log.e("NO EVENTS", "0");
        }
        recyclerView = findViewById(R.id.event_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter= new GDSessionAdapter(events,HomeActivity.this);
        recyclerView.setAdapter(adapter);





    }


    private void logOut(){

            try{
                SQLiteDatabase db = openOrCreateDatabase("gddb", MODE_PRIVATE,null);
                db.execSQL("DELETE FROM USER");
            }
            catch (SQLException e){
                e.printStackTrace();

            }
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));

        finishAffinity();

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            logOut();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        exit=1;
        adapter.notifyDataSetChanged();
    }

    void getEvents(AppUser u){
        //events = new ArrayList<>();
        Log.e("getEvents()", "139");
        if(u!=null){
            Log.e("getEvents()", "141");
            for(int i=0; i<u.getVents().size();i++){
                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("gd_events").document(appuser.getVents().get(i));
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.e("getEvents()", "147");
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document!=null){
                                Log.e("getEvents()", "151");
                                Log.e("Document EVENT", document.toString());
                                GDEvent ev = task.getResult().toObject(GDEvent.class);
                                events.add(ev);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }


        }else{
            Log.e("GDEVENTS", "NULL");
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            exit=0;
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(exit==2) {
                super.onBackPressed();
                finishAffinity();
            }
            else{
                exit++;
                Toast.makeText(HomeActivity.this, "Touch BACK again to exit.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        exit=0;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_profile){
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));

        }
        else if (id == R.id.about_us) {
            Dialog d = new Dialog(HomeActivity.this);
            d.setContentView(R.layout.abou_app_layout);
            d.setCancelable(true);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            d.show();

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, "kiit.gdclub@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Query/Suggestion - "+user.getEmail().substring(0, user.getEmail().indexOf("@")));
            intent.putExtra(Intent.EXTRA_TEXT, "Mail on : gdclubkiit@gmail.com\nType your content here and select any one query or suggestion in subject and omit the other one.");

            startActivity(Intent.createChooser(intent, "Send email..."));
        }
        else if(id==R.id.log_out){
            FirebaseAuth.getInstance().signOut();
            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
