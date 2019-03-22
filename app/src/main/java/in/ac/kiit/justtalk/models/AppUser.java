package in.ac.kiit.justtalk.models;

import java.util.ArrayList;

public class AppUser {

    String userID;
    String emailID;
    ArrayList<String> members = new ArrayList<>();
    String name;
    String year;
    String type;
    String branch;

    public AppUser() {
    }

    public AppUser(String roll, String emailID, ArrayList<String> members, String name, String year, String type, String branch) {
        this.userID = roll;
        this.emailID = emailID;
        this.members = members;
        this.name = name;
        this.year = year;
        this.type = type;
        this.branch = branch;
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
}
