package application.viope.math.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import application.viope.math.app.bean.Answerstatus;

public class ListExercisesActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.viope.saskia.viope0.MESSAGE";
    private DatabaseHelper dbHelper;
    private String id;
    private Button eBtn;
    private int numberofquestions;
    private int frows = numberofquestions / 3;
    private float rowsfloat;
    private float prows;
    private int buttonsbeforepartials;
    private int answerStatus;
    private Answerstatus answerstatus;

    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);

        dbHelper = new DatabaseHelper(this);
        long getnumberofquestions = dbHelper.getQuestionCount();
        numberofquestions = (int) getnumberofquestions;
        frows = numberofquestions / 3;
        rowsfloat = (float) numberofquestions / 3;
        prows = rowsfloat - (float) frows;
        onStart();
    }

    protected void onStart() {
        super.onStart();
        inflateButtons();
    }

    public void inflateButtons() {

        int partialbuttons;
        if (prows < 0.5 && prows > 0) {
            partialbuttons = 1;
        } else if (prows > 0.5) {
            partialbuttons = 2;
        } else {
            partialbuttons = 0;
        }

        // findViewById for RelativeLayout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relative);
        // add LinearLayout
        LinearLayout linear = new LinearLayout(this);
        linear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        // set setOrientation
        linear.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < frows; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            row.setGravity(Gravity.CENTER_HORIZONTAL);

            for (int j = 0; j < 3; j++) {
                ContextThemeWrapper newContext = new ContextThemeWrapper(getBaseContext(), R.style.ExerciseButtonTheme);
                Button eBtn = new Button(newContext);
                final float scale = getResources().getDisplayMetrics().density;
                int dpwidth = (int) (75 * scale);
                int dpheight = (int) (75 * scale);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpwidth, dpheight);
                layoutParams.setMargins(30, 30, 30, 30);
                eBtn.setLayoutParams(layoutParams);
                eBtn.setText("" + (j + 1 + (i * 3)));
                eBtn.setId(j + 1 + (i * 3));
                int questionOrder = j + 1 + (i * 3);
                Log.d("QUESTIONORDER IS: ", "questionorder: " + questionOrder);
                answerstatus = dbHelper.getAnswerStatus(questionOrder);

                answerStatus = answerstatus.getAnswerStatus();
                Log.d("ANSWER STATUS: ", " " + answerStatus);
                if (answerStatus == 1) {
                    eBtn.setBackgroundResource(R.drawable.exercisebuttonyellow);
                } else if (answerStatus == 2) {
                    eBtn.setBackgroundResource(R.drawable.exercisebuttongreen);
                } else {
                    eBtn.setBackgroundResource(R.drawable.exercisebutton);
                }

                // add view to the inner LinearLayout
                row.addView(eBtn);
                buttonsbeforepartials = eBtn.getId();

                eBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                        id = "" + view.getId();
                        intent.putExtra(EXTRA_MESSAGE, id);
                        startActivity(intent);
                    }
                });
            }
            // add view to the outer LinearLayout
            linear.addView(row);
        }
        if (partialbuttons != 0) {
            for (int i = 0; i < 1; i++) {
                LinearLayout row = new LinearLayout(this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                row.setGravity(Gravity.CENTER_HORIZONTAL);


                for (int j = 0; j < partialbuttons; j++) {
                    ContextThemeWrapper newContext = new ContextThemeWrapper(getBaseContext(), R.style.ExerciseButtonTheme);
                    eBtn = new Button(newContext);
                    final float scale = getResources().getDisplayMetrics().density;
                    int dpwidth = (int) (75 * scale);
                    int dpheight = (int) (75 * scale);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpwidth, dpheight);
                    layoutParams.setMargins(30, 30, 30, 30);
                    eBtn.setLayoutParams(layoutParams);

                    eBtn.setText("" + (buttonsbeforepartials + (j + 1)));
                    eBtn.setId(buttonsbeforepartials + (j + 1));
                    int questionOrder = buttonsbeforepartials + (j + 1);
                    Log.d("QUESTIONORDER IS: ", "questionorder: " + questionOrder);
                    answerstatus = dbHelper.getAnswerStatus(questionOrder);

                    answerStatus = answerstatus.getAnswerStatus();
                    Log.d("ANSWER STATUS: ", " " + answerStatus);
                    if (answerStatus == 1) {
                        eBtn.setBackgroundResource(R.drawable.exercisebuttonyellow);
                    } else if (answerStatus == 2) {
                        eBtn.setBackgroundResource(R.drawable.exercisebuttongreen);
                    } else {
                        eBtn.setBackgroundResource(R.drawable.exercisebutton);
                    }

                    row.addView(eBtn);

                    eBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                            id = "" + view.getId();
                            intent.putExtra(EXTRA_MESSAGE, id);
                            startActivity(intent);
                        }
                    });
                }
                linear.addView(row);
            }
        }
        mRelativeLayout.addView(linear);
    }
}