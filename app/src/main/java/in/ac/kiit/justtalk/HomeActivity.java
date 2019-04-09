package in.ac.kiit.justtalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

        avatar = findViewById(R.id.profile_image);
        mail = findViewById(R.id.user_mail);
        name = findViewById(R.id.user_name);
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user!=null) {
//
//            Picasso.with(HomeActivity.this).load(user.getPhotoUrl()).into(avatar);
//            mail.setText(user.getEmail());
//            name.setText(user.getDisplayName());
//        }


        //GDSessionAdapter adapter  = new GDSessionAdapter(getEvents(id),this);
        //recyclerView.setAdapter(adapter);

        if(events.size()==0){
            Log.e("NO EVENTS", "0");
        }
        recyclerView = findViewById(R.id.event_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter= new GDSessionAdapter(events,HomeActivity.this);
        recyclerView.setAdapter(adapter);





    }

    @Override
    protected void onResume() {
        super.onResume();
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
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about_us) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_profile){
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));

        }

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
