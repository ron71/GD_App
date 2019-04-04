package in.ac.kiit.justtalk.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import in.ac.kiit.justtalk.R;
import in.ac.kiit.justtalk.Report;
import in.ac.kiit.justtalk.models.GDEvent;
import in.ac.kiit.justtalk.models.Scores;

public class GDSessionAdapter extends RecyclerView.Adapter<GDSessionAdapter.GDSessionHolder>  {

    ArrayList<GDEvent> events;
    Context context;
    View view;

    public GDSessionAdapter(ArrayList<GDEvent> events, Context context) {

        this.events = events;
        this.context = context;

    }

    String getMarks(String id, int position){

        for(int i=0; i<events.get(position).getPlayerIDs().size();i++){
            Scores scores = events.get(position).getPlayerIDs().get(i);
            if(scores.getId().equals(id)){
                return ""+(scores.getFluency()+scores.getBodyLanguage()+scores.getLanguage()+scores.getContent()+scores.getTeamWork());
            }
        }

        return null;
    }

    @NonNull
    @Override
    public GDSessionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view =LayoutInflater.from(context)
                .inflate(R.layout.gd_event_card, parent, false);
        GDSessionHolder holder = new GDSessionHolder(view);
        return holder;
    }

    private void getDate(String timestamp){

    }

    @Override
    public void onBindViewHolder(@NonNull GDSessionHolder holder, int position) {
        TextView sno = holder.sno;
        TextView marks=holder.marks;
        TextView date=holder.date;
        TextView time = holder.time;
        CardView card = holder.card;

        sno.setText("SESSION NO. : "+(getItemCount()-position));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getEmail().substring(0,user.getEmail().indexOf("@"));
        marks.setText(getMarks(id, position)+"/25");


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Report.class));
            }
        });




    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class GDSessionHolder extends RecyclerView.ViewHolder {
        TextView sno, marks, date, time;
        CardView card;

        public GDSessionHolder(View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.session_no);
            marks = itemView.findViewById(R.id.session_score);
            date = itemView.findViewById(R.id.event_date);
            time = itemView.findViewById(R.id.event_time);
            card = itemView.findViewById(R.id.event_card);
        }
    }
}
