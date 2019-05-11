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

    EditText[] playerEdts = new EditText[12];
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
            if(mem.get(i).isEmpty()){
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
        playerEdts[0] = findViewById(R.id.edtPlayer1);
        playerEdts[1] = findViewById(R.id.edtPlayer2);
        playerEdts[2] = findViewById(R.id.edtPlayer3);
        playerEdts[3] = findViewById(R.id.edtPlayer4);
        playerEdts[4] = findViewById(R.id.edtPlayer5);
        playerEdts[5] = findViewById(R.id.edtPlayer6);
        playerEdts[6] = findViewById(R.id.edtPlayer7);
        playerEdts[7] = findViewById(R.id.edtPlayer8);
        playerEdts[8] = findViewById(R.id.edtPlayer9);
        playerEdts[9] = findViewById(R.id.edtPlayer10);
        playerEdts[10] = findViewById(R.id.edtPlayer11);
        playerEdts[11] = findViewById(R.id.edtPlayer12);

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
            case 6:{
                for(int i=6; i<playerEdts.length;i++){
                    playerEdts[i].setEnabled(false);
                }
                break;
            }
            case 7:{
                for(int i=7; i<playerEdts.length;i++){
                    playerEdts[i].setEnabled(false);
                }
                break;
            }
            case 8:{
                for(int i=8; i<playerEdts.length;i++){
                    playerEdts[i].setEnabled(false);
                }
                break;
            }

            case 9:{
                for(int i=9; i<playerEdts.length;i++){
                    playerEdts[i].setEnabled(false);
                }
                break;
            }

            case 10:{
                for(int i=7; i<playerEdts.length;i++){
                    playerEdts[i].setEnabled(false);
                }
                break;
            }

            case 11:{
                for(int i=7; i<playerEdts.length;i++){
                    playerEdts[i].setEnabled(false);
                }
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
                for(int i=0; i<playerEdts.length;i++){
                    roll.add(playerEdts[i].getText().toString());
                }

                roll =trimPlayers(roll);
                Log.e("TIMMED ROLL SIZE", roll.size()+"");

                for(int i=0;i<roll.size(); i++){
                    if(roll.get(i).length()==7){
                        String s = roll.get(i).substring(0,2);
                        if(!(s.equals("16")||s.equals("15")||s.equals("20")||s.equals("18")||s.equals("19")||s.equals("17")))
                        {
                            roll.remove(i);
                            Snackbar.make(view,"Please provide valid roll number.", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Snackbar.make(view,"Please provide valid roll numbers.", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                }

                //GDEvent gd = new GDEvent(gdID, id, timeStamp,type, topic, players , "7000");
               if(roll.size()>=6){
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
                   Snackbar.make(view,"Minimum 6 players required.", Snackbar.LENGTH_LONG).show();
                   return;
               }

            }
        });


    }
}
