package in.ac.kiit.justtalk.models;

import java.util.HashMap;
import java.util.Map;

public class GDEvent {
    String gdID;
    String organiserID;
    String conductedBy;
    String date;
    String time;
    String type;
    String topic;
    Map<String, Scores> playerIDs = new HashMap<>();

    public  GDEvent(){

    }
    
    public GDEvent(String gdID, String organiserID, String conductedBy, String date, String time, String type, String topic, Map<String, Scores> playerIDs) {
        this.gdID = gdID;
        this.organiserID = organiserID;
        this.conductedBy = conductedBy;
        this.date = date;
        this.time = time;
        this.type = type;
        this.topic = topic;
        this.playerIDs = playerIDs;
    }
}
