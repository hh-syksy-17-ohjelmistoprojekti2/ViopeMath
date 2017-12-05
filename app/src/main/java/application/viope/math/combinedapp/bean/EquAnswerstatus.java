package application.viope.math.combinedapp.bean;

public class EquAnswerstatus {

    private String questionId;
    private int answerStatus;


    public EquAnswerstatus(String questionId, int answerStatus) {
        this.questionId = questionId;
        this.answerStatus = answerStatus;

    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }


    public int getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(int answerStatus) {
        this.answerStatus = answerStatus;
    }

    @Override
    public String toString() {
        return "EquAnswerstatus{" +
                "questionId='" + questionId + '\'' +
                ", answerStatus=" + answerStatus +
                '}';
    }
}