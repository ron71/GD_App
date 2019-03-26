package in.ac.kiit.justtalk.models;

public class Scores {

    String id;
    int fluency;
    int content;
    int teamWork;
    int bodyLanguage;
    int language;

    public Scores(String id,int fluency, int content, int teamWork, int bodyLanguage, int language) {
        this.fluency = fluency;
        this.content = content;
        this.teamWork = teamWork;
        this.bodyLanguage = bodyLanguage;
        this.language = language;
        this.id = id;
    }

    public Scores() {
    }
    public Scores(String id){
        this.id=id;

    }

    public int getFluency() {
        return fluency;
    }

    public int getContent() {
        return content;
    }

    public int getTeamWork() {
        return teamWork;
    }

    public int getBodyLanguage() {
        return bodyLanguage;
    }

    public int getLanguage() {
        return language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFluency(int fluency) {
        this.fluency = fluency;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public void setTeamWork(int teamWork) {
        this.teamWork = teamWork;
    }

    public void setBodyLanguage(int bodyLanguage) {
        this.bodyLanguage = bodyLanguage;
    }

    public void setLanguage(int language) {
        this.language = language;
    }
}
