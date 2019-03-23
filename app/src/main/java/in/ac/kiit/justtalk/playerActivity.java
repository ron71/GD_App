package in.ac.kiit.justtalk;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class playerActivity extends AppCompatActivity {
    TextView countdownText; // Text view which displays count down
    Button countdownButton; //Button that starts and pauses timer
    CountDownTimer countDownTimer;
    long TimeLeftinMiliseconds=420000; // 7 minutes converted into milliseconds
    boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        countdownText=findViewById(R.id.countdown_text);
        countdownButton=findViewById(R.id.countdown_button);


        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startstop();

            }
        });
        updateTimer();
        startTimer();
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
        countdownButton.setText("PAUSE");
        timerRunning=true;
    }

    public void stopTimer(){
        countDownTimer.cancel();
        countdownButton.setText("START");
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

}
