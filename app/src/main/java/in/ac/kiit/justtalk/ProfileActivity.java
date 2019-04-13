package in.ac.kiit.justtalk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
    String url;
    String year = "";
    String type;
    int flag[] = new int[6];
    boolean isNewUser = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!ConnectivityHelper.isConnectedToNetwork(this)) {
            Intent i = new Intent(this, NoInternetAcitivty.class);
            i.putExtra("status", 1);
            startActivity(i);
            finishAffinity();

        }
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

            url = user.getPhotoUrl().toString();
            Log.e("URL", url);
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
        getUserFromFirebase(id);
//        if(isNotAvailableInLocalDB()){
//            getUserFromFirebase(id);
//        }
//        else{
//
//            Log.e("DATA FLOW", "ALREADY PRESENT IN DB");
//            c.moveToFirst();
//            /*
//            * Render Informations on profile Activity
//            * */
//
//            String brnch = c.getString(3).toUpperCase();
//            type = c.getString(4);
//            int yr = c.getInt(5);
//
//            branch.setText(brnch);
//
//            switch (yr){
//                case 1:rb1.setSelected(true);break;
//                case 2:rb2.setSelected(true); break;
//                case 3:rb3.setSelected(true); break;
//                case 4:rb4.setSelected(true); break;
//            }
//
//
//            LinearLayout l=null;
//            if(type.equals("master")){
//                l = findViewById(R.id.member_des);
//            }else{
//                l = findViewById(R.id.memberList);
//            }
//            ViewGroup parent = (ViewGroup) l.getParent();
//            if (parent != null) {
//                parent.removeView(l);
//            }
//
//            String[] members = c.getString(6).split(",");
//            String acesscode = c.getString(7);
//            Log.e("accessCodeDB", acesscode);
//            for(int i=0; i< members.length; i++) {
//                if (members[i] != "") {
//                    if (i == 0) {
//
//                        m1.setText(members[i]);
//                        if (acesscode.charAt(i) == '1') {
//                            img1.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
//                            flag[i] = 1;
//                        }
//
//                    }
//                    if (i == 1) {
//                        m2.setText(members[i]);
//                        if (acesscode.charAt(i) == '1') {
//                            img2.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
//                            flag[i] = 1;
//                        }
//                    }
//                    if (i == 2) {
//                        m3.setText(members[i]);
//                        if (acesscode.charAt(i) == '1') {
//                            img3.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
//                            flag[i] = 1;
//                        }
//                    }
//                    if (i == 3) {
//                        m4.setText(members[i]);
//                        if (acesscode.charAt(i) == '1') {
//                            img4.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
//                            flag[i] = 1;
//                        }
//                    }
//                    if (i == 4) {
//                        m5.setText(members[i]);
//                        if (acesscode.charAt(i) == '1') {
//                            img5.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
//                            flag[i] = 1;
//                        }
//                    }
//                    if (i == 5) {
//                        m6.setText(members[i]);
//                        if (acesscode.charAt(i) == '1') {
//                            img6.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
//                            flag[i] = 1;
//                        }
//                    }
//                }
//            }
//        }

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
                        Log.e("Document", document.toString());
                      appuser = task.getResult().toObject(AppUser.class);
                      if(appuser==null){
                          isNewUser=true;
                          Snackbar.make(name,"WELCOME TO GD-CLUB!", Snackbar.LENGTH_LONG).show();
                          LinearLayout l = findViewById(R.id.memberList);

                          ViewGroup parent = (ViewGroup) l.getParent();
                          if (parent != null) {
                              parent.removeView(l);
                          }
                          return;
                      }else {

                          renderScreen(appuser);

                      }
                    }
                }
           }
       });
   }

   void memberStatusUpdate(final String id, final int flag){
        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(id);
       documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if(task.isSuccessful()){
                   DocumentSnapshot document = task.getResult();
                   if(document!=null){
                       Log.e("Document", document.toString());
                       AppUser u = task.getResult().toObject(AppUser.class);
                       if(u!=null){
                           if(flag==0){
                               u.setType("member");
                               documentReference.set(u);
                               Snackbar.make(getCurrentFocus(), id+" is now member.", Snackbar.LENGTH_SHORT).show();
                           }
                           else{
                               u.setType("master");
                               documentReference.set(u);
                               Snackbar.make(getCurrentFocus(), id+" is now master.", Snackbar.LENGTH_SHORT).show();
                           }
                       }
                   }
               }
           }
       });
   }

  void renderScreen(AppUser user){
      branch.setText(user.getBranch());
//      Log.e("BRANCH", user.getBranch());
      switch (user.getYear()){
          case "1": rb1.setChecked(true); break;
          case "2": rb2.setChecked(true); break;
          case "3": rb3.setChecked(true); break;
          case "4": rb4.setChecked(true); break;
      }

      LinearLayout l=null;
      if(user.getType().equals("master")){
          l = findViewById(R.id.member_des);
          int i=0;
          switch (user.getMembers().size()){
              case 1: {
                  m1.setText(user.getMembers().get(0));
                  displayMemberStatus(user.getMembers().get(0),0,img1);
                  break;
              }
              case 2: {
                  m1.setText(user.getMembers().get(0));
                  displayMemberStatus(user.getMembers().get(0),0,img1);
                  m2.setText(user.getMembers().get(1));
                  displayMemberStatus(user.getMembers().get(1),1,img2);
                    break;
              }
              case 3:{
                  m1.setText(user.getMembers().get(0));
                  displayMemberStatus(user.getMembers().get(0),0,img1);
                  m2.setText(user.getMembers().get(1));
                  displayMemberStatus(user.getMembers().get(1),1,img2);
                  m3.setText(user.getMembers().get(2));
                  displayMemberStatus(user.getMembers().get(2),2,img3);
                  break;
              }
              case 4 :{

                  m1.setText(user.getMembers().get(0));
                  displayMemberStatus(user.getMembers().get(0),0,img1);
                  m2.setText(user.getMembers().get(1));
                  displayMemberStatus(user.getMembers().get(1),1,img2);
                  m3.setText(user.getMembers().get(2));
                  displayMemberStatus(user.getMembers().get(2),2,img3);
                  m4.setText(user.getMembers().get(3));
                  displayMemberStatus(user.getMembers().get(3),3,img4);
                  break;
              }
              case 5:{
                  m1.setText(user.getMembers().get(0));
                  displayMemberStatus(user.getMembers().get(0),0,img1);
                  m2.setText(user.getMembers().get(1));
                  displayMemberStatus(user.getMembers().get(1),1,img2);
                  m3.setText(user.getMembers().get(2));
                  displayMemberStatus(user.getMembers().get(2),2,img3);
                  m4.setText(user.getMembers().get(3));
                  displayMemberStatus(user.getMembers().get(3),3,img4);
                  m5.setText(user.getMembers().get(4));
                  displayMemberStatus(user.getMembers().get(4),4,img5);

                  break;
              }
              case 6:{

                  m1.setText(user.getMembers().get(0));
                  displayMemberStatus(user.getMembers().get(0),0,img1);
                  displayMemberStatus(user.getMembers().get(1),1,img2);
                  displayMemberStatus(user.getMembers().get(2),2,img3);
                  displayMemberStatus(user.getMembers().get(3),3,img4);
                  displayMemberStatus(user.getMembers().get(4),4,img5);
                  displayMemberStatus(user.getMembers().get(5),5,img6);
                  m2.setText(user.getMembers().get(1));
                  m3.setText(user.getMembers().get(2));
                  m4.setText(user.getMembers().get(3));
                  m5.setText(user.getMembers().get(4));
                  m6.setText(user.getMembers().get(5));
                  break;
              }


          }



      }else{
          l = findViewById(R.id.memberList);
      }
      ViewGroup parent = (ViewGroup) l.getParent();
      if (parent != null) {
          parent.removeView(l);
      }

   }

    void displayMemberStatus(final String id, final int index, ImageButton b){
        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document!=null){
                        Log.e("Document", document.toString());
                        AppUser u = task.getResult().toObject(AppUser.class);
                        if(u!=null){
                                if(u.getType().equals("master")){
                                    Log.e(""+index, "Master");
                                    flag[index] = 1;
                                    // TODO:
                                    switch (index){
                                        case 0: img1.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue)); break;
                                        case 1: img2.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue)); break;
                                        case 2: img3.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue)); break;
                                        case 3: img4.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue)); break;
                                        case 4: img5.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue)); break;
                                        case 5: img6.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue)); break;
                                    }

                                }else {
                                    Log.e(""+u.getUserID(), "Member");
                                    flag[index] = 0;
                                    // TODO:
                                    switch (index){
                                        case 0: img1.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp)); break;
                                        case 1: img1.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp)); break;
                                        case 2: img3.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp)); break;
                                        case 3: img4.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp)); break;
                                        case 4: img5.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp)); break;
                                        case 5: img6.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp)); break;
                                    }
                                }
                            }

                        }

                }
            }
        });
        Log.e(":CODE:",getAcessCode(flag));

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
                Log.e(":CODE:",getAcessCode(flag));
