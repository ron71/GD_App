package in.ac.kiit.justtalk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ac.kiit.justtalk.models.AppUser;
import in.ac.kiit.justtalk.models.Scores;

public class ScoreBookActivity extends AppCompatActivity implements View.OnClickListener {

    TextView[] fluency = new TextView[5];
    TextView[] body = new TextView[5];
    TextView[] content = new TextView[5];
    TextView[] team = new TextView[5];
    TextView[] language = new TextView[5];

    TextView profileNameTxt, profileRollNoTxt, branchTxt, yearTxt;
    ImageView playerAvatar;

    int fluencyID[] = {R.id.fluency_one,R.id.fluency_two,R.id.fluency_three,R.id.fluency_four,R.id.fluency_five};
    int bodyID[] = { R.id.body_one, R.id.body_two, R.id.body_three, R.id.body_four, R.id.body_five};
    int contentID[] = {R.id.content_one,R.id.content_two,R.id.content_three,R.id.content_four,R.id.content_five };
    int teamID[] = {R.id.team_one,R.id.team_two,R.id.team_three,R.id.team_four,R.id.team_five};
    int langID[] = {R.id.lang_one,R.id.lang_two,R.id.lang_three,R.id.lang_four,R.id.lang_five,};

    ArrayList<Scores> scores;
    int position;
    Intent i;
    AppUser appuser;

    void initialiseUI(){

        profileNameTxt = findViewById(R.id.playerName);
        profileRollNoTxt = findViewById(R.id.playerroll);
        branchTxt = findViewById(R.id.playerBranch);
        yearTxt = findViewById(R.id.playerYr);
        playerAvatar = findViewById(R.id.player_avatar);

        for(int i=0; i<5; i++){
            fluency[i] = findViewById(fluencyID[i]);
            fluency[i].setOnClickListener(this);
            body[i] = findViewById(bodyID[i]);
            body[i].setOnClickListener(this);
            content[i] = findViewById(contentID[i]);
            content[i].setOnClickListener(this);
            team[i] = findViewById(teamID[i]);
            team[i].setOnClickListener(this);
            language[i] = findViewById(langID[i]);
            language[i].setOnClickListener(this);
        }

    }

