package in.ac.kiit.justtalk.models;

public class Scores {

    int fluency;
    int content;
    int teamWork;
    int bodyLanguage;
    int language;

    public Scores(int fluency, int content, int teamWork, int bodyLanguage, int language) {
        this.fluency = fluency;
        this.content = content;
        this.teamWork = teamWork;
        this.bodyLanguage = bodyLanguage;
        this.language = language;
    }

    public Scores() {
    }
}
