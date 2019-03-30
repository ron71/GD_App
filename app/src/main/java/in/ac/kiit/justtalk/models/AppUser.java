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

    public AppUser() {
    }

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
}
