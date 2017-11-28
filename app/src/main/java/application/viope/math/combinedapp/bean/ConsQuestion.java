package application.viope.math.combinedapp.bean;

/**
 * Created by tomisalin on 11/7/17.
 */

public class ConsQuestion {

    private int id;
    private double rightAnswer;
    private String question;
    private String hint;

    public ConsQuestion() {
    }

    public ConsQuestion(int id, double rightAnswer, String question, String hint) {
        this.id = id;
        this.rightAnswer = rightAnswer;
        this.question = question;
        this.hint = hint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(double rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String toString() {
        return "ConsQuestion{" +
                "id=" + id +
                ", rightAnswer=" + rightAnswer +
                ", question='" + question + '\'' +
                ", hint='" + hint + '\'' +
                '}';
    }
}
