package in.ac.kiit.justtalk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.MoreObjects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.ac.kiit.justtalk.adapters.LivePlayerListAdapter;
import in.ac.kiit.justtalk.mailServices.Config;
import in.ac.kiit.justtalk.mailServices.SendMail;
import in.ac.kiit.justtalk.models.GDEvent;
import in.ac.kiit.justtalk.models.Scores;

public class playerActivity extends AppCompatActivity {
    TextView countdownText; // Text view which displays count down
    CountDownTimer countDownTimer;
    long TimeLeftinMiliseconds=420000; // 7 minutes converted into milliseconds
    boolean timerRunning;

    String gdID;
    String organiserID;
    String type, topic,timeStamp;
    int duration;
    String time;
    ArrayList <String> players;
    RecyclerView livePlayerList;
    Button save, send, cancel;
    LivePlayerListAdapter adapter;
    Intent intent;
    GDEvent event;
    FirebaseFirestore firestore;
    AlertDialog.Builder builder;

    private void getFromBundle(Bundle b){
        gdID = b.getString("gdID");
        organiserID = b.getString("organiserID");
        type = b.getString("type");
        topic = b.getString("topic");
        duration = b.getInt("duration");
        players = (ArrayList<String>) b.get("playerSet");
        timeStamp = b.getString("time");
    }


    private GDEvent createAnEvent(){

        ArrayList<Scores> scores = new ArrayList<>();

        for(int i=0; i<players.size(); i++){
            scores.add(new Scores(players.get(i)));
        }
        return new GDEvent(gdID, organiserID,timeStamp,type,topic,scores, duration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        countdownText=findViewById(R.id.countdown_text);
        livePlayerList = findViewById(R.id.live_player_list);

        send = findViewById(R.id.sendBtn);
        cancel=findViewById(R.id.cancelbtnBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(playerActivity.this);
                builder.setIcon(R.drawable.ic_mail_black_24dp)
                        .setTitle("DO YOU REALLY WANT TO DISMISS THIS LIVE SESSION?")
                        .setMessage("Press 'NO, RETURN' to return back to the live session.")
                        .setPositiveButton("Yes, DISMISS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                playerActivity.this.startActivity(new Intent(playerActivity.this, HomeActivity.class));
                                finish();

                            }
                        })
                        .setNegativeButton("NO,RETURN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                builder.create().show();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                Log.e("Organiser", name);

                builder = new AlertDialog.Builder(playerActivity.this);
                builder.setIcon(R.drawable.ic_mail_black_24dp)
                        .setTitle("Want to send the reports via mail?")
                        .setMessage("Press CANCEL, if you want to have continue evaluating.")
                        .setPositiveButton("Yes, SEND", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                firestore.collection("gd_events").document(event.getGdID()).set(event);
                                new SendMail(playerActivity.this, event,name).execute();

                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                builder.create().show();




            }
        });

        firestore = FirebaseFirestore.getInstance();

        save =  findViewById(R.id.save_btnBtn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataRender();


                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

       builder = new AlertDialog.Builder(playerActivity.this);
        builder.setIcon(R.drawable.ic_cloud_upload_black_24dp)
                .setTitle("Want to save it on cloud?")
                .setMessage("Press CANCEL, if you want to continue evaluating.")
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firestore.collection("gd_events").document(event.getGdID()).set(event);
                        Toast.makeText(playerActivity.this, "SAVED", Toast.LENGTH_LONG).show();


                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });


        intent= getIntent();
        Bundle b = intent.getExtras();

        getFromBundle(b);
        TimeLeftinMiliseconds = duration*60000;

        startTimer();

        event = createAnEvent();
        livePlayerList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new LivePlayerListAdapter(event,this);

        livePlayerList.setAdapter(adapter);

        //startTimer();
    }
    public void startstop(){
        if(timerRunning){
            stopTimer();
        }
        else{
            startTimer();
        }
    }



    public void startTimer(){
        countDownTimer=new CountDownTimer(TimeLeftinMiliseconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftinMiliseconds=millisUntilFinished;
                updateTimer();
            }@Override
            public void onFinish() {

            }
        }.start();
        timerRunning=true;
    }

    public void stopTimer(){
        countDownTimer.cancel();
        timerRunning=false;
    }
    public void updateTimer(){
        int minutes=(int)TimeLeftinMiliseconds/60000;
        int seconds=(int)TimeLeftinMiliseconds%60000/1000;
        String timeLeft=""+minutes+":";
        if(seconds<10) timeLeft+="0";
        timeLeft+=seconds;

        countdownText.setText(timeLeft);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Result OK", "error");
        Bundle b = data.getExtras();
        String code = b.getString("code");
        Log.d("code", code);
        String codeChunks[] = code.split("_");
        int position = Integer.parseInt(codeChunks[0]);
        Log.e("STRING", codeChunks[1]);
        switch (codeChunks[1]){
            case "fluency":{
                event.getPlayerIDs().get(position).setFluency(Integer.parseInt(codeChunks[2]));
                break;
            }
            case "body": {
                event.getPlayerIDs().get(position).setBodyLanguage(Integer.parseInt(codeChunks[2]));
                break;
            }
            case "lang":{
                event.getPlayerIDs().get(position).setLanguage(Integer.parseInt(codeChunks[2]));
                break;
            }
            case "content":{
                event.getPlayerIDs().get(position).setContent(Integer.parseInt(codeChunks[2]));
                break;
            }
            case "team":{
                event.getPlayerIDs().get(position).setTeamWork(Integer.parseInt(codeChunks[2]));
                break;
            }
        }
        adapter.update(event,position);
    }

    private void dataRender(){
        Log.e("Event ID ", event.getGdID());
        Log.e("Event Player 1 ", event.getPlayerIDs().get(0).getTeamWork()+"");
    }

    void saveEventToCloud(){

    }
}
