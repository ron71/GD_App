package in.ac.kiit.justtalk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import in.ac.kiit.justtalk.models.AppUser;
import in.ac.kiit.justtalk.models.GDEvent;
import in.ac.kiit.justtalk.models.Scores;

public class Report extends AppCompatActivity {
    TextView date, time, conduct, topic, idTxt, durTxt;
    TextView f_marks, f_comment;
    TextView b_marks, b_comment;
    TextView l_marks, l_comment;
    TextView t_marks, t_comment;
    TextView c_marks, c_comment;

    TextView totalTxt;

    GDEvent event;

    String findDate(String time){
        String date = time.substring(time.length()-2, time.length());
        String mon = time.substring(4,6);
        String yr = time.substring(0,4);
        return date+"-"+mon+"-"+yr;
    }

    String findTime(String time){
        int hr = Integer.parseInt(time.substring(0,2));
        int min = Integer.parseInt(time.substring(2,4));
        int sec = Integer.parseInt(time.substring(4,6));
        String s = "AM";
        if(hr==0){
            hr=12;
        }
        if(hr>12){
            hr = hr-12;
            s = "PM";
        }
        if(min<10){
            if(sec<10){
                return hr+":0"+min+":0"+sec+" "+s;
            }else{
                return hr+":0"+min+":"+sec+" "+s;
            }
        }else if(sec<10){
            return hr+":"+min+":0"+sec+" "+s;
        }
        return hr+":"+min+":"+sec+" "+s;
    }

    Scores getScore(GDEvent event){
        String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        for(int i=0; i< event.getPlayerIDs().size(); i++){
            if(event.getPlayerIDs().get(i).getId().equals(mail.substring(0, mail.indexOf("@")))){
               return event.getPlayerIDs().get(i);
            }
        }
        return null;
    }

    String getFluencyComment(int f){

        String fluency="";
        if(f==5){
            fluency="Rajdhani Express, you speak without any MTI.";
        }
        else if(f==4){
            fluency="You speak breezily but halt at times, and minimum MTI.";
        }
        else if(f==3){
            fluency="You speak fluently, but with MTI and pauses.";
        }
        else if(f==2){
            fluency="Give many pauses, you love to retain accent.";
        }
        else if(f==1){
            fluency="Hunting for words, you speak in local flavour.";
        }
        else{
            fluency = "You need to work hard on your fluency.";
        }
        return fluency;
    }

    String getContentComment(int c){
        String content="";
        if(c==5){
            content="You speak on both sides, giving many facts.";
        }
        else if(c==4){
            content="You speak for and against, giving some facts and figures and correcting people.";
        }
        else if(c==3){
            content="You speak well on one topic giving few facts and figures.";
        }
        else if(c==2){
            content="You speak on borrowed points, no facts.";
        }
        else if(c==1){
            content="You don't contribute at all, just nod.";
        }
        else {
            content="You spoke somewhere out of context.";
        }
        return content;
    }

    String getTeamComment(int t){
        String teamwork="";
        if(t==5){
            teamwork="You suppress chaos, motivate quiet and track time. \n"+
                    "You listen sincerely, appreciate and support points. \n" +
                    "You initiate the talk, keep it on track and sum up. ";
        }
        else if(t==4){
            teamwork="You defuse deadlocks, facilitate talk-turns and seek clarifications.\n"+
                    "You support and oppose points equally using diplomatic language. \n"+
                    "You start well and summarize all points on both sides.";
        }
        else if(t==3){
            teamwork="You speak in turn, gives chance to silent and follow the 'leader'. \n"+
                    "You don't interrupt, listen well and agree to the majority. \n"+
                    "You speak at the start and keep the discussion on track.";
        }
        else if(t==2){
            teamwork="You use more time than allotted and manage the group.\n"+
                    "You cut into other's talk, disagree and you are blunt.\n"+
                    "You speak in first half of the discussion.";
        }
        else if(t==1){
            teamwork="You dominate the group and prioritize the topic over people.\n"+
                    "You interrupt often, question more and get too aggressive.\n"+
                    "You contribute towards the end of the discussion.";
        }
        else {
            teamwork="Please show some co-operation and teamwork.";
        }
        return teamwork;
    }

    String getBodyLang(int b){
        String bodylang="";
        if(b==5){
            bodylang="You move your hands gracefully.";
        }
        else if(b==4){
            bodylang="Smiling face reflects the feelings of heart.";
        }
        else if(b==3){
            bodylang="You sit straight and lean forward in the chair.";
        }
        else if(b==2){
            bodylang="You make eye contact with ALL.";
        }
        else if(b==1){
            bodylang="Eyes up, hands tied, lost in interstellar.";
        }
        else{
            bodylang = "Please pay attention on your hand gesture and body posture while speaking.";
        }
        return bodylang;
    }

    String getLang(int l){
        String language="";
        if(l==5){
            language="Shakespeare and Shashi Tharoor are proud of you.";
        }
        else if(l==4){
            language="Shakespeare still loves you, but others found faults.";
        }
        else if(l==3){
            language="Language is still English, but Facts > Parts of Speech.";
        }
        else if(l==2){
            language="Everyone understands, but struggle to.";
        }
        else if(l==1){
            language="Grammar has retired. Vocab surrendered.";
        }else{
            language = "You need to work hard on grammar and vocabulary.";
        }
        return language;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        date = findViewById(R.id.event_date);
        time = findViewById(R.id.event_time);
        conduct = findViewById(R.id.session_cond);
        topic = findViewById(R.id.session_topic);
        idTxt = findViewById(R.id.session_id);
        durTxt = findViewById(R.id.session_dur);

        f_marks = findViewById(R.id.session_fluency_marks);
        l_marks = findViewById(R.id.session_lang_marks);
        c_marks = findViewById(R.id.session_content_marks);
        b_marks = findViewById(R.id.session_body_marks);
        t_marks = findViewById(R.id.session_team_marks);

        f_comment = findViewById(R.id.fluency_comment);
        c_comment = findViewById(R.id.content_comment);
        b_comment = findViewById(R.id.body_comment);
        l_comment = findViewById(R.id.lang_comment);
        t_comment = findViewById(R.id.team_comment);

        totalTxt = findViewById(R.id.session_total);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        event = (GDEvent) b.get("event");

        date.setText(findDate(event.getTimeStamp().split("_")[0]));
        time.setText(findTime(event.getTimeStamp().split("_")[1]));

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(event.getOrganiserID());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document!=null){
                        AppUser u = task.getResult().toObject(AppUser.class);
                        conduct.setText(u.getName());
                    }
                }
            }
        });

        topic.setText(event.getTopic());
        durTxt.setText(event.getDuration()+ " MIN");
        idTxt.setText(event.getGdID());

        Scores score = getScore(event);

        f_marks.setText(score.getFluency()+"/5");
        b_marks.setText(score.getBodyLanguage()+"/5");
        l_marks.setText(score.getLanguage()+"/5");
        c_marks.setText(score.getContent()+"/5");
        t_marks.setText(score.getTeamWork()+"/5");

        f_comment.setText(getFluencyComment(score.getFluency()));
        c_comment.setText(getContentComment(score.getContent()));
        t_comment.setText(getTeamComment(score.getTeamWork()));
        b_comment.setText(getBodyLang(score.getBodyLanguage()));
        l_comment.setText(getLang(score.getLanguage()));

        totalTxt.setText((score.getTeamWork()+score.getContent()+score.getLanguage()+score.getFluency()+score.getBodyLanguage())+"/25");







    }
}
