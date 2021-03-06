package application.viope.math.combinedapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import application.viope.math.combinedapp.bean.ConsAnswer;
import application.viope.math.combinedapp.bean.ConsQuestion;
import application.viope.math.combinedapp.bean.EquAnswerstatus;
import application.viope.math.combinedapp.bean.EquQuestion;
import application.viope.math.combinedapp.bean.ln7_Answer;
import application.viope.math.combinedapp.bean.ln7_Exercise;

public class EquDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "EquDatabaseHelper";

    //Table names
    private static final String DATABASE_NAME = "LocalDB";
    private Cursor data;
    private static final String TABLE_USER = "equUser";
    private static final String TABLE_ANSWERSTATUS = "equAnswerstatus";
    private static final String TABLE_QUESTION = "equQuestion";
    private static final String TABLE_PHASE = "equPhase";

    //Common column names
    private static final String KEY_USERID = "userid";
    private static final String KEY_QUESTIONID = "questionid";

    //EquUser table columns
    private static final String KEY_USERNAME = "username";

    //EquAnswerstatus table columns
    private static final String KEY_ANSWERSTATUS = "equAnswerstatus";

    //EquQuestion table columns
    private static final String KEY_QUESTION = "equQuestion";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_QUESTIONORDER = "questionorder";

    //EquPhase table columns
    private static final String KEY_PHASEID = "phaseid";
    private static final String KEY_PHASE = "phase";

    //Table create statements
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_USERID + " TEXT," + KEY_USERNAME
            + " TEXT)";

    private static final String CREATE_TABLE_ANSWERSTATUS = "CREATE TABLE "
            + TABLE_ANSWERSTATUS + "(" + KEY_QUESTIONID + " TEXT," + KEY_ANSWERSTATUS + " INTEGER, PRIMARY KEY("+ KEY_QUESTIONID + "), "
            + "FOREIGN KEY (" + KEY_QUESTIONID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTIONID + "))";
    //+ "FOREIGN KEY (" + KEY_USERID + ") REFERENCES " + TABLE_USER + "(" + KEY_USERID + "))";

    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE "
            + TABLE_QUESTION + "(" + KEY_QUESTIONID + " TEXT PRIMARY KEY," + KEY_QUESTION
            + " TEXT," + KEY_ANSWER + " INTEGER," + KEY_QUESTIONORDER + " INTEGER)";

    private static final String CREATE_TABLE_PHASE = "CREATE TABLE "
            + TABLE_PHASE + "(" + KEY_PHASEID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_QUESTIONID
            + " TEXT," + KEY_PHASE + " TEXT, "
            + "FOREIGN KEY (" + KEY_QUESTIONID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTIONID + "))";
    //+ "FOREIGN KEY (" + KEY_USERID + ") REFERENCES " + TABLE_USER + "(" + KEY_USERID + "))";


    // ----------------------- CONSUMER MATHAPP SQLITE DATABASES --------------------------------
    private static final String TABLE_CONS_ANSWERS = "ConsAnswers";
    private static final String TABLE_CONS_QUESTIONS = "ConsQuestions";
    private static final String KEY_CONS_ID = "id";
    private static final String KEY_CONS_ANSWER = "answer";
    private static final String KEY_CONS_FORMULA = "formula";
    private static final String KEY_CONS_QUESTIONNUMBER = "questionnumber";
    private static final String KEY_CONS_QUESTION = "question";
    private static final String KEY_CONS_R_ANSWER = "r_answer";
    private static final String KEY_CONS_HINT = "hint";

    String CREATE_TABLE_CONS_ANSWER = "CREATE TABLE " + TABLE_CONS_ANSWERS + "("
            + KEY_CONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CONS_ANSWER + " TEXT,"
            + KEY_CONS_FORMULA + " TEXT," + KEY_CONS_QUESTIONNUMBER + " INTEGER" +")";

    String CREATE_TABLE_CONS_QUESTIONS = "CREATE TABLE " + TABLE_CONS_QUESTIONS + "("
            + KEY_CONS_ID + " INTEGER PRIMARY KEY," + KEY_CONS_QUESTION + " TEXT,"
            + KEY_CONS_R_ANSWER + " REAL," + KEY_CONS_HINT + " TEXT" + ")";

    //-------------------------------------------------------------------------------------------

    //------------------------------fracs and roots----------------------------------------------
    //ln7_Exercise table
    private static final String TABLE_FR_EXERCISE = "exercise";
    private static final String KEY_FR_ID = "id";
    private static final String KEY_FR_QUESTION = "question";
    private static final String KEY_FR_CORRECT = "correct";
    private static final String KEY_FR_TYPE = "type";

    //ln7_Answer table
    private static final String TABLE_FR_ANSWER = "answer";
    private static final String KEY_FR_EXERCISE_ID = "exercise_id";
    private static final String KEY_FR_ANSWER = "answer";
    private static final String KEY_FR_ISCORRECT = "isCorrect";

    private String CREATE_FR_EXERCISE_TABLE = "CREATE TABLE " +  TABLE_FR_EXERCISE + " ("
            + KEY_FR_ID + " INTEGER PRIMARY KEY UNIQUE, "
            + KEY_FR_QUESTION + " TEXT UNIQUE, "
            + KEY_FR_CORRECT + " TEXT, "
            + KEY_FR_TYPE + " TEXT,"
            + "UNIQUE (" + KEY_FR_QUESTION + ") ON CONFLICT IGNORE"
            + ");";

    private String CREATE_FR_ANSWER_TABLE = "CREATE TABLE " + TABLE_FR_ANSWER + " ("
            + KEY_FR_EXERCISE_ID + " INTEGER PRIMARY KEY UNIQUE, "
            + KEY_FR_ANSWER + " TEXT, "
            + KEY_FR_ISCORRECT + " SMALLINT DEFAULT 0); ";
    //-------------------------------------------------------------------------------------------

    private SQLiteDatabase db;
    private Cursor cursor;
    private EquQuestion equQuestion;
    private EquAnswerstatus equAnswerstatus;
    private int id;

    public EquDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_ANSWERSTATUS);
        db.execSQL(CREATE_TABLE_PHASE);
        db.execSQL(CREATE_TABLE_CONS_ANSWER);
        db.execSQL(CREATE_TABLE_CONS_QUESTIONS);
        db.execSQL(CREATE_FR_EXERCISE_TABLE);
        db.execSQL(CREATE_FR_ANSWER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERSTATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONS_ANSWERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONS_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FR_ANSWER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FR_EXERCISE);

        onCreate(db);
    }

    public boolean addData(String inputString, String questionId) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PHASE, inputString);
        contentValues.put(KEY_QUESTIONID, questionId);

        Log.d(TAG, "addData: Adding " + inputString + " to " + TABLE_PHASE);

        long result = db.insert(TABLE_PHASE, null, contentValues);

        //if data is inserted incorrectly, it will return -1
        if (result == -1) {
            Log.d(TAG, "DB insert failed");
            return false;
        } else {
            Log.d(TAG, "DB insert worked");
            return true;
        }
    }

    public boolean addTestDataForShowingQuestion(String question, String answer, String id, int order) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_QUESTION, question);
        contentValues.put(KEY_QUESTIONID, id);
        contentValues.put(KEY_ANSWER, answer);
        contentValues.put(KEY_QUESTIONORDER, order);

        Log.d(TAG, "addTestingData: Adding " + question + " to " + TABLE_QUESTION);

        long result = db.insert(TABLE_QUESTION, null, contentValues);

        if (result == -1){
            Log.d(TAG, "insert failed");
            return false;
        }else {
            Log.d(TAG, "insert successful");
            return true;
        }

    }

    public void checkUsername(){
        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT " + KEY_USERID + " FROM " + TABLE_USER + "", null);
        if (cursor!=null && cursor.getCount() < 1){
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " +TABLE_USER+ " ( " + KEY_USERID + ", " + KEY_USERNAME + ") VALUES ('unknown', '" + EquNickname.getName() + "')");
        }
    }
    public boolean checkIfUseridExists(){
        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + "", null);


        if (cursor !=null && cursor.getCount() > 0){
            //userid ei ole lokaalisti
            //return true;
            if (cursor.moveToFirst()) {
                return !cursor.getString(cursor.getColumnIndex(KEY_USERID)).equals("unknown");
            }
        }/*else {
            //userid on lokaalisti
            return false;
        }*/
        return false;
    }

    public void updateUserid(String userid){ //update userid
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " +TABLE_USER+ " SET " + KEY_USERID + " = '" + userid + "'");
    }
    public String getUserid(){
        db = this.getReadableDatabase();
        cursor = db.rawQuery("SELECT " + KEY_USERID + " FROM " + TABLE_USER + "", null);
        String userid = "";

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_USERID + " FROM " + TABLE_USER;
        data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            userid = data.getString(data.getColumnIndex(KEY_USERID));
        }
        return userid;
    }
    public void addStatus(String questionid, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+ TABLE_ANSWERSTATUS + " (" + KEY_QUESTIONID + ", " + KEY_ANSWERSTATUS + ")Values ('" + questionid + "', '"+ status +"')");
    }

    public int getQuestionStatus(int questionOrder) {
        db = this.getReadableDatabase();
        //db.execSQL("SELECT * FROM " + TABLE_ANSWERSTATUS + );
        return 0;
    }

    public void updateStatus(int status, String id, int order) {
        db = this.getWritableDatabase();
        /*ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ANSWERSTATUS, status);
        Log.d(TAG, "addStatus: updating " + status + " " + id
                + " " + order + " to " + TABLE_ANSWERSTATUS);*/



        //long result = db.update(TABLE_ANSWERSTATUS, contentValues, "questionorder="+order, null);
        db.execSQL("UPDATE " + TABLE_ANSWERSTATUS + " SET " + KEY_ANSWERSTATUS + "=" + status + " WHERE " + TABLE_ANSWERSTATUS + "." + KEY_QUESTIONID + " IN (SELECT " + KEY_QUESTIONID + " FROM " + TABLE_QUESTION + " WHERE " + KEY_QUESTIONORDER + " = " + order + ")");
        /*
        if (result == -1){
            Log.d(TAG, "insert failed");
            return false;
        }else {
            Log.d(TAG, "insert successful");
            return true;
        }*/
    }

    public EquQuestion findFirstQuestion() {
        //String SQLQuery = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + KEY_QUESTIONID + "=" + id;
        //Log.d(TAG, SQLQuery);
        id = 1;
        db = this.getReadableDatabase();
        cursor = db.query(TABLE_QUESTION, new String[] {KEY_QUESTIONID, KEY_QUESTION,
                        KEY_ANSWER, KEY_QUESTIONORDER},
                KEY_QUESTIONORDER + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor !=null){
            Log.d(TAG, "cursor not null");
            cursor.moveToFirst();
        }
        equQuestion = new EquQuestion(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        Log.d(TAG, equQuestion.getQuestionText());
        return equQuestion;
    }

    public EquQuestion findQuestionById(int number) {
        int questionId = number;
        id = questionId;
        Log.d(TAG, "FIND QUESTION BY ID => questionId = " + questionId + ", id = " + id);
        db = this.getReadableDatabase();
        cursor = db.query(TABLE_QUESTION, new String[] {KEY_QUESTIONID, KEY_QUESTION,
                        KEY_ANSWER, KEY_QUESTIONORDER},
                KEY_QUESTIONORDER + "=?", new String[] {String.valueOf(questionId)}, null, null, null, null);
        if (cursor !=null){
            Log.d(TAG, "cursor not null");
            cursor.moveToFirst();
        }
        equQuestion = new EquQuestion(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        Log.d(TAG, equQuestion.getQuestionText());
        return equQuestion;
    }

    public EquAnswerstatus getAnswerStatus(int questionOrder) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ANSWERSTATUS+ " A WHERE A."
                + KEY_QUESTIONID + " IN (SELECT " + KEY_QUESTIONID + " FROM " + TABLE_QUESTION +" WHERE "+KEY_QUESTIONORDER+"="+ questionOrder+")";
        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        equAnswerstatus = new EquAnswerstatus(cursor.getString(0), cursor.getInt(1));
        return equAnswerstatus;
    }

    public ArrayList toFirebaseFromLocalAnswerstatus(){
        ArrayList<EquAnswerstatus> AnswerstatusList = new ArrayList<EquAnswerstatus>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ANSWERSTATUS;
        cursor = db.rawQuery(query, null);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            System.out.println(cursor.getString(0)+ " " + cursor.getInt(1));
            equAnswerstatus = new EquAnswerstatus(cursor.getString(0), cursor.getInt(1));
            AnswerstatusList.add(equAnswerstatus);
        }
        return AnswerstatusList;
    }

    public EquQuestion findNextQuestion() {

        id++;
        db = this.getReadableDatabase();
        cursor = db.query(TABLE_QUESTION, new String[] {KEY_QUESTIONID, KEY_QUESTION,
                        KEY_ANSWER, KEY_QUESTIONORDER},
                KEY_QUESTIONORDER + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor !=null){
            Log.d(TAG, "cursor not null");
            cursor.moveToFirst();
        }
        equQuestion = new EquQuestion(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        Log.d(TAG, equQuestion.getQuestionText());
        return equQuestion;
    }

    public EquQuestion findPreviousQuestion() {
        Log.d(TAG, " " + id);
        if (id == 1){
            return null;
        }
        id--;
        db = this.getReadableDatabase();
        cursor = db.query(TABLE_QUESTION, new String[] {KEY_QUESTIONID, KEY_QUESTION,
                        KEY_ANSWER, KEY_QUESTIONORDER},
                KEY_QUESTIONORDER + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor !=null){
            Log.d(TAG, "cursor not null");
            cursor.moveToFirst();
        }
        equQuestion = new EquQuestion(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        Log.d(TAG, equQuestion.getQuestionText());
        return equQuestion;
    }

    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_PHASE + " WHERE " + KEY_PHASEID + " = (SELECT MAX(phaseid) FROM " + TABLE_PHASE + ")";
        Log.d(TAG, "deleteData: query: " + query);
        Log.d(TAG, "deleteData: Deleting from database");
        db.execSQL(query);
    }

    public Cursor getPhases(String questionId){
        Log.d(TAG, "START OF GETPHASES METHOD");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_PHASE + " FROM " + TABLE_PHASE +
                " WHERE " + KEY_QUESTIONID + "='" + questionId + "'";
        data = db.rawQuery(query, null);
        return data;
    }

    public long getQuestionCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count  = DatabaseUtils.queryNumEntries(db, TABLE_QUESTION);
        return count;
    }

    //return the data from db
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PHASE;
        data = db.rawQuery(query, null);
        return data;
    }

    public String getPost() {
        String str = "";

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_USERNAME + " FROM " + TABLE_USER;
        data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            str = data.getString(data.getColumnIndex(KEY_USERNAME));
        }
        return str;
    }

    public  void toLocalFromFirebase(String questionid, String question, String answer, int questionorder){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO "+ TABLE_QUESTION +" (" + KEY_QUESTIONID + ", " + KEY_QUESTION +", " + KEY_ANSWER + ", " + KEY_QUESTIONORDER + ")Values ('" + questionid + "', '" + question + "', '"+ answer + "', " + questionorder +")");
            //db.execSQL("INSERT INTO "+ TABLE_ANSWERSTATUS + " (" + KEY_QUESTIONID + ", " + KEY_ANSWERSTATUS + ")Values ('" + questionid + "', '"+ status +"')");
        }
        catch(Exception e){
            System.out.println("dbHelper error:" + e);
        }
    }

    //--------------------------- Fracs and roots ----------------------------------------------
    //INSERT VALUES to TABLES
    public void setExercisesFirebase_fr(List<ln7_Exercise> list) {
        for (int i = 0; i < list.size(); i ++) {
            ln7_Exercise exercise = list.get(i);
            addExercise_fr(exercise);
        }
    }

    public void addExercise_fr(ln7_Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FR_QUESTION, exercise.getQuestion());
        values.put(KEY_FR_CORRECT, exercise.getCorrect());
        values.put(KEY_FR_TYPE, exercise.getType());

        db.insertWithOnConflict(TABLE_FR_EXERCISE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void addAnswer_fr(ln7_Answer answer){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FR_EXERCISE_ID, answer.getExercise_id());
        values.put(KEY_FR_ANSWER, answer.getAnswer());
        values.put(KEY_FR_ISCORRECT, answer.isCorrect());

        db.insertWithOnConflict(TABLE_FR_ANSWER, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    //READ TABLES
    public ln7_Exercise getExercise_fr(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FR_EXERCISE, new String[]{KEY_FR_ID, KEY_FR_QUESTION, KEY_FR_CORRECT, KEY_FR_TYPE}, KEY_FR_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        ln7_Exercise exercise = new ln7_Exercise(cursor.getString(0), cursor.getString(1), cursor.getString(2));

        return exercise;
    }

    public ln7_Answer getAnswer_fr(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FR_ANSWER, new String[]{KEY_FR_EXERCISE_ID, KEY_FR_ANSWER, KEY_FR_ISCORRECT}, KEY_FR_EXERCISE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        ln7_Answer answer = new ln7_Answer(cursor.getInt(0), cursor.getString(1), (cursor.getInt(2) == 1));

        return answer;
    }

    public List<ln7_Exercise> getAllExercisesFracs_fr(){
        List<ln7_Exercise> exerciseList = new ArrayList<ln7_Exercise>();

        String selectQuery = "SELECT * FROM " + TABLE_FR_EXERCISE + " WHERE \"type\"  = 'frac'";

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

    public List<ln7_Exercise> getAllExercisesRoots_fr(){
        List<ln7_Exercise> exerciseList = new ArrayList<ln7_Exercise>();

        String selectQuery = "SELECT * FROM " + TABLE_FR_EXERCISE + " WHERE \"type\"  = 'root'";

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

    public List<ln7_Exercise> getAllExercises_fr(){
        List<ln7_Exercise> exerciseList = new ArrayList<ln7_Exercise>();

        String selectQuery = "SELECT * FROM " + TABLE_FR_EXERCISE;

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

    public List<ln7_Answer> getAllAnswers_fr() {
        List<ln7_Answer> answerList = new ArrayList<ln7_Answer>();

        String selectQuery = "SELECT * FROM " + TABLE_FR_ANSWER;

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
    public int updateExercise_fr(ln7_Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FR_QUESTION, exercise.getQuestion());
        values.put(KEY_FR_CORRECT, exercise.getCorrect());
        values.put(KEY_FR_TYPE, exercise.getType());

        return db.update(TABLE_FR_EXERCISE, values, KEY_FR_ID + "=?",
                new String[]{String.valueOf(exercise.getId())});
    }

    public int updateAnswer_fr(ln7_Answer answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FR_EXERCISE_ID, answer.getExercise_id());
        values.put(KEY_FR_ANSWER, answer.getAnswer());
        values.put(KEY_FR_ISCORRECT, answer.isCorrect());

        return db.update(TABLE_FR_ANSWER, values, KEY_FR_ID + "=?",
                new String[]{String.valueOf(answer.getExercise_id())});
    }

    //DELETE TUPLES from TABLE
    public void deleteExercise_fr(ln7_Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FR_EXERCISE, KEY_FR_ID + "=?",
                new String[]{String.valueOf(exercise.getId())});
        db.close();
    }

    public void deleteAnswer_fr(ln7_Answer answer){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FR_ANSWER, KEY_FR_ID + "=?",
                new String[]{String.valueOf(answer.getExercise_id())});
        db.close();
    }


    //--------------------------- CONSUMER MATHAPP METHODS --------------------------------------

    public boolean ConsInsertAnswers (double answer, String formula, int questionnumber) {
        //DESC Table answers
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("questionnumber", questionnumber);
        contentValues.put("answer", answer);
        contentValues.put("formula", formula);
        db.insert("ConsAnswers", null, contentValues);

        return true;
    }

    public boolean ConsInsertQuestion (int id, String question, double r_answer, String hint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("question", question);
        contentValues.put("r_answer", r_answer);
        contentValues.put("hint", hint);
        db.insert("ConsQuestions", null, contentValues);
        return true;
    }

    public ArrayList<ConsQuestion> ConsGetAllQuestions() {

        ArrayList<ConsQuestion> questionList = new ArrayList<ConsQuestion>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONS_QUESTIONS;

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

    public ArrayList<ConsAnswer> ConsGetAllElements() {

        ArrayList<ConsAnswer> answerList = new ArrayList<ConsAnswer>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONS_ANSWERS;

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
        Cursor cursor = db.query(TABLE_CONS_ANSWERS, allColumns, KEY_CONS_ANSWER + " = " + rightAnswer, null, null, null ,null);
        cursor.moveToFirst();
        boolean retBool= false;
        if (!cursor.isAfterLast()){
            retBool = true;
        }
        return retBool;
    }



}
