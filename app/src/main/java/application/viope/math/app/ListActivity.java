package application.viope.math.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
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

import application.viope.math.app.bean.Answerstatus;
import application.viope.math.app.bean.Phase;
import application.viope.math.app.bean.Question;

public class ListActivity extends Activity {

    private ArrayList<String> equasionList;
    private ListView listView;
    private EditText input;
    private String inputString;
    private CustomAdapter customAdapter;
    private CustomKeyboard customKeyboard;

    private static final String TAG = "ListActivity";
    private ImageView iView;
    private Question question;
    private String answer;
    private TextView questionTextView;
    private String currentQuestionId;
    private DatabaseHelper dbHelper;
    private FirebaseHelper FbHelper;
    private int currentQuestionIdInt;
    private int statusYellow = 1;
    private int statusGreen = 2;
    private Answerstatus answerstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //ActionBar myActionBar = getSupportActionBar();
        //myActionBar.hide();
        dbHelper = new DatabaseHelper(this);
        FbHelper = new FirebaseHelper();

        equasionList = new ArrayList<String>();

        input = (EditText) findViewById(R.id.inputListItem);

        customKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.xml.hexkbd);
        customKeyboard.registerEditText(R.id.inputListItem);

        listView = (ListView) findViewById(R.id.eList);


        // Select the questions ID that is selected from the main activity

        Intent intent = getIntent();
        currentQuestionId = intent.getStringExtra(ListExercisesActivity.EXTRA_MESSAGE);
        try {
            currentQuestionIdInt = Integer.parseInt(currentQuestionId);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "THIS IS CURRENTQUESTIONIDINT = "+currentQuestionIdInt);


        customAdapter = new CustomAdapter(this, R.layout.itemlistrow, equasionList);
        listView.setAdapter(customAdapter);



        // ****************
        // **** HUOMIO ****
        // ****************

        // POISTA KOMMENTOINNIT SEURAAVILTA KOLMELTA RIVILTÄ ENSIMMÄISELLÄ KÄYNNISTYKSELLÄ,
        // JONKA JÄLKEEN LAITA KOMMENNTIT TAKAISIN!

        // KOMENTO ASETTAA KOLME KYSYMYSTÄ PUHELIMEN LOKAALIIN TIETOKANTAAN TESTAUSTA VARTEN

        dbHelper.addTestDataForShowingQuestion("2(3x-4)+5=3(x+1)", "2", "esimerkkiid", 1);
        dbHelper.addTestDataForShowingQuestion("3(2x+3)-5=-4(-x+3)", "-8", "esimerkkiiid", 2);
        dbHelper.addTestDataForShowingQuestion("(3(5x-2))/4+4=(4(4x-3))/2", "2", "esimerkkiiiid", 3);
        Log.d(TAG, "BEFORE IF ID IS NOT NULL");
        if (currentQuestionId != null) {
            Log.d(TAG, "INSIDE IF ID IS NOT NULL" + currentQuestionIdInt);
            question = dbHelper.findQuestionById(currentQuestionIdInt);

        }else{
            question = dbHelper.findFirstQuestion();
        }
        Log.d(TAG, question.toString());
        answer = question.getAnswer();
        questionTextView = (TextView) findViewById(R.id.equasionTextView);
        questionTextView.setText(question.getQuestionText());

        //FbHelper.Post("Pekka");
        FbHelper.Get();
        dbHelper.addStatus(0, "1245",12);
        dbHelper.addStatus(0, "12415",13);
        dbHelper.addStatus(0, "154215",14);
        //FbHelper.Post(dbHelper.getPost());
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateListView(question.getQuestionId());
    }

    public void populateListView(String questionId) {
        question.removeAllPhases();
        Cursor data = dbHelper.getPhases(questionId);
        //ArrayList<String> equasionList = new ArrayList<String>();
        Log.d(TAG, "populating listview");
        if (data != null){
            while(data.moveToNext()){
                //get value from the db, first column
                //add it to the ArrayList
                Phase phase = new Phase(questionId, data.getString(0));
                question.addPhase(phase);
                //equasionList.add(data.getString(3));
            }
        }
        List<Phase> p = question.getPhaseList();
        List<String> phases = new ArrayList<String>();
        for (int i = 0; i < p.size(); i++){
            phases.add(p.get(i).getPhaseText());
        }
        customAdapter = new CustomAdapter(this, R.layout.itemlistrow, phases);
        listView = (ListView) findViewById(R.id.eList);
        listView.setAdapter(customAdapter);
    }

    public void AddData(String inputString, String questionId){
        dbHelper.addData(inputString, questionId);
        populateListView(questionId);
    }


    public void addButtonClicked(View view){

        inputString = input.getText().toString().toLowerCase();
        Testi testi = new Testi();
        iView = new ImageView(getApplicationContext());
        if(inputString.contains("x") && inputString.contains("=") && inputString.matches(".*\\d+.*")){
            if(testi.testi(inputString, answer) == 1){
                customKeyboard.hideCustomKeyboard();

                showCorrectAnswerToast();

                //add phase to db
                AddData(inputString, question.getQuestionId());
                customAdapter.notifyDataSetChanged();
                input.getText().clear();
                listView.setSelection(customAdapter.getCount() -1);

                //update status to db
                dbHelper.updateStatus(statusGreen, question.getQuestionId(), currentQuestionIdInt);

            }else if(testi.testi(inputString, answer) == 2) {

                showCorrectPhaseToast();

                //add phase to db
                AddData(inputString, question.getQuestionId());
                customAdapter.notifyDataSetChanged();
                input.getText().clear();
                listView.setSelection(customAdapter.getCount() -1);

                //update status to db
                dbHelper.updateStatus(statusYellow, question.getQuestionId(), currentQuestionIdInt);

            }else if(testi.testi(inputString, answer) == 3){
                //Incorrect Answer/Phase
                showIncorrectToast();

            }else if(testi.testi(inputString, answer) == 4){
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
        if(!customAdapter.isEmpty()) {
            //customAdapter.remove(customAdapter.getItem(customAdapter.getCount() - 1));
            //customAdapter.notifyDataSetChanged();
            question.removeLastPhase();
            dbHelper.deleteData();
            populateListView(question.getQuestionId());
        }
    }


    public void nextButtonClicked(View view) {
        loadNextQuestion();
    }


    public void backButtonClicked(View view){
        loadPreviousQuestion();
    }

    public void menuButtonClicked(View view) {
        Intent intent = new Intent(this, ListExercisesActivity.class);
        startActivity(intent);
    }

    public void infoButtonClicked(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    public void loadNextQuestion() {
        question = dbHelper.findNextQuestion();
        Log.d(TAG, question.toString());
        answer = question.getAnswer();
        populateListView(question.getQuestionId());
        questionTextView.setText(question.getQuestionText());
        customKeyboard.showCustomKeyboard(input);
    }

    public void loadPreviousQuestion() {
        question = dbHelper.findPreviousQuestion();
        if (question == null){
            finish();
        }else{
            questionTextView.setText(question.getQuestionText());
            answer = question.getAnswer();
            populateListView(question.getQuestionId());
            customKeyboard.showCustomKeyboard(input);
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
