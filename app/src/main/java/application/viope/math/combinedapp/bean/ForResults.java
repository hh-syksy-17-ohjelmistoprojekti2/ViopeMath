package application.viope.math.combinedapp.bean;

public class ForResults {
    public ForResults(String r_question_id, String results) {
        this.r_question_id = r_question_id;
        this.results = results;
    }

    public String r_question_id;

    public String getR_question_id() {
        return r_question_id;
    }

    public void setR_question_id(String r_question_id) {
        this.r_question_id = r_question_id;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String results;


}