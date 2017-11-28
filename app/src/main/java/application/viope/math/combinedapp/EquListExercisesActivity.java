package application.viope.math.combinedapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import application.viope.math.combinedapp.bean.EquAnswerstatus;

public class EquListExercisesActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.viope.saskia.viope0.MESSAGE";
    private EquDatabaseHelper dbHelper;
    private EquFirebaseHelper FbHelper;
    private String id;
    private Button eBtn;
    private int numberofquestions;
    private int frows = numberofquestions / 3;
    private float rowsfloat;
    private float prows;
    private int buttonsbeforepartials;
    private int answerStatus;
    private long getnumberofquestions;
    private EquAnswerstatus equAnswerstatus;

    private ImageButton settingsButton;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dbHelper = new EquDatabaseHelper(this);
        FbHelper = new EquFirebaseHelper();
        setContentView(R.layout.equ_activity_list_exercises);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.equ_toolbar_custom);

        //setupping the settings menu icon and popup
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(EquListExercisesActivity.this, settingsButton);
                popupMenu.getMenuInflater().inflate(R.menu.equ_toolbar_custom_popupmenu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    Intent intent;
                    public boolean onMenuItemClick(MenuItem item) {
                        CharSequence itemTitle = item.getTitle();
                        if (itemTitle.equals("Info")) {
                            intent = new Intent(EquListExercisesActivity.this, EquInfoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else if (itemTitle.equals("Carregar as respostas")) {
                            if (EquAppNetStatus.getInstance(EquListExercisesActivity.this).isOnline()) {

                                if(dbHelper.checkIfUseridExists() == true){
                                    FbHelper.Post(dbHelper.toFirebaseFromLocalAnswerstatus(), dbHelper.getPost(), dbHelper.getUserid());
                                }else{
                                    String userid = FbHelper.getKey();
                                    dbHelper.updateUserid(userid);
                                    FbHelper.Post(dbHelper.toFirebaseFromLocalAnswerstatus(), dbHelper.getPost(), userid);
                                }

                                Toast.makeText(EquListExercisesActivity.this, "Answers uploaded", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EquListExercisesActivity.this, "Internet connection not detected", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (EquAppNetStatus.getInstance(EquListExercisesActivity.this).isOnline()) {

                                //KYSYMYSTEN LATAAMINEN TULEE TÄHÄN

                                FbHelper.Get();
                                TextView tView = (TextView) findViewById(R.id.downloadQuestions);
                                tView.setText("Loading questions...");

                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getnumberofquestions = dbHelper.getQuestionCount();
                                        inflateButtons();
                                        Toast.makeText(EquListExercisesActivity.this, "Questions downloaded", Toast.LENGTH_SHORT).show();
                                    }
                                }, 5000);

                            } else {
                                Toast.makeText(EquListExercisesActivity.this, "Internet connection not detected", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        inflateButtons();
    }

    public void inflateButtons() {
        if (mRelativeLayout != null) {
            mRelativeLayout.removeAllViews();
        }

        dbHelper = new EquDatabaseHelper(this);
        getnumberofquestions = dbHelper.getQuestionCount();
        numberofquestions = (int) getnumberofquestions;
        frows = numberofquestions / 3;
        rowsfloat = (float) numberofquestions / 3;
        prows = rowsfloat - (float) frows;

        int partialbuttons;
        if (prows < 0.5 && prows > 0) {
            partialbuttons = 1;
        } else if (prows > 0.5) {
            partialbuttons = 2;
        } else {
            partialbuttons = 0;
        }

        if (numberofquestions != 0) {
            View downloadText = findViewById(R.id.downloadQuestions);
            View scrollView = findViewById(R.id.scrollButtons);
            scrollView.setVisibility(View.VISIBLE);
            downloadText.setVisibility(View.GONE);
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
                equAnswerstatus = dbHelper.getAnswerStatus(questionOrder);

                answerStatus = equAnswerstatus.getAnswerStatus();
                Log.d("ANSWER STATUS: ", " " + answerStatus);
                if (answerStatus == 1) {
                    eBtn.setBackgroundResource(R.drawable.equ_exercisebuttonyellow);
                } else if (answerStatus == 2){
                    eBtn.setBackgroundResource(R.drawable.equ_exercisebuttongreen);
                } else {
                    eBtn.setBackgroundResource(R.drawable.equ_exercisebutton);
                }

                // add view to the inner LinearLayout
                row.addView(eBtn);
                buttonsbeforepartials = eBtn.getId();

                eBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), EquListActivity.class);
                        id = "" + view.getId();
                        intent.putExtra(EXTRA_MESSAGE, id);
                        finish();
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
                    equAnswerstatus = dbHelper.getAnswerStatus(questionOrder);

                    answerStatus = equAnswerstatus.getAnswerStatus();
                    Log.d("ANSWER STATUS: ", " " + answerStatus);
                    if (answerStatus == 1) {
                        eBtn.setBackgroundResource(R.drawable.equ_exercisebuttonyellow);
                    } else if (answerStatus == 2){
                        eBtn.setBackgroundResource(R.drawable.equ_exercisebuttongreen);
                    } else {
                        eBtn.setBackgroundResource(R.drawable.equ_exercisebutton);
                    }

                    row.addView(eBtn);

                    eBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), EquListActivity.class);
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