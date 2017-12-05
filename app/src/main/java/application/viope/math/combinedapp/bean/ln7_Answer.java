package application.viope.math.combinedapp.bean;


public class ln7_Answer {
    private int exercise_id;
    private String answer;
    private boolean isCorrect;

    public ln7_Answer() {
        super();
    }

    public ln7_Answer(int exercise_id, String answer, boolean isCorrect) {
        this.exercise_id = exercise_id;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "ln7_Answer{" +
                "exercise_id=" + exercise_id +
                ", answer='" + answer + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
