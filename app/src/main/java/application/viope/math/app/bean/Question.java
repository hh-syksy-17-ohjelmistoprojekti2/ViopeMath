package application.viope.math.app.bean;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a1600519 on 30.10.2017.
 */

public class Question {

    private static final String TAG = "QuestionBean";


    private String questionId;
    private String questionText;
    private String answer;
    private int questionOrder;
    private List<Phase> phaseList;

    public Question() {
        super();
        this.questionId = null;
        this.questionText = null;
        this.answer = null;
        this.questionOrder = 0;
        this.phaseList = new ArrayList<Phase>();
    }

    public Question(String questionId, String questionText, String answer, int questionOrder) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.answer = answer;
        this.questionOrder = questionOrder;
        this.phaseList = new ArrayList<Phase>();
    }

    public Question(String questionId, String questionText, String answer, int questionOrder, List<Phase> phaseList) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.answer = answer;
        this.questionOrder = questionOrder;
        if (phaseList == null) {
            this.phaseList = new ArrayList<Phase>();
        }else{
            this.phaseList = phaseList;
        }
    }

    public void addPhase(Phase phase) {
        if (phase != null) {
            this.phaseList.add(phase);
        }
    }

    public Phase getPhase(int index) {
        if (index >= 0 && index < this.phaseList.size()){
            return this.phaseList.get(index);
        }else{
            Log.d(TAG,"index not valid!");
        }
        return null;
    }

    public void removeLastPhase(){
        phaseList.remove(phaseList.size() -1);
    }

    public void removeAllPhases() {
        phaseList.clear();
    }

    public List<Phase> getPhaseList() {
        return phaseList;
    }

    public void setPhaseList(List<Phase> phaseList) {
        this.phaseList = phaseList;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId='" + questionId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", answer=" + answer +
                ", questionOrder=" + questionOrder +
                '}';
    }
}
