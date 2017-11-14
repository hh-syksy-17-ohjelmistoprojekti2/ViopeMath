package application.viope.math.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import application.viope.math.app.bean.EquAnswerstatus;
import application.viope.math.app.bean.EquPhase;
import application.viope.math.app.bean.EquQuestion;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equ_activity_list);

        //ActionBar myActionBar = getSupportActionBar();
        //myActionBar.hide();
        dbHelper = new EquDatabaseHelper(this);
        FbHelper = new EquFirebaseHelper();

        equasionList = new ArrayList<String>();

        input = (EditText) findViewById(R.id.inputListItem);

        equCustomKeyboard = new EquCustomKeyboard(this, R.id.keyboardview, R.xml.equ_hexkbd);
        equCustomKeyboard.registerEditText(R.id.inputListItem);

        listView = (ListView) findViewById(R.id.eList);


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



        // ****************
        // **** HUOMIO ****
        // ****************

        // POISTA KOMMENTOINNIT SEURAAVILTA KOLMELTA RIVILTÄ ENSIMMÄISELLÄ KÄYNNISTYKSELLÄ,
        // JONKA JÄLKEEN LAITA KOMMENNTIT TAKAISIN!

        // KOMENTO ASETTAA KOLME KYSYMYSTÄ PUHELIMEN LOKAALIIN TIETOKANTAAN TESTAUSTA VARTEN

        //dbHelper.addTestDataForShowingQuestion("2(3x-4)+5=3(x+1)", "2", "esimerkkiid", 1);
        //dbHelper.addTestDataForShowingQuestion("3(2x+3)-5=-4(-x+3)", "-8", "esimerkkiiid", 2);
        //dbHelper.addTestDataForShowingQuestion("(3(5x-2))/4+4=(4(4x-3))/2", "2", "esimerkkiiiid", 3);
        Log.d(TAG, "BEFORE IF ID IS NOT NULL");
        if (currentQuestionId != null) {
            Log.d(TAG, "INSIDE IF ID IS NOT NULL" + currentQuestionIdInt);
            equQuestion = dbHelper.findQuestionById(currentQuestionIdInt);

        }else{
            equQuestion = dbHelper.findFirstQuestion();
        }
        Log.d(TAG, equQuestion.toString());
        answer = equQuestion.getAnswer();
        questionTextView = (TextView) findViewById(R.id.equasionTextView);
        questionTextView.setText(equQuestion.getQuestionText());

        //FbHelper.Post("Pekka");
        //FbHelper.Get();

        //FbHelper.Post(dbHelper.getPost());
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateListView(equQuestion.getQuestionId());
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
        listView = (ListView) findViewById(R.id.eList);
        listView.setAdapter(equCustomAdapter);
    }

    public void AddData(String inputString, String questionId){
        dbHelper.addData(inputString, questionId);
        populateListView(questionId);
    }


    public void addButtonClicked(View view){

        inputString = input.getText().toString().toLowerCase();
        EquTesti equTesti = new EquTesti();
        iView = new ImageView(getApplicationContext());
        if(inputString.contains("x") && inputString.contains("=") && inputString.matches(".*\\d+.*")){
            if(equTesti.testi(inputString, answer) == 1){
                equCustomKeyboard.hideCustomKeyboard();

                showCorrectAnswerToast();

                //add phase to db
                AddData(inputString, equQuestion.getQuestionId());
                equCustomAdapter.notifyDataSetChanged();
                input.getText().clear();
                listView.setSelection(equCustomAdapter.getCount() -1);

                //update status to db
                dbHelper.updateStatus(statusGreen, equQuestion.getQuestionId(), currentQuestionIdInt);

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
        startActivity(intent);
    }

    public void infoButtonClicked(View view) {
        Intent intent = new Intent(this, EquInfoActivity.class);
        startActivity(intent);
    }

    public void loadNextQuestion() {
        equQuestion = dbHelper.findNextQuestion();
        Log.d(TAG, equQuestion.toString());
        answer = equQuestion.getAnswer();
        populateListView(equQuestion.getQuestionId());
        questionTextView.setText(equQuestion.getQuestionText());
        equCustomKeyboard.showCustomKeyboard(input);
    }

    public void loadPreviousQuestion() {
        equQuestion = dbHelper.findPreviousQuestion();
        if (equQuestion == null){
            finish();
        }else{
            questionTextView.setText(equQuestion.getQuestionText());
            answer = equQuestion.getAnswer();
            populateListView(equQuestion.getQuestionId());
            equCustomKeyboard.showCustomKeyboard(input);
        }

    }

    public void showCorrectAnswerToast() {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        int imageSize = (int) (density * 100 + 0.5f);
        int imageMargin = (int) (density * 50 + 0.5f);

        TextView tView = new TextView(this);
        tView.setTextSize(25);

        Toast toast = new Toast(getApplicationContext());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imageSize, imageSize);
        layoutParams.setMargins(0, imageMargin, 0, 0);
        iView.setLayoutParams(layoutParams);
        iView.setImageResource(R.drawable.equ_correct);

        RelativeLayout layout = new RelativeLayout(this);

        layout.addView(iView);
        layout.addView(tView);

        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, -50);
        toast.setView(layout);
        toast.show();
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
