package application.viope.math.app.bean;

/**
 * Created by a1600519 on 30.10.2017.
 */

public class Phase {

    private int phaseId;
    private String questionId;
    private int userId;
    private String phaseText;

    public Phase() {
        super();
        this.phaseId = 0;
        this.questionId = null;
        this.userId = 0;
        this.phaseText = null;
    }

    public Phase(String questionId, String phaseText) {
        this.questionId = questionId;
        this.phaseText = phaseText;
        this.phaseId = 0;
        this.userId = 0;
    }

    public Phase(int phaseId, String questionId, int userId, String phaseText) {
        this.phaseId = phaseId;
        this.questionId = questionId;
        this.userId = userId;
        this.phaseText = phaseText;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhaseText() {
        return phaseText;
    }

    public void setPhaseText(String phaseText) {
        this.phaseText = phaseText;
    }

    @Override
    public String toString() {
        return "Phase{" +
                "phaseId=" + phaseId +
                ", questionId=" + questionId +
                ", userId=" + userId +
                ", phaseText='" + phaseText + '\'' +
                '}';
    }
}
