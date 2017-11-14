package application.viope.math.app.bean;

/**
 * Created by a1600519 on 30.10.2017.
 */

public class EquAnswerstatus {

    private String questionId;
    private String userId;
    private int answerStatus;
    private int questionOrder;

    public EquAnswerstatus(String questionId, String userId, int answerStatus, int questionOrder) {
        this.questionId = questionId;
        this.userId = userId;
        this.answerStatus = answerStatus;
        this.questionOrder = questionOrder;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
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
                ", userId='" + userId + '\'' +
                ", answerStatus=" + answerStatus +
                ", questionOrder=" + questionOrder +
                '}';
    }
}
