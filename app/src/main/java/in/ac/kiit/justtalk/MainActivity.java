package in.ac.kiit.justtalk;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    String timeStamp;
    String type = "basic";
    String topic = "Lovable Kiitians";
    String duration = "420000";

    EditText p1,p2,p3,p4,p5,p6;
    Button g2go;

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

        g2go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String mail = user.getEmail();
                String id = mail.substring(0, mail.indexOf('@'));
                timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

                gdID = id+"_"+timeStamp;
                organiserID = id;
                int i = 0;
                ArrayList<String> roll = new ArrayList<>();
                roll.add(i,p1.getText().toString()); i++;
                roll.add(i,p2.getText().toString()); i++;
                roll.add(i,p3.getText().toString()); i++;
                roll.add(i,p4.getText().toString()); i++;
                roll.add(i,p5.getText().toString()); i++;
                roll.add(i,p6.getText().toString());

                for(i=0;i<6; i++){
                    if(roll.get(i).length()!=7){
                        roll.remove(i);
                        Snackbar.make(view,"Please provide valid roll number.", Snackbar.LENGTH_LONG).show();
                        return;


                    }
                }
                if(roll.size()<3){
                    Snackbar.make(view,"Minimum 3 players are mandatory for the GD.", Snackbar.LENGTH_LONG).show();
                    return;
                }

                //GDEvent gd = new GDEvent(gdID, id, timeStamp,type, topic, players , "7000");
                Intent intent =new Intent(MainActivity.this, ProfileActivity.class);
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
        });
    }
}
