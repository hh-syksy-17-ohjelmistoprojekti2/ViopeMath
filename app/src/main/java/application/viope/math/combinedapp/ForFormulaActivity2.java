package application.viope.math.combinedapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.lang.String;

import application.viope.math.combinedapp.bean.ForDatabaseHelper;
import application.viope.math.combinedapp.bean.ForQuestions;


public class ForFormulaActivity2 extends AppCompatActivity {

    Button answer1, answer2, answer3, answer4, nextButton;
    final Context context = this;
    TextView question, score, juniorTextView;
    ImageView dynamicImage;
    private ForQuestions mForQuestions = new ForQuestions();
    private String img;
    private String mAnswer;
    private int mScore = 0;
    private int mQuestionsLenght = mForQuestions.mQuestions.length;
    private int questionIndex = 0;
    private boolean isEnabled = false;
    private int isCorrect = 0;
    ForDatabaseHelper db;


    private final ArrayList<ForQuestions> QuestionArray= new ArrayList<ForQuestions>();
    // Context dynamicImageContext = dynamicImage.getContext();
   /* Random r;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new ForDatabaseHelper(this);


        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.for_activity_formula2);

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.for_activity_formula2_land);
        }

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        menu.setIcon(R.drawable.for_logo1);
        menu.setBackgroundDrawable(getResources().getDrawable(R.drawable.for_actionbar_gradient));

        ArrayList<ForQuestions> QuestionArray =new ArrayList<ForQuestions>();
        QuestionArray = db.getQuestions();
        int r = 0;
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);
        nextButton = (Button) findViewById(R.id.nextButton);
        dynamicImage = (ImageView) findViewById(R.id.image_view_1);
        question = (TextView) findViewById(R.id.question);
        score = (TextView) findViewById(R.id.score);
        juniorTextView = (TextView) findViewById(R.id.juniorTextView);
        score.setText("Ponto: " + mScore);

        updateQuestion(questionIndex, QuestionArray);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ForQuestions> QuestionArray =new ArrayList<ForQuestions>();
                QuestionArray = db.getQuestions();
                updateQuestion(questionIndex, QuestionArray);
            }
        });

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer1text= answer1.getText().toString().replaceAll("\\s+","").toString();
                String answer2text= answer2.getText().toString().replaceAll("\\s+","").toString();
                String answer3text= answer3.getText().toString().replaceAll("\\s+","").toString();
                String answer4text= answer4.getText().toString().replaceAll("\\s+","").toString();
                String mAnswertext = mAnswer.replaceAll("\\s+","").toString();


                if (answer1text.equals(mAnswertext) ) {
                    answer1.setBackgroundResource(R.drawable.for_bt_correct);
                    answer1.setTextColor(Color.WHITE);
                    db.insertData(1, questionIndex);
                    mScore++;
                    questionIndex++;
                    score.setText("Ponto:" + mScore);
                    setAllButtonsEnabled(false);

                    //updateQuestion(questionIndex);
                    nextButton.setVisibility(View.VISIBLE);
                } else {
                    answer1.setBackgroundResource(R.drawable.for_bt_incorrect);
                    answer1.setTextColor(Color.WHITE);
                    db.insertData(0, questionIndex);
                    if (answer2text.equals(mAnswertext)) {
                        answer2.setBackgroundResource(R.drawable.for_bt_correct);
                        answer2.setTextColor(Color.WHITE);
                    } else if (answer3text.equals(mAnswertext)) {
                        answer3.setBackgroundResource(R.drawable.for_bt_correct);
                        answer3.setTextColor(Color.WHITE);
                    } else {
                        answer4.setBackgroundResource(R.drawable.for_bt_correct);
                        answer4.setTextColor(Color.WHITE);
                    }
                    questionIndex++;
                    setAllButtonsEnabled(false);
                    nextButton.setVisibility(View.VISIBLE);
                    //updateQuestion(questionIndex);


                }

            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer1text= answer1.getText().toString().replaceAll("\\s+","").toString();
                String answer2text= answer2.getText().toString().replaceAll("\\s+","").toString();
                String answer3text= answer3.getText().toString().replaceAll("\\s+","").toString();
                String answer4text= answer4.getText().toString().replaceAll("\\s+","").toString();
                String mAnswertext = mAnswer.replaceAll("\\s+","").toString();
                if (answer2text.equals(mAnswertext)) {
                    answer2.setBackgroundResource(R.drawable.for_bt_correct);
                    answer2.setTextColor(Color.WHITE);
                    db.insertData(1, questionIndex);
                    mScore++;
                    questionIndex++;

                    score.setText("Ponto:" + mScore);
                    //updateQuestion(questionIndex);
                    setAllButtonsEnabled(false);

                    nextButton.setVisibility(View.VISIBLE);
                } else {
                    answer2.setBackgroundResource(R.drawable.for_bt_incorrect);
                    answer2.setTextColor(Color.WHITE);
                    db.insertData(0, questionIndex);
                    if (answer1text.equals(mAnswertext)) {
                        answer1.setBackgroundResource(R.drawable.for_bt_correct);
                        answer1.setTextColor(Color.WHITE);
                    } else if (answer3text.equals(mAnswertext)) {
                        answer3.setBackgroundResource(R.drawable.for_bt_correct);
                        answer3.setTextColor(Color.WHITE);
                    } else {
                        answer4.setBackgroundResource(R.drawable.for_bt_correct);
                        answer4.setTextColor(Color.WHITE);
                    }
                    questionIndex++;
                    setAllButtonsEnabled(false);

                    nextButton.setVisibility(View.VISIBLE);
                    //updateQuestion(questionIndex);
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer1text= answer1.getText().toString().replaceAll("\\s+","").toString();
                String answer2text= answer2.getText().toString().replaceAll("\\s+","").toString();
                String answer3text= answer3.getText().toString().replaceAll("\\s+","").toString();
                String answer4text= answer4.getText().toString().replaceAll("\\s+","").toString();
                String mAnswertext = mAnswer.replaceAll("\\s+","").toString();
                if (answer3text.equals(mAnswertext)) {
                    answer3.setBackgroundResource(R.drawable.for_bt_correct);
                    answer3.setTextColor(Color.WHITE);
                    db.insertData(1, questionIndex);
                    mScore++;
                    questionIndex++;
                    score.setText("Ponto:" + mScore);
                    setAllButtonsEnabled(false);

                    nextButton.setVisibility(View.VISIBLE);
                    //updateQuestion(questionIndex);
                } else {
                    answer3.setBackgroundResource(R.drawable.for_bt_incorrect);
                    answer3.setTextColor(Color.WHITE);
                    db.insertData(0, questionIndex);
                    if (answer2text.equals(mAnswertext)) {
                        answer2.setBackgroundResource(R.drawable.for_bt_correct);
                        answer2.setTextColor(Color.WHITE);
                    } else if (answer1text.equals(mAnswertext) ) {
                        answer1.setBackgroundResource(R.drawable.for_bt_correct);
                        answer1.setTextColor(Color.WHITE);
                    } else {
                        answer4.setBackgroundResource(R.drawable.for_bt_correct);
                        answer4.setTextColor(Color.WHITE);
                    }
                    questionIndex++;
                    setAllButtonsEnabled(false);

                    nextButton.setVisibility(View.VISIBLE);
                    //updateQuestion(questionIndex);
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer1text= answer1.getText().toString().replaceAll("\\s+","").toString();
                String answer2text= answer2.getText().toString().replaceAll("\\s+","").toString();
                String answer3text= answer3.getText().toString().replaceAll("\\s+","").toString();
                String answer4text= answer4.getText().toString().replaceAll("\\s+","").toString();
                String mAnswertext = mAnswer.replaceAll("\\s+","").toString();
                if (answer4text.equals(mAnswertext)) {
                    answer4.setBackgroundResource(R.drawable.for_bt_correct);
                    answer4.setTextColor(Color.WHITE);
                    db.insertData(1, questionIndex);
                    mScore++;
                    questionIndex++;
                    score.setText("Ponto:" + mScore);
                    setAllButtonsEnabled(false);

                    nextButton.setVisibility(View.VISIBLE);
                    //updateQuestion(questionIndex);
                } else {
                    answer4.setBackgroundResource(R.drawable.for_bt_incorrect);
                    answer4.setTextColor(Color.WHITE);
                    db.insertData(0, questionIndex);
                    if (answer2text.equals(mAnswertext)) {
                        answer2.setBackgroundResource(R.drawable.for_bt_correct);
                        answer2.setTextColor(Color.WHITE);
                    } else if (answer3text.equals(mAnswertext)) {
                        answer3.setBackgroundResource(R.drawable.for_bt_correct);
                        answer3.setTextColor(Color.WHITE);
                    } else {
                        answer1.setBackgroundResource(R.drawable.for_bt_correct);
                        answer1.setTextColor(Color.WHITE);
                    }
                    questionIndex++;
                    setAllButtonsEnabled(false);

                    nextButton.setVisibility(View.VISIBLE);
                    //updateQuestion(questionIndex);
                }
            }
        });
    }

    private void updateQuestion(int num, ArrayList<ForQuestions> QuestionArray)  {
        //delay(5);

        if (questionIndex == QuestionArray.size()) {
            completed();
        } else {
            resetButtons();
            question.setText(QuestionArray.get(num).getQuestion());
            juniorTextView.setText(QuestionArray.get(num).getQuestion());
            answer1.setText(QuestionArray.get(num).getChoice1(num));
            answer2.setText(QuestionArray.get(num).getChoice2(num));
            answer3.setText(QuestionArray.get(num).getChoice3(num));
            answer4.setText(QuestionArray.get(num).getChoice4(num));
            mAnswer = QuestionArray.get(num).getCorrectAnswer(num);
            img = QuestionArray.get(num).getQuestionImage(num);
            /*answer1.setText(mQuestions.getChoice1(num));
            answer2.setText(mQuestions.getChoice2(num));
            answer3.setText(mQuestions.getChoice3(num));
            answer4.setText(mQuestions.getChoice4(num));


*/
            AddImageToQuestion(img);
        }
    }

    //resetoi kysymyksen vaihtuessa buttonit alkuperäiseen
    private void resetButtons() {
        nextButton.setVisibility(View.INVISIBLE);
        // setAllButtonsEnabled(true); //
        answer1.setBackgroundResource(R.drawable.for_bt_main);
        answer2.setBackgroundResource(R.drawable.for_bt_main);
        answer3.setBackgroundResource(R.drawable.for_bt_main);
        answer4.setBackgroundResource(R.drawable.for_bt_main);
        answer1.setTextColor(Color.parseColor("#659cdf"));
        answer2.setTextColor(Color.parseColor("#659cdf"));
        answer3.setTextColor(Color.parseColor("#659cdf"));
        answer4.setTextColor(Color.parseColor("#659cdf"));
        setAllButtonsEnabled(true);
    }

    private void completed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle("Toda pergunta respondida!");
        alertDialogBuilder
                .setMessage("Sua pontuação foi " + mScore + "! Retornar ao Menu Principal?")
                .setCancelable(false)
                .setPositiveButton("Me leve para casa!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), ForMainPage.class);
                        startActivity(intent);
                        ForFormulaActivity2.this.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setAllButtonsEnabled(boolean isEnabled) {
        answer1.setEnabled(isEnabled);
        answer2.setEnabled(isEnabled);
        answer3.setEnabled(isEnabled);
        answer4.setEnabled(isEnabled);
    }

    private void AddImageToQuestion(String img) {
        if (img != null && img.length() != 0) {
            question.setVisibility(View.INVISIBLE);
            dynamicImage.setVisibility(View.VISIBLE);
            juniorTextView.setVisibility(View.VISIBLE);
            int resId = getResources().getIdentifier(img, "drawable", "application.viope.math.combinedapp");
            dynamicImage.setImageResource(resId);
        } else {
            question.setVisibility(View.VISIBLE);
            dynamicImage.setVisibility(View.INVISIBLE);
            juniorTextView.setVisibility(View.INVISIBLE);
        }
    }

    //Juuh elikkäs. Eli tässä jouduttiin nyt alustamaan kaikki buttonit ja metodit uudestaan etteivät napit lukittuisi käännettäessä. Tämä ratkaisu on vastoin kaikkia estetiikan lakeja, mutta se toimii.
    //Deadline rupesi puskemaan päälle, tätä osuutta päivitetään ja korjataan vain jos aikataulu ja mielenterveys sallii.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setContentView(R.layout.for_activity_formula2_land);

            int r = 0;
            answer1 = (Button) findViewById(R.id.answer1);
            answer2 = (Button) findViewById(R.id.answer2);
            answer3 = (Button) findViewById(R.id.answer3);
            answer4 = (Button) findViewById(R.id.answer4);
            nextButton = (Button) findViewById(R.id.nextButton);
            dynamicImage = (ImageView) findViewById(R.id.image_view_1);
            question = (TextView) findViewById(R.id.question);
            score = (TextView) findViewById(R.id.score);
            juniorTextView = (TextView) findViewById(R.id.juniorTextView);
            score.setText("Ponto: " + mScore);


            updateQuestion(questionIndex, QuestionArray);

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateQuestion(questionIndex, QuestionArray);
                }
            });

            answer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer1.getText() == mAnswer) {
                        answer1.setBackgroundResource(R.drawable.for_bt_correct);
                        answer1.setTextColor(Color.WHITE);
                        mScore++;
                        questionIndex++;
                        score.setText("Ponto:" + mScore);
                        setAllButtonsEnabled(false);

                        //updateQuestion(questionIndex);
                        nextButton.setVisibility(View.VISIBLE);
                    } else {
                        answer1.setBackgroundResource(R.drawable.for_bt_incorrect);
                        answer1.setTextColor(Color.WHITE);
                        if (answer2.getText() == mAnswer) {
                            answer2.setBackgroundResource(R.drawable.for_bt_correct);
                            answer2.setTextColor(Color.WHITE);
                        } else if (answer3.getText() == mAnswer) {
                            answer3.setBackgroundResource(R.drawable.for_bt_correct);
                            answer3.setTextColor(Color.WHITE);
                        } else {
                            answer4.setBackgroundResource(R.drawable.for_bt_correct);
                            answer4.setTextColor(Color.WHITE);
                        }
                        questionIndex++;
                        setAllButtonsEnabled(false);
                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);


                    }

                }
            });

            answer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer2.getText() == mAnswer) {
                        answer2.setBackgroundResource(R.drawable.for_bt_correct);
                        answer2.setTextColor(Color.WHITE);
                        mScore++;
                        questionIndex++;

                        score.setText("Ponto:" + mScore);
                        //updateQuestion(questionIndex);
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                    } else {
                        answer2.setBackgroundResource(R.drawable.for_bt_incorrect);
                        answer2.setTextColor(Color.WHITE);
                        if (answer1.getText() == mAnswer) {
                            answer1.setBackgroundResource(R.drawable.for_bt_correct);
                            answer1.setTextColor(Color.WHITE);
                        } else if (answer3.getText() == mAnswer) {
                            answer3.setBackgroundResource(R.drawable.for_bt_correct);
                            answer3.setTextColor(Color.WHITE);
                        } else {
                            answer4.setBackgroundResource(R.drawable.for_bt_correct);
                            answer4.setTextColor(Color.WHITE);
                        }
                        questionIndex++;
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);
                    }
                }
            });

            answer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer3.getText() == mAnswer) {
                        answer3.setBackgroundResource(R.drawable.for_bt_correct);
                        answer3.setTextColor(Color.WHITE);
                        mScore++;
                        questionIndex++;
                        score.setText("Ponto:" + mScore);
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);
                    } else {
                        answer3.setBackgroundResource(R.drawable.for_bt_incorrect);
                        answer3.setTextColor(Color.WHITE);
                        if (answer2.getText() == mAnswer) {
                            answer2.setBackgroundResource(R.drawable.for_bt_correct);
                            answer2.setTextColor(Color.WHITE);
                        } else if (answer1.getText() == mAnswer) {
                            answer1.setBackgroundResource(R.drawable.for_bt_correct);
                            answer1.setTextColor(Color.WHITE);
                        } else {
                            answer4.setBackgroundResource(R.drawable.for_bt_correct);
                            answer4.setTextColor(Color.WHITE);
                        }
                        questionIndex++;
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);
                    }
                }
            });

            answer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer4.getText() == mAnswer) {
                        answer4.setBackgroundResource(R.drawable.for_bt_correct);
                        answer4.setTextColor(Color.WHITE);
                        mScore++;
                        questionIndex++;
                        score.setText("Ponto:" + mScore);
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);
                    } else {
                        answer4.setBackgroundResource(R.drawable.for_bt_incorrect);
                        answer4.setTextColor(Color.WHITE);
                        if (answer2.getText() == mAnswer) {
                            answer2.setBackgroundResource(R.drawable.for_bt_correct);
                            answer2.setTextColor(Color.WHITE);
                        } else if (answer3.getText() == mAnswer) {
                            answer3.setBackgroundResource(R.drawable.for_bt_correct);
                            answer3.setTextColor(Color.WHITE);
                        } else {
                            answer1.setBackgroundResource(R.drawable.for_bt_correct);
                            answer1.setTextColor(Color.WHITE);
                        }
                        questionIndex++;
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);
                    }
                }
            });

        } else {

            setContentView(R.layout.for_activity_formula2);

            int r = 0;
            answer1 = (Button) findViewById(R.id.answer1);
            answer2 = (Button) findViewById(R.id.answer2);
            answer3 = (Button) findViewById(R.id.answer3);
            answer4 = (Button) findViewById(R.id.answer4);
            nextButton = (Button) findViewById(R.id.nextButton);
            dynamicImage = (ImageView) findViewById(R.id.image_view_1);
            question = (TextView) findViewById(R.id.question);
            score = (TextView) findViewById(R.id.score);
            juniorTextView = (TextView) findViewById(R.id.juniorTextView);
            score.setText("Ponto: " + mScore);

            updateQuestion(questionIndex, QuestionArray);

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateQuestion(questionIndex, QuestionArray);
                }
            });

            answer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer1.getText() == mAnswer) {
                        answer1.setBackgroundResource(R.drawable.for_bt_correct);
                        answer1.setTextColor(Color.WHITE);
                        mScore++;
                        questionIndex++;
                        score.setText("Ponto:" + mScore);
                        setAllButtonsEnabled(false);

                        //updateQuestion(questionIndex);
                        nextButton.setVisibility(View.VISIBLE);
                    } else {
                        answer1.setBackgroundResource(R.drawable.for_bt_incorrect);
                        answer1.setTextColor(Color.WHITE);
                        if (answer2.getText() == mAnswer) {
                            answer2.setBackgroundResource(R.drawable.for_bt_correct);
                            answer2.setTextColor(Color.WHITE);
                        } else if (answer3.getText() == mAnswer) {
                            answer3.setBackgroundResource(R.drawable.for_bt_correct);
                            answer3.setTextColor(Color.WHITE);
                        } else {
                            answer4.setBackgroundResource(R.drawable.for_bt_correct);
                            answer4.setTextColor(Color.WHITE);
                        }
                        questionIndex++;
                        setAllButtonsEnabled(false);
                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);


                    }

                }
            });

            answer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer2.getText() == mAnswer) {
                        answer2.setBackgroundResource(R.drawable.for_bt_correct);
                        answer2.setTextColor(Color.WHITE);
                        mScore++;
                        questionIndex++;

                        score.setText("Ponto:" + mScore);
                        //updateQuestion(questionIndex);
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                    } else {
                        answer2.setBackgroundResource(R.drawable.for_bt_incorrect);
                        answer2.setTextColor(Color.WHITE);
                        if (answer1.getText() == mAnswer) {
                            answer1.setBackgroundResource(R.drawable.for_bt_correct);
                            answer1.setTextColor(Color.WHITE);
                        } else if (answer3.getText() == mAnswer) {
                            answer3.setBackgroundResource(R.drawable.for_bt_correct);
                            answer3.setTextColor(Color.WHITE);
                        } else {
                            answer4.setBackgroundResource(R.drawable.for_bt_correct);
                            answer4.setTextColor(Color.WHITE);
                        }
                        questionIndex++;
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);
                    }
                }
            });

            answer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer3.getText() == mAnswer) {
                        answer3.setBackgroundResource(R.drawable.for_bt_correct);
                        answer3.setTextColor(Color.WHITE);
                        mScore++;
                        questionIndex++;
                        score.setText("Ponto:" + mScore);
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);
                    } else {
                        answer3.setBackgroundResource(R.drawable.for_bt_incorrect);
                        answer3.setTextColor(Color.WHITE);
                        if (answer2.getText() == mAnswer) {
                            answer2.setBackgroundResource(R.drawable.for_bt_correct);
                            answer2.setTextColor(Color.WHITE);
                        } else if (answer1.getText() == mAnswer) {
                            answer1.setBackgroundResource(R.drawable.for_bt_correct);
                            answer1.setTextColor(Color.WHITE);
                        } else {
                            answer4.setBackgroundResource(R.drawable.for_bt_correct);
                            answer4.setTextColor(Color.WHITE);
                        }
                        questionIndex++;
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);
                    }
                }
            });

            answer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer4.getText() == mAnswer) {
                        answer4.setBackgroundResource(R.drawable.for_bt_correct);
                        answer4.setTextColor(Color.WHITE);
                        mScore++;
                        questionIndex++;
                        score.setText("Ponto:" + mScore);
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);
                    } else {
                        answer4.setBackgroundResource(R.drawable.for_bt_incorrect);
                        answer4.setTextColor(Color.WHITE);
                        if (answer2.getText() == mAnswer) {
                            answer2.setBackgroundResource(R.drawable.for_bt_correct);
                            answer2.setTextColor(Color.WHITE);
                        } else if (answer3.getText() == mAnswer) {
                            answer3.setBackgroundResource(R.drawable.for_bt_correct);
                            answer3.setTextColor(Color.WHITE);
                        } else {
                            answer1.setBackgroundResource(R.drawable.for_bt_correct);
                            answer1.setTextColor(Color.WHITE);
                        }
                        questionIndex++;
                        setAllButtonsEnabled(false);

                        nextButton.setVisibility(View.VISIBLE);
                        //updateQuestion(questionIndex);
                    }
                }
            });

        }

    }
}