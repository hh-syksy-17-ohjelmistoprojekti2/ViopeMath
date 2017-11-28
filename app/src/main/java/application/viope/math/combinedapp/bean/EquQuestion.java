package application.viope.math.combinedapp.bean;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a1600519 on 30.10.2017.
 */

public class EquQuestion {

    private static final String TAG = "QuestionBean";


    private String questionId;
    private String questionText;
    private String answer;
    private int questionOrder;
    private List<EquPhase> equPhaseList;

    public EquQuestion() {
        super();
        this.questionId = null;
        this.questionText = null;
        this.answer = null;
        this.questionOrder = 0;
        this.equPhaseList = new ArrayList<EquPhase>();
    }

    public EquQuestion(String questionId, String questionText, String answer, int questionOrder) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.answer = answer;
        this.questionOrder = questionOrder;
        this.equPhaseList = new ArrayList<EquPhase>();
    }

    public EquQuestion(String questionId, String questionText, String answer, int questionOrder, List<EquPhase> equPhaseList) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.answer = answer;
        this.questionOrder = questionOrder;
        if (equPhaseList == null) {
            this.equPhaseList = new ArrayList<EquPhase>();
        }else{
            this.equPhaseList = equPhaseList;
        }
    }

    public void addPhase(EquPhase equPhase) {
        if (equPhase != null) {
            this.equPhaseList.add(equPhase);
        }
    }

    public EquPhase getPhase(int index) {
        if (index >= 0 && index < this.equPhaseList.size()){
            return this.equPhaseList.get(index);
        }else{
            Log.d(TAG,"index not valid!");
        }
        return null;
    }

    public void removeLastPhase(){
        equPhaseList.remove(equPhaseList.size() -1);
    }

    public void removeAllPhases() {
        equPhaseList.clear();
    }

    public List<EquPhase> getEquPhaseList() {
        return equPhaseList;
    }

    public void setEquPhaseList(List<EquPhase> equPhaseList) {
        this.equPhaseList = equPhaseList;
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
        return "EquQuestion{" +
                "questionId='" + questionId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", answer=" + answer +
                ", questionOrder=" + questionOrder +
                '}';
    }
}
