package application.viope.math.combinedapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.style.LineHeightSpan;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.viope.math.combinedapp.bean.ln7_Answer;
import application.viope.math.combinedapp.bean.ln7_Exercise;


public class ln7_DatabaseHelper extends SQLiteOpenHelper{

    //Database
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "exerciseManager";

    //ln7_Exercise table
    private static final String TABLE_EXERCISE = "exercise";
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_CORRECT = "correct";
    private static final String KEY_TYPE = "type";

    //ln7_Answer table
    private static final String TABLE_ANSWER = "answer";
    private static final String KEY_EXERCISE_ID = "exercise_id";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_ISCORRECT = "isCorrect";


    public ln7_DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //CREATE TABLES
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXERCISE_TABLE = "CREATE TABLE " +  TABLE_EXERCISE + " ("
                + KEY_ID + " INTEGER PRIMARY KEY UNIQUE, "
                + KEY_QUESTION + " TEXT UNIQUE, "
                + KEY_CORRECT + " TEXT, "
                + KEY_TYPE + " TEXT,"
                + "UNIQUE (" + KEY_QUESTION + ") ON CONFLICT IGNORE"
                + ");";

        String CREATE_ANSWER_TABLE = "CREATE TABLE " + TABLE_ANSWER + " ("
                + KEY_EXERCISE_ID + " INTEGER PRIMARY KEY UNIQUE, "
                + KEY_ANSWER + " TEXT, "
                + KEY_ISCORRECT + " SMALLINT DEFAULT 0); ";

        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_ANSWER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        onCreate(db);
    }

    public void setExercisesFirebase(List<ln7_Exercise> list) {
        for (int i = 0; i < list.size() - 1; i ++) {
            ln7_Exercise exercise = list.get(i);
            addExercise(exercise);
        }
    }

    //INSERT VALUES to TABLES
    void addExercise(ln7_Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, exercise.getQuestion());
        values.put(KEY_CORRECT, exercise.getCorrect());
        values.put(KEY_TYPE, exercise.getType());

        db.insertWithOnConflict(TABLE_EXERCISE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    void addAnswer(ln7_Answer answer){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EXERCISE_ID, answer.getExercise_id());
        values.put(KEY_ANSWER, answer.getAnswer());
        values.put(KEY_ISCORRECT, answer.isCorrect());

        db.insertWithOnConflict(TABLE_ANSWER, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    //READ TABLES
    ln7_Exercise getExercise(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXERCISE, new String[]{KEY_ID, KEY_QUESTION, KEY_CORRECT, KEY_TYPE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        ln7_Exercise exercise = new ln7_Exercise(cursor.getString(0), cursor.getString(1), cursor.getString(2));

        return exercise;
    }

    ln7_Answer getAnswer(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ANSWER, new String[]{KEY_EXERCISE_ID, KEY_ANSWER, KEY_ISCORRECT}, KEY_EXERCISE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        ln7_Answer answer = new ln7_Answer(cursor.getInt(0), cursor.getString(1), (cursor.getInt(2) == 1));

        return answer;
    }

    public List<ln7_Exercise> getAllExercisesFracs(){
        List<ln7_Exercise> exerciseList = new ArrayList<ln7_Exercise>();

        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE + " WHERE \"type\"  = 'frac'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db. rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                ln7_Exercise exercise = new ln7_Exercise();
                exercise.setId(Integer.parseInt(cursor.getString(0)));
                exercise.setQuestion(cursor.getString(1));
                exercise.setCorrect(cursor.getString(2));
                exercise.setType(cursor.getString(3));

                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }

        return exerciseList;
    }

    public List<ln7_Exercise> getAllExercisesRoots(){
        List<ln7_Exercise> exerciseList = new ArrayList<ln7_Exercise>();

        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE + " WHERE \"type\"  = 'root'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db. rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                ln7_Exercise exercise = new ln7_Exercise();
                exercise.setId(Integer.parseInt(cursor.getString(0)));
                exercise.setQuestion(cursor.getString(1));
                exercise.setCorrect(cursor.getString(2));
                exercise.setType(cursor.getString(3));

                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }

        return exerciseList;
    }

    public List<ln7_Exercise> getAllExercises(){
        List<ln7_Exercise> exerciseList = new ArrayList<ln7_Exercise>();

        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db. rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                ln7_Exercise exercise = new ln7_Exercise();
                exercise.setId(Integer.parseInt(cursor.getString(0)));
                exercise.setQuestion(cursor.getString(1));
                exercise.setCorrect(cursor.getString(2));
                exercise.setType(cursor.getString(3));

                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }

        return exerciseList;
    }

    public List<ln7_Answer> getAllAnswers() {
        List<ln7_Answer> answerList = new ArrayList<ln7_Answer>();

        String selectQuery = "SELECT * FROM " + TABLE_ANSWER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db. rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                ln7_Answer answer = new ln7_Answer();
                answer.setExercise_id(cursor.getInt(0));
                answer.setAnswer(cursor.getString(1));
                answer.setCorrect(cursor.getInt(2) == 1);

                answerList.add(answer);
            } while (cursor.moveToNext());
        }

        return answerList;
    }

    //UPDATE VALUES on TABLES
    public int updateExercise(ln7_Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, exercise.getQuestion());
        values.put(KEY_CORRECT, exercise.getCorrect());
        values.put(KEY_TYPE, exercise.getType());

        return db.update(TABLE_EXERCISE, values, KEY_ID + "=?",
                new String[]{String.valueOf(exercise.getId())});
    }

    public int updateAnswer(ln7_Answer answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EXERCISE_ID, answer.getExercise_id());
        values.put(KEY_ANSWER, answer.getAnswer());
        values.put(KEY_ISCORRECT, answer.isCorrect());

        return db.update(TABLE_ANSWER, values, KEY_ID + "=?",
                new String[]{String.valueOf(answer.getExercise_id())});
    }

    //DELETE TUPLES from TABLE
    public void deleteExercise(ln7_Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXERCISE, KEY_ID + "=?",
                new String[]{String.valueOf(exercise.getId())});
        db.close();
    }

    public void deleteAnswer(ln7_Answer answer){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ANSWER, KEY_ID + "=?",
                new String[]{String.valueOf(answer.getExercise_id())});
        db.close();
    }

    //COUNT OF TUPLES on TABLE
    public int getExerciseCount(){
        String countQuery = "SELECT * FROM " + TABLE_EXERCISE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int getAnswerCount(){
        String countQuery = "SELECT * FROM " + TABLE_ANSWER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int getCountOf(String TABLE_NAME){
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}