//                if(findViewById(R.id.member_des).getVisibility()==View.INVISIBLE){
//                    type = "master";
//                }else {
//                    type="member";
//                }
                if(appuser!=null){
                    type = appuser.getType();
                }else {
                    type="member";
                }

                mem.add(m1.getText().toString());
                mem.add(m2.getText().toString());
                mem.add(m3.getText().toString());
                mem.add(m4.getText().toString());
                mem.add(m5.getText().toString());
                mem.add(m6.getText().toString());

                mem = trimList(mem);
                if(!isNewUser){
                    if(mem.contains(appuser.getUserID())){
                        Snackbar.make(getCurrentFocus(), "Don't enter your own ID.", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }


                Log.e("Type", type);
                AppUser user = new AppUser(user_mail.substring(0,user_mail.indexOf("@")),user_mail,mem,user_name, year, type,brnch.toUpperCase(), url);
               if(isNewUser==false){
                   user.setVents(appuser.getVents());
                   user.setVentsConducted(appuser.getVentsConducted());

               }

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
                    //deselected to member
                    img1.setImageDrawable(getDrawable(R.drawable.ic_verified_user_black_24dp));
                    flag[0]=0;

                }
                else{
                    img1.setImageDrawable(getDrawable(R.drawable.ic_verified_user_sky_blue));
                    flag[0]=1;
                }

                memberStatusUpdate(m1.getText().toString(), flag[0]);


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
                memberStatusUpdate(m2.getText().toString(), flag[1]);
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
                memberStatusUpdate(m3.getText().toString(), flag[2]);
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
                memberStatusUpdate(m4.getText().toString(), flag[3]);
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
                memberStatusUpdate(m5.getText().toString(), flag[4]);
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
                memberStatusUpdate(m6.getText().toString(), flag[5]);
                break;
            }
        }

    }

    ArrayList<String> trimList(ArrayList<String> mem){

        for(int i=0; i<mem.size(); i++){
            if(mem.get(i).equals("")|| mem.get(i).length()!=7){
                mem.remove(i);
                i--;
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
        Log.e(":CODE:",acessCode);
        return acessCode;
    }

   String getMembersInString(ArrayList<String> mem){
        String s = "";
        for(int i=0; i<mem.size(); i++){
            s+=mem.get(i)+",";
        }
        if(mem.size()==0){
            return "";
        }
        s = s.substring(0,s.length()-1);
       Log.e("Member ", s);
        return s;
    }

}
