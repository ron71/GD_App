package in.ac.kiit.justtalk.adapters;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import in.ac.kiit.justtalk.R;
import in.ac.kiit.justtalk.ScoreBookActivity;
import in.ac.kiit.justtalk.models.GDEvent;
import in.ac.kiit.justtalk.models.Scores;
import in.ac.kiit.justtalk.playerActivity;

import static android.app.Activity.RESULT_OK;

public class LivePlayerListAdapter extends RecyclerView.Adapter<LivePlayerListAdapter.LivePlayerListHolder> {

    GDEvent gdEvent;
    Context context;
    View view;

    public LivePlayerListAdapter(GDEvent gdEvent, Context context) {
        this.gdEvent = gdEvent;
        this.context = context;
    }

    @NonNull
    @Override
    public LivePlayerListAdapter.LivePlayerListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(context==null){
            Log.e("context","NULL");
        }

        view = LayoutInflater.from(context)
                .inflate(R.layout.player_score_card, parent, false);

        LivePlayerListHolder livePlayerListHolder = new LivePlayerListHolder(view);
        return livePlayerListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LivePlayerListAdapter.LivePlayerListHolder holder, final int position) {
        TextView player = holder.player;
        TextView fluency_txt = holder.fluency_txt;
        TextView content_txt = holder.content_txt;
        TextView body_txt = holder.body_txt;
        TextView lang_txt = holder.lang_txt;
        TextView team_txt = holder.team_txt;
        CardView cd = holder.cd;

        Scores playerScore = gdEvent.getPlayerIDs().get(position);
        player.setText("P"+(position+1));
        fluency_txt.setText(""+playerScore.getFluency());
        content_txt.setText(""+playerScore.getContent());
        body_txt.setText(""+playerScore.getBodyLanguage());
        lang_txt.setText(""+playerScore.getLanguage());
        team_txt.setText(""+playerScore.getTeamWork());

        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ScoreBookActivity.class);
                i.putExtra("scores", gdEvent.getPlayerIDs());
                i.putExtra("playerno", position);
                ((Activity)context).startActivityForResult(i,1001);
            }
        });



    }

    @Override
    public int getItemCount() {
        return gdEvent.getPlayerIDs().size();
    }

    public class LivePlayerListHolder extends RecyclerView.ViewHolder {

        TextView player, fluency_txt, content_txt, body_txt, lang_txt,team_txt;
        CardView cd;
        public LivePlayerListHolder(View itemView) {
            super(itemView);

            this.body_txt = itemView.findViewById(R.id.bodylang);
            this.content_txt = itemView.findViewById(R.id.content);
            this.fluency_txt = itemView.findViewById(R.id.fluency);
            this.player = itemView.findViewById(R.id.player_roll);
            this.team_txt = itemView.findViewById(R.id.teamwork);
            this.lang_txt = itemView.findViewById(R.id.language);
            this.cd = itemView.findViewById(R.id.player_score_card);


        }
    }


 public void update(GDEvent event, int position){
        this.gdEvent = event;
        Log.e("Check", event.getPlayerIDs().get(position).getBodyLanguage()+"");
        Log.e("",event.getPlayerIDs().get(0).getFluency()+"");
        notifyItemChanged(position);
    }
}

