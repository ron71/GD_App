package in.ac.kiit.justtalk;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InitiateGDActivity extends AppCompatActivity {
    EditText topic_edt;
    String type, topic, timeStamp,gdID;
    RadioGroup topicRG, durRG, playerRG;
    int no_of_players=6;
    Button next;
    TextView id_txt;
    int dur;
    Spinner spinner;
    String[] NoOfPlayers = {"6 Players","7 Players","8 Players","9 Players","10 Players","11 Players","12 Players"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!ConnectivityHelper.isConnectedToNetwork(this)) {
            Intent i = new Intent(this, NoInternetAcitivty.class);
            i.putExtra("status", 2);
            startActivity(i);
            finishAffinity();

        }
        setContentView(R.layout.activity_initiate_gd);

        topic_edt = findViewById(R.id.topic_edt);
        topicRG = findViewById(R.id.gd_type_rg);
        durRG = findViewById(R.id.dur_rg);
       // playerRG = findViewById(R.id.no_player_rg);
        id_txt = findViewById(R.id.session_id_txt);
       // RadioButton threeRB = findViewById(R.id.three_player_rb);
        spinner = findViewById(R.id.spinner);


        next = findViewById(R.id.next_btn);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String mail = user.getEmail();
        String id = mail.substring(0, mail.indexOf('@'));
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        Log.e("Time Stamp", timeStamp);

        gdID = id+"_"+timeStamp;
        id_txt.setText(gdID);
      //  threeRB.setSelected(true);

        topicRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.basic_rb: type="BASIC";break;
                    case R.id.adv_rb: type = "ADVANCE"; break;
                }
            }
        });

        durRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.seven_rb: dur=7; break;
                    case R.id.ten_rb: dur=10; break;
                    case R.id.fifteen_rb: dur=15; break;
                }
            }
        });

        // Radio Buttons
        /*

        playerRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.three_player_rb: no_of_players=3; break;
                    case R.id.four_player_rb: no_of_players=4; break;
                    case R.id.five_player_rb: no_of_players = 5; break;
                    case R.id.six_player_rb: no_of_players = 6; break;
                }
            }
        });

*/

        ArrayAdapter<CharSequence> adapter  = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1,NoOfPlayers);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                no_of_players = Integer.parseInt(NoOfPlayers[i].split(" ")[0]);
                Log.e("No of player", no_of_players+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topic = topic_edt.getText().toString();
                if(topic.isEmpty()){
                    topic_edt.setError("Please provide any topic.");
                    return;
                }
                if(type==null){
                    Snackbar.make(next, "Please provide the GD Type.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(dur==0){
                    Snackbar.make(next, "Please provide the time limit.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent(InitiateGDActivity.this, MainActivity.class);
                i.putExtra("gdID", gdID);
                i.putExtra("topic", topic);
                i.putExtra("type", type);
                i.putExtra("nPlayers", no_of_players);
                i.putExtra("dur", dur);
                startActivity(i);


            }
        });

    }
}
