package application.viope.math.combinedapp;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import application.viope.math.combinedapp.bean.ln7_Answer;
import application.viope.math.combinedapp.bean.ln7_Exercise;
import io.github.kexanie.library.MathView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ln7_Exercises_frac extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton goToHome;
    private ImageButton goToExerciseGr;
    private Button submitButton;
    private Button previousButton;
    private Button nextButton;
    private EditText inputEditText;
    private String correctAnswer;
    private String answerStr;
    private ImageView image;
    private TextView questions;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    private List<ln7_Exercise> exerciseList = new ArrayList<ln7_Exercise>();
    private int questionNumber = 0;
    private HashMap<Integer, ln7_Answer> AnswerMapper = new HashMap<Integer, ln7_Answer>();

    private MathView formula_one;

    private EquDatabaseHelper db = new EquDatabaseHelper(this);

    public void onBackPressed(){ } // Empty method for disabling phone's own back- button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check the orientation of the device
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            setContentView(R.layout.ln7_activity_fraction_exercises);

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setContentView(R.layout.ln7_land_activity_fraction_exercises);
        }


        // Initiate buttons and Views
        questions = (TextView) findViewById(R.id.questionNr);
        formula_one = (MathView) findViewById(R.id.formula_one);
        inputEditText = (EditText) findViewById(R.id.inputEt);
        image = (ImageView) findViewById(R.id.errorText);
        submitButton = (Button) findViewById(R.id.submitBt);
        previousButton = (Button) findViewById(R.id.previousBt);
        nextButton = (Button) findViewById(R.id.nextBt);

        getUsersAnswers();
        setDrawerMenu();
        setNavigationViewListener();
        getFracs();
        setActivityConfigurations();
        displayQuestion();
        createButtons();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setContentView(R.layout.ln7_land_activity_fraction_exercises);

            // Initiate buttons and Views
            questions = (TextView) findViewById(R.id.questionNr);
            formula_one = (MathView) findViewById(R.id.formula_one);
            inputEditText = (EditText) findViewById(R.id.inputEt);
            image = (ImageView) findViewById(R.id.errorText);
            submitButton = (Button) findViewById(R.id.submitBt);
            previousButton = (Button) findViewById(R.id.previousBt);
            nextButton = (Button) findViewById(R.id.nextBt);

            getUsersAnswers();
            setDrawerMenu();
            setNavigationViewListener();
            getFracs();
            setActivityConfigurations();
            displayQuestion();
            createButtons();

        } else {

            setContentView(R.layout.ln7_activity_fraction_exercises);

            // Initiate buttons and Views
            questions = (TextView) findViewById(R.id.questionNr);
            formula_one = (MathView) findViewById(R.id.formula_one);
            inputEditText = (EditText) findViewById(R.id.inputEt);
            image = (ImageView) findViewById(R.id.errorText);
            submitButton = (Button) findViewById(R.id.submitBt);
            previousButton = (Button) findViewById(R.id.previousBt);
            nextButton = (Button) findViewById(R.id.nextBt);

            getUsersAnswers();
            setDrawerMenu();
            setNavigationViewListener();
            getFracs();
            setActivityConfigurations();
            displayQuestion();
            createButtons();

        }
    }

    public void getFracs() {
        // get all exercises
        exerciseList = db.getAllExercisesFracs_fr();
    }

    private void getUsersAnswers() {
        List<ln7_Answer> answerList = db.getAllAnswers_fr();
        for (ln7_Answer answer : answerList) {
            AnswerMapper.put(answer.getExercise_id(), answer);
        }
    }

    private void setActivityConfigurations(){
        //prevents keyboard from popping up until the EditText is activated
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Add listener to input -> shows written answer interactively when text changes in input field.
        inputEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                image.setBackgroundResource(android.R.color.transparent);
                inputEditText.setBackgroundResource(R.drawable.ln7_layout_border);
                TextView answerView = (TextView) findViewById(R.id.answerView);
                answerView.setText(s.toString());

            }
        });
    }

    public void displayQuestion(){
        if(exerciseList.size() <= 0){
            return;
        }

        //Reads in the first ln7_Exercise from ExerciseList;
        ln7_Exercise currentExercise = exerciseList.get(questionNumber);

        // Text for displaying the number of current exercise / all exercises on the list
        questions.setText("Exercício " + (questionNumber + 1) + "/" + exerciseList.size());

        // Display question
        formula_one.setText(currentExercise.getQuestion());

        // Get current exercise's correct answer
        correctAnswer = currentExercise.getCorrect();

        ln7_Answer answer = AnswerMapper.get(currentExercise.getId());
        if (answer != null) {
            String givenAnswer = answer.getAnswer();
            inputEditText.setText(givenAnswer);
            correctAnswer();
        } else {
            resetAnswer();
        }
    }

    public void createButtons() {
        // onClick- listeners to Home, ln7_ExerciseGroups, nextButton, previousButton and Submit buttons
        goToHome = (ImageButton) findViewById(R.id.home_bt);
        goToHome.setOnClickListener((new View.OnClickListener() {
            public void onClick (View v) {
                Intent homeintent = new Intent(ln7_Exercises_frac.this, EquMainActivity.class);
                startActivity(homeintent);
            }
        }));

        goToExerciseGr = (ImageButton) findViewById(R.id.exerciseGr_bt);
        goToExerciseGr.setOnClickListener((new View.OnClickListener() {
            public void onClick (View v) {
                Intent exercisegrIntent = new Intent(ln7_Exercises_frac.this, ln7_ExerciseGroups.class);
                startActivity(exercisegrIntent);
            }
        }));

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Check answerField compared to correctAnswer and then run correctAnswer() or wrongAnswer()
                answerStr = inputEditText.getText().toString().trim();

                if (answerStr.length() != 0) {
                    if(answerStr.matches(correctAnswer)){
                        correctAnswer();

                        //Save answered value
                        int questionId = exerciseList.get(questionNumber).getId();
                        AnswerMapper.put(questionId, new ln7_Answer(questionId, answerStr, true));

                        //save to SQLite
                        ln7_Answer answer = new ln7_Answer(questionId, answerStr,true);
                        db.addAnswer_fr(answer); //saves only correct answers...

                        //Toast
                        showToast("Vá para o próximo exercício!"); //R.string.next doesn't work , showToast would need improving.
                    } else {
                        wrongAnswer();
                    }
                } else {
                    resetAnswer();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextExercise();
                toggleButtons();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                previousExercise();
                toggleButtons();
            }
        });

        toggleButtons();
    }

    // Checks if next/previous buttons need to be toggled on/off.
    public void toggleButtons(){

        if(questionNumber == 0)
        {
            nextButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.GONE);

        } else if (questionNumber == exerciseList.size()-1){

            nextButton.setVisibility(View.GONE);
            previousButton.setVisibility(View.VISIBLE);

        } else {

            nextButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.VISIBLE);
        }
    }

    // Shows the previous exercise
    public void previousExercise() {
        if (questionNumber > 0){
            questionNumber--;
            displayQuestion();
        }
    }

    // Shows the next exercise
    public void nextExercise() {
        if (questionNumber < exerciseList.size()-1){
            questionNumber++;
            displayQuestion();
        }
    }

    // creates Toast
    public void showToast(String thetext) {
        int length = Toast.LENGTH_LONG;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, thetext, length);
        toast.show();
    }

    // Resets input field and buttons to normal
    public void resetAnswer () {
        //reset inputTextView to normal style
        inputEditText.setBackgroundResource(R.drawable.ln7_layout_border);
        inputEditText.setEnabled(true);
        //reset errorText
        image.setBackgroundResource(android.R.color.transparent);
        //reset inputEditText
        inputEditText.setText("");
        //reset Button
        submitButton.setBackgroundResource(R.drawable.ln7_bt_gradient_green);
        submitButton.setEnabled(true);
    }

    // Handles correct answers
    public void correctAnswer () {
        //change inputTextView
        inputEditText.setBackgroundResource(R.drawable.ln7_correct_answer_et);
        //disable inputTextView
        inputEditText.setEnabled(false);
        //change errorText
        image.setBackgroundResource(R.drawable.ln7_correct);
        //change submitButton style:
        submitButton.setBackgroundResource(R.drawable.ln7_bt_disabled);
        submitButton.setEnabled(false);
    }

    // Handles ln7_incorrect answer
    public void wrongAnswer () {
        inputEditText.setBackgroundResource(R.drawable.ln7_wrong_answer_et);
        image.setBackgroundResource(R.drawable.ln7_incorrect);
    }


    public void setDrawerMenu() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ln7_actionbar_gradient_violet));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        Intent MenuIntents = getIntent();
        switch (item.getItemId()) {

            case R.id.navi_Home:
                MenuIntents = new Intent(ln7_Exercises_frac.this, EquMainActivity.class);
                startActivity(MenuIntents);
                return true;

            case R.id.navi_Excerices:
                MenuIntents = new Intent(ln7_Exercises_frac.this, ln7_ExerciseGroups.class);
                startActivity(MenuIntents);
                return true;

            case R.id.navi_Theory:
                MenuIntents = new Intent(ln7_Exercises_frac.this, ln7_FracTheory.class);
                startActivity(MenuIntents);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setNavigationViewListener(){

        NavigationView nView = (NavigationView) findViewById(R.id.nav_view);
        nView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
