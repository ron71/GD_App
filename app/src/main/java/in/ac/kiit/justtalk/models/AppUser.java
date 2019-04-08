package in.ac.kiit.justtalk.models;

import android.net.Uri;

import java.util.ArrayList;

public class AppUser {

    String userID;
    String emailID;
    ArrayList<String> members = new ArrayList<>();
    String name;
    String year;
    String type;
    String branch;
    String url;
    ArrayList<String> vents = new ArrayList<String>();
    ArrayList<String> ventsConducted = new ArrayList<>();


    public AppUser(String roll, String emailID, ArrayList<String> members, String name, String year, String type, String branch, String url) {
        this.userID = roll;
        this.emailID = emailID;
        this.members = members;
        this.name = name;
        this.year = year;
        this.type = type;
        this.branch = branch;
        this.url=url;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmailID() {
        return emailID;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getBranch() {
        return branch;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<String> getVents() {
        return vents;
    }

    public void setVents(ArrayList<String> vents) {
        this.vents = vents;
    }


    public ArrayList<String> getVentsConducted() {
        return ventsConducted;
    }

    public void setVentsConducted(ArrayList<String> ventsConducted) {
        this.ventsConducted = ventsConducted;
    }

    public AppUser() {
    }

    public void setType(String type) {
        this.type = type;
    }
}
