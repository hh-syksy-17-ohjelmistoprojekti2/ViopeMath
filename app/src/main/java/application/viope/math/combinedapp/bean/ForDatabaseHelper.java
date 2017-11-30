package application.viope.math.combinedapp.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class ForDatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_FORMULAS= "Formulas.db";
    public static final String QUESTIONS="ForQuestions";
    public static final String QUESTION_ID="q_id";
    public static final String QUESTION="question";
    public static final String OFFEREDANSWER="offeredanswer";
    public static final String ANSWER="answer";

    public static final String OPTIONS="options_table";
    public static final String O_QUESTION_ID="question_ID";
    public static final String O_1="option1";
    public static final String O_2="option2";
    public static final String O_3="option3";
    public static final String O_4="option4";

    public static final String RESULTS="Results_table";
    public static final String R_QUESTIONS_ID="questions_ID";
    public static final String R_CORRECT="correct";

    public static final String QUESTION_TYPE="type_table";
    public static final String QT_ID= "question_type_id";
    public static final String QT_TYPE= "question_type";





    public ForDatabaseHelper(Context context) {
        super(context, DATABASE_FORMULAS, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS " + QUESTIONS +" (q_id INTEGER, question TEXT, answer TEXT, offeredanswer TEXT)");
        //  db.execSQL("create table IF NOT EXISTS " + OPTIONS +" (question_ID INTEGER, option1 INTEGER, option2 INTEGER, option3 INTEGER, option4 INTEGER)");
        db.execSQL("create table IF NOT EXISTS " + RESULTS +" (questions_ID INTEGER, correct INTEGER)");
        //db.execSQL("create table IF NOT EXISTS " + QUESTION_TYPE +" (question_type_id INTEGER, question_type TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  db.execSQL("DROP TABLE IF EXISTS "+ QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS "+ OPTIONS);
        db.execSQL("DROP TABLE IF EXISTS "+ RESULTS);
        db.execSQL("DROP TABLE IF EXISTS "+ QUESTION_TYPE);


        onCreate(db);
    }
    public boolean insertRecord(String QId, String Question, String answer, String offeredanswer){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION_ID, QId);
        contentValues.put(QUESTION, Question);
        contentValues.put(ANSWER, answer);
        contentValues.put(OFFEREDANSWER, offeredanswer);
        db.insert(QUESTIONS, null, contentValues);
        return true;
    }

    public void InsertIntoDB(String QId, String question,String answer, String offeredanswer) {
        insertRecord(QId, question, answer, offeredanswer);
        final Cursor cursor = getQuestions();
    }

    public Cursor getQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + QUESTIONS, null );
        // String test = res.getString(res.getColumnIndex("question"));
        //String test2 = res.getString(res.getColumnIndex("answer"));
        return res;
    }

    public boolean insertData(int correct, int q_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(R_CORRECT, correct);
        long result = db.insert(RESULTS,null ,contentValues);
        if(result == -1)
            return false;
        else
            return  true;
    }


}