package application.viope.math.combinedapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import application.viope.math.combinedapp.bean.EquAnswerstatus;
import application.viope.math.combinedapp.bean.EquPhase;
import application.viope.math.combinedapp.bean.EquQuestion;

public class EquListActivity extends Activity {

    private ArrayList<String> equasionList;
    private ListView listView;
    private EditText input;
    private String inputString;
    private EquCustomAdapter equCustomAdapter;
    private EquCustomKeyboard equCustomKeyboard;

    private static final String TAG = "EquListActivity";
    private ImageView iView;
    private EquQuestion equQuestion;
    private String answer;
    private TextView questionTextView;
    private String currentQuestionId;
    private EquDatabaseHelper dbHelper;
    private EquFirebaseHelper FbHelper;
    private int currentQuestionIdInt;
    private int statusYellow = 1;
    private int statusGreen = 2;
    private EquAnswerstatus equAnswerstatus;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equ_activity_list);

        //ActionBar myActionBar = getSupportActionBar();
        //myActionBar.hide();
        dbHelper = new EquDatabaseHelper(this);
        FbHelper = new EquFirebaseHelper();

        equasionList = new ArrayList<String>();

        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        input = findViewById(R.id.inputListItem);

        equCustomKeyboard = new EquCustomKeyboard(this, R.id.keyboardview, R.xml.equ_hexkbd);
        equCustomKeyboard.registerEditText(R.id.inputListItem);

        listView = findViewById(R.id.eList);


        // Select the questions ID that is selected from the main activity

        Intent intent = getIntent();
        currentQuestionId = intent.getStringExtra(EquListExercisesActivity.EXTRA_MESSAGE);
        try {
            currentQuestionIdInt = Integer.parseInt(currentQuestionId);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "THIS IS CURRENTQUESTIONIDINT = "+currentQuestionIdInt);


        equCustomAdapter = new EquCustomAdapter(this, R.layout.equ_itemlistrow, equasionList);
        listView.setAdapter(equCustomAdapter);
        image = findViewById(R.id.correct);
        image.setVisibility(View.GONE);

        Log.d(TAG, "BEFORE IF ID IS NOT NULL");
        if (currentQuestionId != null) {
            Log.d(TAG, "INSIDE IF ID IS NOT NULL" + currentQuestionIdInt);
            equQuestion = dbHelper.findQuestionById(currentQuestionIdInt);

        }else{
            equQuestion = dbHelper.findFirstQuestion();
        }
        Log.d(TAG, equQuestion.toString());
        answer = equQuestion.getAnswer();
        questionTextView = findViewById(R.id.equasionTextView);
        questionTextView.setText(equQuestion.getQuestionText());

    }

    @Override
    protected void onStart() {
        super.onStart();
        populateListView(equQuestion.getQuestionId());
        checkBackgroundColor();

    }

    @Override
    public void onBackPressed() {
        if (equCustomKeyboard.isCustomKeyboardVisible()){
            equCustomKeyboard.hideCustomKeyboard();
        }else {
            Intent intent = new Intent(this, EquListExercisesActivity.class);
            finish();
            startActivity(intent);
        }
    }

    public void isKeyboardActive() {
        if (equCustomKeyboard.isCustomKeyboardVisible()){
            ImageButton backButton = findViewById(R.id.backButton);
            backButton.setVisibility(View.GONE);
        }else{
            ImageButton backButton = findViewById(R.id.backButton);
            backButton.setVisibility(View.GONE);
        }
    }

    public void populateListView(String questionId) {
        equQuestion.removeAllPhases();
        Cursor data = dbHelper.getPhases(questionId);
        //ArrayList<String> equasionList = new ArrayList<String>();
        Log.d(TAG, "populating listview");
        if (data != null){
            while(data.moveToNext()){
                //get value from the db, first column
                //add it to the ArrayList
                EquPhase equPhase = new EquPhase(questionId, data.getString(0));
                equQuestion.addPhase(equPhase);
                //equasionList.add(data.getString(3));
            }
        }
        List<EquPhase> p = equQuestion.getEquPhaseList();
        List<String> phases = new ArrayList<String>();
        for (int i = 0; i < p.size(); i++){
            phases.add(p.get(i).getPhaseText());
        }
        equCustomAdapter = new EquCustomAdapter(this, R.layout.equ_itemlistrow, phases);
        listView = findViewById(R.id.eList);
        listView.setAdapter(equCustomAdapter);
    }

    public void AddData(String inputString, String questionId){
        dbHelper.addData(inputString, questionId);
        populateListView(questionId);
    }

    public void checkBackgroundColor() {
        int status = dbHelper.getAnswerStatus(equQuestion.getQuestionOrder()).getAnswerStatus();
        EditText text = findViewById(R.id.inputListItem);
        ImageButton add = findViewById(R.id.addListButton);
        ImageButton delete = findViewById(R.id.delButton);
        if (status == 2){
            final Animation animZoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
            if(image.getVisibility() == View.GONE){
                image.startAnimation(animZoom);
            }
            image.setVisibility(View.VISIBLE);
            if (text.isFocusable()){
                text.setFocusable(false);
                text.setEnabled(false);
                add.setClickable(false);
                delete.setClickable(false);
                //text.setCursorVisible(false);
                //text.setKeyListener(null);
            }
            equCustomKeyboard.hideCustomKeyboard();
        }else{
            final Animation animZoomOut = AnimationUtils.loadAnimation(this, R.anim.zoomout);
            if(image.getVisibility() == View.VISIBLE){
                image.startAnimation(animZoomOut);
            }
            text.setFocusableInTouchMode(true);
            text.setEnabled(true);
            add.setClickable(true);
            delete.setClickable(true);
            equCustomKeyboard.hideCustomKeyboard();
            image.setVisibility(View.GONE);
            //text.setFocusable(true);
        }
        /*if (equAnswerstatus != null){
            if (equAnswerstatus.getAnswerStatus() == statusGreen){
                Log.d(TAG, "OIKEA VASTAUS PITÄISI NYT VAIHTUA BLAA BLAA BLAA");
                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                //relative.setBackgroundColor(13434828);
                //View someView = findViewById(R.id.backButton);
                //View rootView = someView.getRootView();
                //rootView.setBackgroundColor(13434828);
                //View view = this.getWindow().getDecorView();
                //view.setBackgroundColor(13434828);
            }else{
                Log.d(TAG, "ANSWERSTATUS ON ======> " + equAnswerstatus.getAnswerStatus());
            }
        }*/

    }

    public void addButtonClicked(View view) {
        inputString = input.getText().toString().toLowerCase();
        EquTesti equTesti = new EquTesti();
        iView = new ImageView(getApplicationContext());
        if(inputString.contains("x") && inputString.contains("=") && inputString.matches(".*\\d+.*")){
            if(equTesti.testi(inputString, answer) == 1){
                equCustomKeyboard.hideCustomKeyboard();

                //add phase to db
                AddData(inputString, equQuestion.getQuestionId());
                equCustomAdapter.notifyDataSetChanged();
                input.getText().clear();
                listView.setSelection(equCustomAdapter.getCount() -1);

                //update status to db
                dbHelper.updateStatus(statusGreen, equQuestion.getQuestionId(), currentQuestionIdInt);
                checkBackgroundColor();

            }else if(equTesti.testi(inputString, answer) == 2) {

                showCorrectPhaseToast();

                //add phase to db
                AddData(inputString, equQuestion.getQuestionId());
                equCustomAdapter.notifyDataSetChanged();
                input.getText().clear();
                listView.setSelection(equCustomAdapter.getCount() -1);

                //update status to db
                dbHelper.updateStatus(statusYellow, equQuestion.getQuestionId(), currentQuestionIdInt);

            }else if(equTesti.testi(inputString, answer) == 3){
                //Incorrect Answer/EquPhase
                showIncorrectToast();

            }else if(equTesti.testi(inputString, answer) == 4){
                //Syntax Error
                showIncorrectToast();
            } else {
                //Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
            }
        }else{
            //Toast.makeText(getApplicationContext(), "Your input must contain x, = and number(s)", Toast.LENGTH_SHORT).show();
            showIncorrectToast();
        }
    }


    public void deleteButtonClicked(View view){
        if(!equCustomAdapter.isEmpty()) {
            //equCustomAdapter.remove(equCustomAdapter.getItem(equCustomAdapter.getCount() - 1));
            //equCustomAdapter.notifyDataSetChanged();
            equQuestion.removeLastPhase();
            dbHelper.deleteData();
            populateListView(equQuestion.getQuestionId());
        }
    }


    public void nextButtonClicked(View view) {
        loadNextQuestion();
    }


    public void backButtonClicked(View view){
        loadPreviousQuestion();
    }

    public void menuButtonClicked(View view) {
        Intent intent = new Intent(this, EquListExercisesActivity.class);
        finish();
        startActivity(intent);
    }

    public void infoButtonClicked(View view) {
        Intent intent = new Intent(this, EquInfoActivity.class);
        startActivity(intent);
    }

    public void loadNextQuestion() {
        int count = (int) dbHelper.getQuestionCount();
        int order = equQuestion.getQuestionOrder();
        Handler handler = new Handler();
        final Animation animSlide1 = AnimationUtils.loadAnimation(this, R.anim.slide1);
        final Animation animSlide2 = AnimationUtils.loadAnimation(this, R.anim.slide2);
        Log.d(TAG, "QUESTION COUNT IS === " + count + " AND");
        Log.d(TAG, "QUESTION ORDER IS === " + order);
        if (order == count){
            Intent intent = new Intent(this, EquListExercisesActivity.class);
            finish();
            startActivity(intent);
        }else{
            equQuestion = dbHelper.findNextQuestion();
            currentQuestionIdInt++;
            Log.d(TAG, equQuestion.toString());
            answer = equQuestion.getAnswer();
            questionTextView.startAnimation(animSlide1);
            listView.startAnimation(animSlide1);
            handler.postDelayed(new Runnable() {
                public void run() {
                    questionTextView.setText(equQuestion.getQuestionText());
                    questionTextView.startAnimation(animSlide2);
                    listView.startAnimation(animSlide2);
                    answer = equQuestion.getAnswer();
                    populateListView(equQuestion.getQuestionId());
                    checkBackgroundColor();
                }
            }, 200);
        }
    }

    public void loadPreviousQuestion() {
        equQuestion = dbHelper.findPreviousQuestion();
        Handler handler = new Handler();
        final Animation animSlide3 = AnimationUtils.loadAnimation(this, R.anim.slide3);
        final Animation animSlide4 = AnimationUtils.loadAnimation(this, R.anim.slide4);
        currentQuestionIdInt--;
        if (equQuestion == null){
            Intent intent = new Intent(this, EquListExercisesActivity.class);
            finish();
            startActivity(intent);
        }else{
            questionTextView.startAnimation(animSlide3);
            listView.startAnimation(animSlide3);
            handler.postDelayed(new Runnable() {
                public void run() {
                    questionTextView.setText(equQuestion.getQuestionText());
                    questionTextView.startAnimation(animSlide4);
                    listView.startAnimation(animSlide4);
                    answer = equQuestion.getAnswer();
                    populateListView(equQuestion.getQuestionId());
                    checkBackgroundColor();
                }
            }, 200);
        }
    }

    public void showCorrectPhaseToast() {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        int imageSize = (int) (density * 100 + 0.5f);
        int imageMargin = (int) (density * 50 + 0.5f);

        TextView tView = new TextView(this);
        tView.setTextSize(25);

        Toast toast = new Toast(getApplicationContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imageSize, imageSize);
        layoutParams.setMargins(0, imageMargin, 0, 0);
        iView.setLayoutParams(layoutParams);
        iView.setImageResource(R.drawable.equ_thumb);

        RelativeLayout layout = new RelativeLayout(this);

        layout.addView(iView);
        layout.addView(tView);

        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, -300);
        toast.setView(layout);
        toast.show();
    }

    public void showIncorrectToast() {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        int imageSize = (int) (density * 100 + 0.5f);
        int imageMargin = (int) (density * 50 + 0.5f);

        TextView tView = new TextView(this);
        tView.setTextSize(15);
        //tView.setText("That is not right");

        Toast toast = new Toast(getApplicationContext());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imageSize, imageSize);
        layoutParams.setMargins(0, imageMargin, 0, 0);
        iView.setLayoutParams(layoutParams);
        iView.setImageResource(R.drawable.equ_incorrect);

        RelativeLayout layout = new RelativeLayout(this);

        layout.addView(iView);
        layout.addView(tView);

        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, -400);
        toast.setView(layout);
        toast.show();
    }
}