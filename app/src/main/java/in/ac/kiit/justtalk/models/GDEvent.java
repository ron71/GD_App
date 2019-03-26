package in.ac.kiit.justtalk.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GDEvent {
    String gdID;
    String organiserID;
    String timeStamp;
    String type;
    String topic;
    int duration;
   ArrayList<Scores> playerIDs = new ArrayList<>();

    public  GDEvent(){

    }
    
    public GDEvent(String gdID, String organiserID, String timeStamp,String type, String topic, ArrayList<Scores> playerIDs, int duration) {
        this.gdID = gdID;
        this.organiserID = organiserID;
        this.timeStamp= timeStamp;
        this.type = type;
        this.topic = topic;
        this.playerIDs = playerIDs;
        this.duration = duration;
    }

    public String getGdID() {
        return gdID;
    }

    public String getOrganiserID() {
        return organiserID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getType() {
        return type;
    }

    public String getTopic() {
        return topic;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Scores> getPlayerIDs() {
        return playerIDs;
    }
}
