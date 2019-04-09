package in.ac.kiit.justtalk;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import in.ac.kiit.justtalk.models.GDEvent;
import in.ac.kiit.justtalk.models.Scores;

public class MainActivity extends AppCompatActivity {


    String gdID;
    String organiserID;
    String type, topic, timeStamp;
    int no_of_players=3;
    int duration;

    EditText p1,p2,p3,p4,p5,p6;
    Button g2go, edit;

    private void getFromBundle(Bundle b){
        gdID = b.getString("gdID");
        organiserID = gdID;
        type = b.getString("type");
        topic = b.getString("topic");
        duration = b.getInt("dur");
        no_of_players = b.getInt("nPlayers");
    }

    private ArrayList<String> trimPlayers(ArrayList<String> mem){

        for(int i=0; i<mem.size(); i++){
            Log.e("TRIMMED PLAYERS", "--------"+mem.get(i)+"--------");
            if(mem.get(i).isEmpty()||mem.get(i).length()!=7){
                mem.remove(i);
                i--;
            }
        }

        for(int i=0; i<mem.size(); i++){
            Log.e("TRIMMED PLAYERS NEW", "--------"+mem.get(i)+"--------");
        }



        Log.e("TRIMMED PLAYERS", mem.size()+"");
        return mem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        g2go = findViewById(R.id.good_to_go_btn);
        p1 = findViewById(R.id.edtPlayer1);
        p2 = findViewById(R.id.edtPlayer2);
        p3 = findViewById(R.id.edtPlayer3);
        p4 = findViewById(R.id.edtPlayer4);
        p5 = findViewById(R.id.edtPlayer5);
        p6 = findViewById(R.id.edtPlayer6);

        edit = findViewById(R.id.back_btn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle b = getIntent().getExtras();
//        Log.e("Bundle", b.toString());

        getFromBundle(b);

        switch (no_of_players){
            case 3:{
                p4.setFocusable(false);
                p5.setFocusable(false);
                p6.setFocusable(false);

                p4.setEnabled(false);
                p5.setEnabled(false);
                p6.setEnabled(false);
                break;
            }
            case 4:{
                p5.setFocusable(false);
                p6.setFocusable(false);

                p5.setEnabled(false);
                p6.setEnabled(false);
                break;
            }
            case 5:{
                p6.setFocusable(false);

                p6.setEnabled(false);
                break;
            }
        }

        g2go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String mail = user.getEmail();
                String id = mail.substring(0, mail.indexOf('@'));
                timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

                gdID = id+"_"+timeStamp;
                organiserID = id;
                ArrayList<String> roll = new ArrayList<String>();
                roll.add(p1.getText().toString());
                roll.add(p2.getText().toString());
                roll.add(p3.getText().toString());
                roll.add(p4.getText().toString());
                roll.add(p5.getText().toString());
                roll.add(p6.getText().toString());

                roll =trimPlayers(roll);

                for(int i=0;i<roll.size(); i++){
                    if(roll.get(i).length()!=7){
                        String s = roll.get(i).substring(0,2);
                        if(!(s.equals("16")||s.equals("15")||s.equals("20")||s.equals("18")||s.equals("19")))
                        {
                            roll.remove(i);
                            Snackbar.make(view,"Please provide valid roll number.", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

                //GDEvent gd = new GDEvent(gdID, id, timeStamp,type, topic, players , "7000");
               if(roll.size()>=3){
                   Intent intent =new Intent(MainActivity.this, playerActivity.class);
                   intent.putExtra("gdID", gdID);
                   intent.putExtra("organiserID", organiserID);
                   intent.putExtra("time", timeStamp);
                   intent.putExtra("type", type);
                   intent.putExtra("topic", topic);
                   intent.putExtra("playerSet", roll);
                   intent.putExtra("duration", duration);
                   startActivity(intent);
                   finish();
               }
               else{
                   Snackbar.make(view,"Minimum 3 players required.", Snackbar.LENGTH_LONG).show();
                   return;
               }

            }
        });


    }
}
