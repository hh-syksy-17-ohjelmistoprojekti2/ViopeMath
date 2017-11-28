package application.viope.math.combinedapp.bean;

/**
 * Created by tomisalin on 10/23/17.
 */

public class ConsAnswer {

    private int id;
    private double answer;
    private String formula;
    private int questionnumber;

    public ConsAnswer() {
    }

    public ConsAnswer(int id, double answer) {
        this.id = id;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAnswer() {
        return answer;
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public int getQuestionnumber() {
        return questionnumber;
    }

    public void setQuestionnumber(int questionnumber) {
        this.questionnumber = questionnumber;
    }

    @Override
    public String toString() {
        return "Answers: " +
                " questionnumber: " + questionnumber +
                " answer: " + answer +
                " formula: " + formula;
    }
}
