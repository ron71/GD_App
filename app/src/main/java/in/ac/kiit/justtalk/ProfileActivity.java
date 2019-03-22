package in.ac.kiit.justtalk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import in.ac.kiit.justtalk.models.AppUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView name, mail;
    EditText branch, m1, m2, m3, m4, m5, m6;
    RadioGroup rgb;
    SQLiteDatabase db;
    FirebaseFirestore fdb;
    Cursor c;
    AppUser appuser;
    RadioButton rb1,rb2,rb3,rb4;
    ImageButton img1, img2, img3, img4, img5, img6;
    Button saveBtn;
    String user_name="", user_mail="";
    String year = "";
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.profile_name);
        mail = findViewById(R.id.profile_mail);
        branch = findViewById(R.id.branch_edt);
        rgb = findViewById(R.id.year_group);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb1);
        rb3 = findViewById(R.id.rb1);
        rb1 = findViewById(R.id.rb1);
        saveBtn = findViewById(R.id.save_btn);
        img1 = findViewById(R.id.super_control_btn1);
        img2 = findViewById(R.id.super_control_btn2);
        img5 = findViewById(R.id.super_control_btn5);
        img3 = findViewById(R.id.super_control_btn3);
        img4 = findViewById(R.id.super_control_btn4);
        img6 = findViewById(R.id.super_control_btn6);

        m1 = findViewById(R.id.member_roll_edt1);
        m2 = findViewById(R.id.member_roll_edt2);
        m3 = findViewById(R.id.member_roll_edt3);
        m4 = findViewById(R.id.member_roll_edt4);
        m5 = findViewById(R.id.member_roll_edt5);
        m6 = findViewById(R.id.member_roll_edt6);

        saveBtn.setOnClickListener(this);
        m1.setOnClickListener(this);
        m2.setOnClickListener(this);
        m3.setOnClickListener(this);
        m4.setOnClickListener(this);
        m5.setOnClickListener(this);
        m6.setOnClickListener(this);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user_name = user.getDisplayName();
            user_mail = user.getEmail();
        }else{
            startActivity(new Intent(ProfileActivity.this, PromptingErrorActivity.class));
            finish();
        }

        name.setText(user_name);
        mail.setText(user_mail);


        rgb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rb1)
                    year = "1";
                if(i==R.id.rb2)
                    year = "2";
                if(i==R.id.rb3)
                    year = "3";
                if(i==R.id.rb4)
                    year = "4";
            }
        });



        String id = user_mail.substring(0,user_mail.indexOf("@"));

        if(isNotAvailableInLocalDB()){
            getUserFromFirebase(id);
        }
        else{
            c.moveToFirst();
            /*
            * Render Informations on profile Activity
            * */
            ArrayList<String> mem = new ArrayList<>();
            String brnch = c.getString(3).toUpperCase();
            String type = c.getString(4).toUpperCase();
            int yr = c.getInt(5);
            String[] members = c.getString(6).split(",");
            for(int i=0; i< members.length; i++){
                mem.add(members[i]);
            }


        }

    }

    boolean isNotAvailableInLocalDB(){

        String sql = "SELECT * FROM USER;";
        db= openOrCreateDatabase("gddb", MODE_PRIVATE, null);
        c = db.rawQuery(sql,null);
        if(!c.moveToNext()){
            // no entries
            return true;
        }
        return false;

    }

   void getUserFromFirebase(String id){
       DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(id);
       documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document!=null){

                      appuser = task.getResult().toObject(AppUser.class);
                      renderScreen(appuser);
                    }else{
                       LinearLayout l = findViewById(R.id.member_des);

                    ViewGroup parent = (ViewGroup) l.getParent();
                    if (parent != null) {
                        parent.removeView(l);
                    }
                        return;
                    }

                }
           }
       });
   }

  void renderScreen(AppUser user){
      branch.setText(user.getBranch());
      Log.e("BRANCH", user.getBranch());

      LinearLayout l=null;
      if(!user.getType().equals("member")){
          l = findViewById(R.id.memberList);


      }else{
          l = findViewById(R.id.member_des);
      }
      ViewGroup parent = (ViewGroup) l.getParent();
      if (parent != null) {
          parent.removeView(l);
      }

   }

    void addUserInFireBase(AppUser user){
        fdb.collection("users").document(user.getUserID()).set(user);
    }

    @Override
    public void onClick(View view) {

        ArrayList<String> mem = new ArrayList<>();
        switch (view.getId()){
            case R.id.save_btn:{
                // Saving Profile

                String brnch = branch.getText().toString();
                if(brnch.isEmpty()){
                    branch.setError("This field is mandatory.");
                    return;
                }
                if(year==""){
                    if(!brnch.equalsIgnoreCase("nil")){
                        rb1.setError("What's your year dude!");
                        return;
                    }
                }
                if(findViewById(R.id.memberList).getVisibility()==View.INVISIBLE){
                    type = "member";
                }else {
                    type="master";
                }

                AppUser user = new AppUser(user_mail.substring(0,user_mail.indexOf("@")),user_mail,mem,user_name, year, type,brnch.toUpperCase());
                addUserInFireBase(user);
                break;
            }
            case R.id.super_control_btn1:{
                if(m1.getText().toString().isEmpty()){
                    m1.setError("No Roll Number associated provided!");
                    return;
                }
                String roll = m1.getText().toString();
                if(roll.length()==7){
                    mem.add(roll);
                }else{
                    m1.setError("Enter correct Roll Number!");
                }

                break;
            }
            case R.id.super_control_btn2:{
                if(m2.getText().toString().isEmpty()){
                    m2.setError("No Roll Number associated provided!");
                    return;
                }

                String roll = m2.getText().toString();
                if(roll.length()==7){
                    mem.add(roll);
                }else{
                    m2.setError("Enter correct Roll Number!");
                }
                break;
            }
            case R.id.super_control_btn3:{
                if(m3.getText().toString().isEmpty()){
                    m3.setError("No Roll Number associated provided!");
                    return;
                }

                String roll = m3.getText().toString();
                if(roll.length()==7){
                    mem.add(roll);
                }else{
                    m3.setError("Enter correct Roll Number!");
                }
                break;
            }
            case R.id.super_control_btn4:{
                if(m4.getText().toString().isEmpty()){
                    m4.setError("No Roll Number associated provided!");
                    return;
                }

                String roll = m4.getText().toString();
                if(roll.length()==7){
                    mem.add(roll);
                }else{
                    m4.setError("Enter correct Roll Number!");
                }
                break;
            }
            case R.id.super_control_btn5:{
                if(m5.getText().toString().isEmpty()){
                    m5.setError("No Roll Number associated provided!");
                    return;
                }
                String roll = m5.getText().toString();
                if(roll.length()==7){
                    mem.add(roll);
                }else{
                    m5.setError("Enter correct Roll Number!");
                }
                break;
            }
            case R.id.super_control_btn6:{
                if(m6.getText().toString().isEmpty()){
                    m6.setError("No Roll Number associated provided!");
                    return;
                }

                String roll = m6.getText().toString();
                if(roll.length()==7){
                    mem.add(roll);
                }else{
                    m6.setError("Enter correct Roll Number!");
                }
                break;
            }
        }

    }
}
