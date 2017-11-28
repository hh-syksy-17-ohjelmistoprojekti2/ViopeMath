package application.viope.math.combinedapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import application.viope.math.combinedapp.bean.ConsAnswer;
import application.viope.math.combinedapp.bean.ConsQuestion;

/**
 * Created by tomisalin on 10/23/17.
 */


public class ConsDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "answers";
    private static final String TABLE_ANSWERS = "ConsAnswers";
    private static final String TABLE_QUESTIONS = "ConsQuestions";
    private static final String KEY_ID = "id";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_FORMULA = "formula";
    private static final String KEY_QUESTIONNUMBER = "questionnumber";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_R_ANSWER = "r_answer";
    private static final String KEY_HINT = "hint";



    String CREATE_TABLE_ANSWER = "CREATE TABLE " + TABLE_ANSWERS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ANSWER + " TEXT,"
            + KEY_FORMULA + " TEXT," + KEY_QUESTIONNUMBER + " INTEGER" +")";

    String CREATE_TABLE_QUESTIONS = "CREATE TABLE " + TABLE_QUESTIONS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_QUESTION + " TEXT,"
            + KEY_R_ANSWER + " REAL," + KEY_HINT + " TEXT" + ")";



    public ConsDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_ANSWER);
        db.execSQL(CREATE_TABLE_QUESTIONS);
        System.out.println(db.rawQuery("SELECT * FROM answers;",null));
        System.out.println();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    public boolean insertAnswers (double answer, String formula, int questionnumber) {
        //DESC Table answers
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("questionnumber", questionnumber);
        contentValues.put("answer", answer);
        contentValues.put("formula", formula);
        db.insert("answers", null, contentValues);
        System.out.println(db.rawQuery("select * from answers",null));
        return true;
    }

    public boolean insertQuestion (int id, String question, double r_answer, String hint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("question", question);
        contentValues.put("r_answer", r_answer);
        contentValues.put("hint", hint);
        db.insert("questions", null, contentValues);
        return true;
    }

    public ArrayList<ConsQuestion> getAllQuestions() {

        ArrayList<ConsQuestion> questionList = new ArrayList<ConsQuestion>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS;

        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        ConsQuestion question = new ConsQuestion();
                        question.setId(Integer.parseInt(cursor.getString(0)));
                        question.setQuestion(cursor.getString(1));
                        question.setRightAnswer(cursor.getDouble(2));
                        question.setHint(cursor.getString(3));
                        questionList.add(question);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return questionList;

    }

    public ArrayList<ConsAnswer> getAllElements() {

        ArrayList<ConsAnswer> answerList = new ArrayList<ConsAnswer>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ANSWERS;

        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        ConsAnswer answer = new ConsAnswer();
                        answer.setId(Integer.parseInt(cursor.getString(0)));
                        answer.setAnswer(cursor.getDouble(1));
                        answer.setFormula(cursor.getString(2));
                        answer.setQuestionnumber(cursor.getInt(3));
                        answerList.add(answer);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return answerList;
    }

    public boolean ifExists(double rightAnswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] allColumns = {KEY_ANSWER};
        Cursor cursor = db.query(TABLE_ANSWERS, allColumns, KEY_ANSWER + " = " + rightAnswer, null, null, null ,null);
        cursor.moveToFirst();
        boolean retBool= false;
        if (!cursor.isAfterLast()){
            retBool = true;
        }
        return retBool;
    }
}
