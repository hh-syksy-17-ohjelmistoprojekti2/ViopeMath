package application.viope.math.combinedapp;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import application.viope.math.combinedapp.bean.ConsAnswer;
import application.viope.math.combinedapp.bean.ConsQuestion;

public class ConsExercisePage extends Fragment {

    View myView;
    EditText edit;
    EditText formulaEdittext;
    TextView question1TextView;
    TextView exerciseCountertextView;
    TextView currentExercise;
    Button answerButton;
    ImageButton nextBtn;
    ImageButton prevBtn;
    ImageView answerImage;
    private EquDatabaseHelper mydb;
    double rightAnswer;
    int counter = 0;
    int questionNumber=0;
    List<ConsQuestion> questionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.cons_exercise_page, container, false);
            answerLogic();
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    //-----------------------  METHODS  ---------------------------
    public void answerInDb(){
        answerImage.setVisibility(View.VISIBLE);
        answerImage.setImageResource(R.drawable.bb_checked);
        edit.setText(Double.toString(rightAnswer));
        edit.setFocusable(false);
        formulaEdittext.setFocusable(false);
    }

    public void ifRightAnswer(){
        double userAnswer = Double.parseDouble(edit.getText().toString());
        answerImage = myView.findViewById(R.id.answerImageView);
        ConsAnswer answer = new ConsAnswer();
        answer.setAnswer(userAnswer);
        answer.setFormula(formulaEdittext.getText().toString());
        answerImage.setVisibility(View.VISIBLE);
        answerImage.setImageResource(R.drawable.bb_checked);
        mydb.ConsInsertAnswers(answer.getAnswer(), answer.getFormula(), (questionNumber+1));
        System.out.println(mydb.ConsGetAllElements());
        mydb.close();

    }
    public void ifWrongAnswer() {
        answerImage.setVisibility(View.VISIBLE);
        answerImage.setImageResource(R.drawable.bb_error);
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                answerImage.setVisibility(View.GONE);
            }
        }.start();
        counter++;
    }
    public void getQuestion(int newQuestion){
        question1TextView.setText(questionList.get(newQuestion).getQuestion().toString());
        rightAnswer = questionList.get(newQuestion).getRightAnswer();
        answerImage.setVisibility(View.INVISIBLE);
        edit.setText("");
        edit.setFocusable(true);
        formulaEdittext.setText("");
        formulaEdittext.setFocusable(true);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(ConsExercisePage.this).attach(ConsExercisePage.this).commit();
        counter=0;
        if (mydb.ifExists(rightAnswer)) {
            answerInDb();
            answerButton.setEnabled(false);
        }
    }


    public void answerLogic() {
        mydb= new EquDatabaseHelper(getContext());
        questionList= mydb.ConsGetAllQuestions();
        rightAnswer= questionList.get(questionNumber).getRightAnswer();
        nextBtn = myView.findViewById(R.id.nextBtn);
        prevBtn= myView.findViewById(R.id.prevBtn);
        answerButton= myView.findViewById(R.id.answerBtn);
        edit= myView.findViewById(R.id.answerView);
        answerImage= myView.findViewById(R.id.answerImageView);
        formulaEdittext= myView.findViewById(R.id.formulaView);
        question1TextView= myView.findViewById(R.id.question1TextView);
        exerciseCountertextView= myView.findViewById(R.id.exerciseCounterTextView);
        currentExercise= myView.findViewById(R.id.taskName);
        String exerciseNumber= String.valueOf(questionNumber + 1);
        String allExercises = String.valueOf(questionList.size());
        exerciseCountertextView.setText(exerciseNumber + "/" + allExercises);
        currentExercise.setText("Exercício " + exerciseNumber);
        question1TextView.setText(questionList.get(questionNumber).getQuestion().toString());
        final ImageButton hintBtn = myView.findViewById(R.id.hintBtn);
        hintBtn.setVisibility(View.INVISIBLE);
        if (mydb.ifExists(rightAnswer)) {
            answerInDb();
            answerButton.setEnabled(false);
        } else {
            answerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (edit.getText().toString().equals("") || formulaEdittext.getText().toString().equals("")) {
                        Toast.makeText(getContext(), getString(R.string.please_insert), Toast.LENGTH_LONG).show();
                    } else {
                        double userAnswer = Double.parseDouble(edit.getText().toString());
                        answerImage = myView.findViewById(R.id.answerImageView);

                        if (userAnswer == rightAnswer) {
                            ifRightAnswer();
                        } else {
                            ifWrongAnswer();
                            if (counter == 3) {
                                hintBtn.setVisibility(View.VISIBLE);
                                hintBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getContext(), questionList.get(questionNumber).getHint().toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }

        if (questionNumber == questionList.size() -1) {
            nextBtn.setVisibility(View.INVISIBLE);
        }else {
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questionNumber++;
                    getQuestion(questionNumber);
                }
            });
        }
        if (questionNumber == 0) {
            prevBtn.setVisibility(View.INVISIBLE);
        } else {
            prevBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questionNumber--;
                   getQuestion(questionNumber);
                }
            });
        }
    }
}