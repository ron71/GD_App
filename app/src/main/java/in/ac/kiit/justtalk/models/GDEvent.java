package in.ac.kiit.justtalk.models;

import java.util.HashMap;
import java.util.Map;

public class GDEvent {
    String gdID;
    String organiserID;
    String timeStamp;
    String type;
    String topic;
    Map<String, Scores> playerIDs = new HashMap<>();

    public  GDEvent(){

    }
    
    public GDEvent(String gdID, String organiserID, String timeStamp,String type, String topic, Map<String, Scores> playerIDs) {
        this.gdID = gdID;
        this.organiserID = organiserID;
        this.timeStamp= timeStamp;
        this.type = type;
        this.topic = topic;
        this.playerIDs = playerIDs;
    }
}
