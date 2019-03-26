package in.ac.kiit.justtalk;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.ac.kiit.justtalk.adapters.LivePlayerListAdapter;
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
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(playerActivity.this, ScoreBookActivity.class));
            }
        });

        Bundle b = getIntent().getExtras();

        getFromBundle(b);
        TimeLeftinMiliseconds = duration*60000;

        startTimer();

        GDEvent event = createAnEvent();
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
        adapter.onActivityResult(requestCode,resultCode,data);
    }
}
