package application.viope.math.combinedapp.bean;

public class ln7_Exercise {
    private int id;
    private String question;
    private String correct;
    private String type;

    public ln7_Exercise() {
        super();
    }

    public ln7_Exercise(String question, String correct, String type) {
        this.question = question;
        this.correct = correct;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ln7_Exercise{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", correct='" + correct + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
