package application.viope.math.app.bean;

/**
 * Created by a1600519 on 30.10.2017.
 */

public class EquAnswerstatus {

    private String questionId;
    //private String userId;
    private int answerStatus;


    public EquAnswerstatus(String questionId, int answerStatus) {
        this.questionId = questionId;
        //this.userId = userId;
        this.answerStatus = answerStatus;

    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    /*public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }*/

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