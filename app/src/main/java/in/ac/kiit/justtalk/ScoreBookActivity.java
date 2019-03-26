package in.ac.kiit.justtalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import in.ac.kiit.justtalk.models.Scores;

public class ScoreBookActivity extends AppCompatActivity {

    TextView[] fluency = new TextView[6];
    TextView[] body = new TextView[6];
    TextView[] content = new TextView[6];
    TextView[] team = new TextView[6];
    TextView[] language = new TextView[6];

    TextView profileNameTxt, profileRollNoTxt, branchTxt, yearTxt;

    int fluencyID[] = {R.id.fluency_zero,R.id.fluency_one,R.id.fluency_two,R.id.fluency_three,R.id.fluency_four,R.id.fluency_five};
    int bodyID[] = {R.id.body_zero, R.id.body_one, R.id.body_two, R.id.body_three, R.id.body_four, R.id.body_five};
    int contentID[] = {R.id.content_zero,R.id.content_one,R.id.content_two,R.id.content_three,R.id.content_four,R.id.content_five };
    int teamID[] = {R.id.team_zero,R.id.team_one,R.id.team_two,R.id.team_three,R.id.team_four,R.id.team_five};
    int langID[] = {R.id.lang_zero,R.id.lang_one,R.id.lang_two,R.id.lang_three,R.id.lang_four,R.id.lang_five,};

    ArrayList<Scores> scores;
    int position;

    void initialiseUI(){

        profileNameTxt = findViewById(R.id.playerName);
        profileRollNoTxt = findViewById(R.id.playerRoll);
        branchTxt = findViewById(R.id.playerBranch);
        yearTxt = findViewById(R.id.playerYr);

        for(int i=0; i<6; i++){
            fluency[i] = findViewById(fluencyID[i]);
            body[i] = findViewById(bodyID[i]);
            content[i] = findViewById(contentID[i]);
            team[i] = findViewById(teamID[i]);
            language[i] = findViewById(langID[i]);
        }

    }

    private void renderPlayerDetails(String id){


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_book);

        Bundle b = getIntent().getExtras();
       // scores = (ArrayList<Scores>) b.get("scores");
        position = b.getInt("playerno");

        initialiseUI();

      //  String id = scores.get(position).getId();

      //  renderPlayerDetails(id);






    }
}