    void getUserFromFirebase(String id) {
        final String roll = id;
        Log.e("AppUser", id);
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Log.e("DOCUMENT", document.toString());
                        appuser = document.toObject(AppUser.class);
                        if(appuser!=null){
                            Log.e("Name", appuser.getName());
                            Log.e("AppUser", appuser.toString());
                            profileNameTxt.setText("NAME : " +appuser.getName());
                            profileRollNoTxt.setText("ROLL NO. : "+appuser.getUserID());
                            branchTxt.setText("BRANCH : "+appuser.getBranch());
                            yearTxt.setText("YEAR : "+appuser.getYear());
                            Picasso.with(ScoreBookActivity.this).load(appuser.getUrl()).into(playerAvatar);

                         }
                         else{
                            profileNameTxt.setText("NAME : Anonymous");
                            profileRollNoTxt.setText("ROLL NO. : "+roll);
                            branchTxt.setText("BRANCH : Unknown");
                            yearTxt.setText("YEAR : Unknown");
                         }
                    }
                }
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = new Intent(this, playerActivity.class);
        setContentView(R.layout.activity_score_book);

        Bundle b = getIntent().getExtras();
        scores = (ArrayList<Scores>)b.get("scores");
        position = b.getInt("playerno");

        initialiseUI();

        String id = scores.get(position).getId();

       getUserFromFirebase(id);

    }

    @Override
    public void onClick(View view) {

        setResult(1001,i);
        switch (view.getId()) {

            case R.id.fluency_one: {
                scores.get(position).setFluency(1);
                String code = position + "_" + "fluency" + "_" + 1;
                i.putExtra("code", code);
                finish();
                break;
            }
            case R.id.fluency_two: {
                scores.get(position).setFluency(2);
                String code = position + "_" + "fluency" + "_" + 2;
                i.putExtra("code", code);
                finish();
                break;
            }
            case R.id.fluency_three: {
                scores.get(position).setFluency(3);
                String code = position + "_" + "fluency" + "_" + 3;
                i.putExtra("code", code);
                finish();
                break;
            }
            case R.id.fluency_four: {
                scores.get(position).setFluency(4);
                String code = position + "_" + "fluency" + "_" + 4;
                i.putExtra("code", code);
                finish();
                break;
            }
            case R.id.fluency_five: {
                scores.get(position).setFluency(5);
                String code = position + "_" + "fluency" + "_" + 5;
                i.putExtra("code", code);
                finish();
                break;
            }
            case R.id.body_one:{
                scores.get(position).setBodyLanguage(1);
                String code = position + "_" + "body" + "_" + 1;
                i.putExtra("code", code);
                finish();
                break;
            }
            case R.id.body_two:{
                scores.get(position).setBodyLanguage(2);
                String code = position + "_" + "body" + "_" + 2;
                i.putExtra("code", code);
                finish();
                break;
            }
            case R.id.body_three:{
                scores.get(position).setBodyLanguage(3);
                String code = position + "_" + "body" + "_" + 3;
                i.putExtra("code", code);
                finish();
                break;
            }
            case R.id.body_four:{
                scores.get(position).setBodyLanguage(4);
                String code = position + "_" + "body" + "_" + 4;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.body_five:{
                scores.get(position).setBodyLanguage(5);
                String code = position + "_" + "body" + "_" + 5;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.content_one:{
                scores.get(position).setContent(1);
                String code = position + "_" + "content" + "_" + 1;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.content_two:{
                scores.get(position).setContent(2);
                String code = position + "_" + "content" + "_" + 2;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.content_three:{
                scores.get(position).setContent(3);
                String code = position + "_" + "content"+ "_" + 3;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.content_four:{
                scores.get(position).setContent(4);
                String code = position + "_" + "content" + "_" + 4;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.content_five:{
                scores.get(position).setContent(5);
                String code = position + "_" + "content" + "_" + 5;
                i.putExtra("code", code);
                finish();
                break;
            }
            case R.id.team_one:{
                scores.get(position).setTeamWork(1);
                String code = position + "_" + "team" + "_" + 1;
                i.putExtra("code", code);
                finish();
                break;
            }


            case R.id.team_two:{
                scores.get(position).setTeamWork(2);
                String code = position + "_" + "team" + "_" + 2;
                i.putExtra("code", code);
                finish();
                break;
            }


            case R.id.team_three:{
                scores.get(position).setTeamWork(3);
                String code = position + "_" + "team" + "_" + 3;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.team_four:{
                scores.get(position).setTeamWork(4);
                String code = position + "_" + "team" + "_" + 4;
                i.putExtra("code", code);
                finish();
                break;
            }
            case R.id.team_five:{
                scores.get(position).setTeamWork(5);
                String code = position + "_" + "team" + "_" + 5;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.lang_one:{
                scores.get(position).setLanguage(1);
                String code = position + "_" + "lang" + "_" + 1;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.lang_two:{
                scores.get(position).setLanguage(2);
                String code = position + "_" + "lang" + "_" + 2;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.lang_three:{
                scores.get(position).setLanguage(3);
                String code = position + "_" + "lang" + "_" + 3;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.lang_four:{
                scores.get(position).setLanguage(4);
                String code = position + "_" + "lang" + "_" + 4;
                i.putExtra("code", code);
                finish();
                break;
            }

            case R.id.lang_five:{
                scores.get(position).setLanguage(5);
                String code = position + "_" + "lang" + "_" + 5;
                i.putExtra("code", code);
                finish();
                break;
            }

        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Snackbar.make(profileNameTxt, "Please mark the player.", Snackbar.LENGTH_LONG).show();

    }
}
