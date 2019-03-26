package in.ac.kiit.justtalk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
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
import com.google.gson.internal.bind.SqlDateTypeAdapter;

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
    int flag[] = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.profile_name);
        mail = findViewById(R.id.profile_mail);
        branch = findViewById(R.id.branch_edt);
        rgb = findViewById(R.id.year_group);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
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
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);



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
        fdb = FirebaseFirestore.getInstance();

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

            Log.e("DATA FLOW", "ALREADY PRESENT IN DB");
            c.moveToFirst();
            /*
            * Render Informations on profile Activity
            * */

            String brnch = c.getString(3).toUpperCase();
            type = c.getString(4);
            int yr = c.getInt(5);

            branch.setText(brnch);

            switch (yr){
                case 1:rb1.setSelected(true);break;
                case 2:rb2.setSelected(true); break;
                case 3:rb3.setSelected(true); break;
                case 4:rb4.setSelected(true); break;
            }


            LinearLayout l=null;
            if(type.equals("master")){
                l = findViewById(R.id.member_des);
            }else{
                l = findViewById(R.id.memberList);
            }
            ViewGroup parent = (ViewGroup) l.getParent();
            if (parent != null) {
                parent.removeView(l);
            }

            String[] members = c.getString(6).split(",");
            String acesscode = c.getString(7);
            Log.e("accessCodeDB", acesscode);
            for(int i=0; i< members.length; i++) {
                if (members[i] != "") {
                    if (i == 0) {

                        m1.setText(members[i]);
                        if (acesscode.charAt(i) == '1') {
                            img1.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                            flag[i] = 1;
                        }

                    }
                    if (i == 1) {
                        m2.setText(members[i]);
                        if (acesscode.charAt(i) == '1') {
                            img2.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                            flag[i] = 1;
                        }
                    }
                    if (i == 2) {
                        m3.setText(members[i]);
                        if (acesscode.charAt(i) == '1') {
                            img3.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                            flag[i] = 1;
                        }
                    }
                    if (i == 3) {
                        m4.setText(members[i]);
                        if (acesscode.charAt(i) == '1') {
                            img4.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                            flag[i] = 1;
                        }
                    }
                    if (i == 4) {
                        m5.setText(members[i]);
                        if (acesscode.charAt(i) == '1') {
                            img5.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                            flag[i] = 1;
                        }
                    }
                    if (i == 5) {
                        m6.setText(members[i]);
                        if (acesscode.charAt(i) == '1') {
                            img6.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                            flag[i] = 1;
                        }
                    }
                }
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
//      Log.e("BRANCH", user.getBranch());

      LinearLayout l=null;
      if(user.getType().equals("master")){
          l = findViewById(R.id.member_des);


      }else{
          l = findViewById(R.id.memberList);
      }
      ViewGroup parent = (ViewGroup) l.getParent();
      if (parent != null) {
          parent.removeView(l);
      }

   }

    void addUserInFireBase(AppUser user){
        if(user!=null)
            fdb.collection("users").document(user.getUserID()).set(user);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.save_btn:{
                // Saving Profile
                ArrayList<String> mem = new ArrayList<String>();
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
                mem.add(m1.getText().toString());
                mem.add(m2.getText().toString());
                mem.add(m3.getText().toString());
                mem.add(m4.getText().toString());
                mem.add(m5.getText().toString());
                mem.add(m6.getText().toString());

                mem = trimList(mem);


                Log.e("Type", type);
                AppUser user = new AppUser(user_mail.substring(0,user_mail.indexOf("@")),user_mail,mem,user_name, year, type,brnch.toUpperCase());
                addUserInFireBase(user);
                saveToDatabase(user, flag);
                startActivity(new Intent(this,HomeActivity.class));
                finish();
                break;
            }
            case R.id.super_control_btn1:{
                if(m1.getText().toString().isEmpty()){
                    m1.setError("No Roll Number associated provided!");
                    return;
                }

                if(flag[0]==1){
                    img1.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp));
                    flag[0]=0;
                }
                else{
                    img1.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                    flag[0]=1;
                }
                break;
            }
            case R.id.super_control_btn2:{
                if(m2.getText().toString().isEmpty()){
                    m2.setError("No Roll Number associated provided!");
                    return;
                }

                if(flag[1]==1){
                    img2.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp));
                    flag[1]=0;
                }
                else{
                    img2.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                    flag[1]=1;
                }

                break;
            }
            case R.id.super_control_btn3:{
                if(m3.getText().toString().isEmpty()){
                    m3.setError("No Roll Number associated provided!");
                    return;
                }

                if(flag[2]==1){
                    img3.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp));
                    flag[2]=0;
                }
                else{
                    img3.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                    flag[2]=1;

                }
                break;
            }
            case R.id.super_control_btn4:{
                if(m4.getText().toString().isEmpty()){
                    m4.setError("No Roll Number associated provided!");
                    return;
                }

                if(flag[3]==1){
                    img4.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp));
                    flag[3]=0;
                }
                else{
                    img4.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                    flag[3]=1;
                }
                break;
            }
            case R.id.super_control_btn5:{
                if(m5.getText().toString().isEmpty()){
                    m5.setError("No Roll Number associated provided!");
                    return;
                }

                if(flag[4]==1){
                    img5.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp));
                    flag[4]=0;
                }
                else{
                    img5.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                    flag[4]=1;
                }
                break;
            }
            case R.id.super_control_btn6:{
                if(m6.getText().toString().isEmpty()){
                    m6.setError("No Roll Number associated provided!");
                    return;
                }

                if(flag[5]==1){
                    img6.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp));
                    flag[5]=0;
                }
                else{
                    img6.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                    flag[5]=1;
                }
                break;
            }
        }

    }

    ArrayList<String> trimList(ArrayList<String> mem){

        for(int i=0; i<mem.size(); i++){
            if(mem.get(i).equals("")|| mem.get(i).length()!=7){
                mem.remove(i);
            }
        }
        return mem;
    }

    void saveToDatabase(AppUser user, int[] flag){
        try{
             String acessCode = getAcessCode(flag);
             String memberList = getMembersInString(user.getMembers());

            String sql = "DELETE FROM USER WHERE userId='"+user.getUserID()+"'";
            SQLiteDatabase db = openOrCreateDatabase("gddb",MODE_PRIVATE, null);
            db.execSQL(sql);
            sql = "INSERT INTO USER VALUES('"+user.getUserID()+"','"+user.getName()+"','"+user.getEmailID()+"','"+user.getBranch()+"','"+user.getType()+"',"+user.getYear()+",'"+memberList+"','"+acessCode+"');";
            db.execSQL(sql);
            Log.e("SAVE Status", "SAVED");

        }catch (SQLException ex){
            Log.e("Error", "Invalid Insertion");
        }
    }

    private String getAcessCode(int[] flag){
        String acessCode="";
        for(int i=0; i<flag.length; i++){
            acessCode+=flag[i]+"";
        }
        return acessCode;
    }

   String getMembersInString(ArrayList<String> mem){
        String s = "";
        for(int i=0; i<mem.size(); i++){
            s+=mem.get(i)+",";
        }
        s = s.substring(0,s.length()-1);
       Log.e("Member ", s);
        return s;
    }

}
