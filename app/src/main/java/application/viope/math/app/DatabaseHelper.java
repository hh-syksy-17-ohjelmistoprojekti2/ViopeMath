package application.viope.math.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import application.viope.math.app.bean.Answerstatus;
import application.viope.math.app.bean.Question;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    //Table names
    private static final String DATABASE_NAME = "LocalDB";
    private Cursor data;
    private static final String TABLE_USER = "user";
    private static final String TABLE_ANSWERSTATUS = "answerstatus";
    private static final String TABLE_QUESTION = "question";
    private static final String TABLE_PHASE = "phases";

    //Common column names
    private static final String KEY_USERID = "userid";
    private static final String KEY_QUESTIONID = "questionid";

    //User table columns
    private static final String KEY_USERNAME = "username";

    //Answerstatus table columns
    private static final String KEY_ANSWERSTATUS = "answerstatus";

    //Question table columns
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_QUESTIONORDER = "questionorder";

    //Phase table columns
    private static final String KEY_PHASEID = "phaseid";
    private static final String KEY_PHASE = "phase";

    //Table create statements
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_USERID + " TEXT PRIMARY KEY," + KEY_USERNAME
            + " TEXT)";

    private static final String CREATE_TABLE_ANSWERSTATUS = "CREATE TABLE "
            + TABLE_ANSWERSTATUS + "(" + KEY_QUESTIONID + " TEXT," + KEY_USERID
            + " TEXT," + KEY_ANSWERSTATUS + " INTEGER," + KEY_QUESTIONORDER
            + " INTEGER, PRIMARY KEY("+ KEY_QUESTIONID +", " + KEY_USERID +"))";

    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE "
            + TABLE_QUESTION + "(" + KEY_QUESTIONID + " TEXT PRIMARY KEY," + KEY_QUESTION
            + " TEXT," + KEY_ANSWER + " INTEGER," + KEY_QUESTIONORDER + " INTEGER)";

    private static final String CREATE_TABLE_PHASE = "CREATE TABLE "
            + TABLE_PHASE + "(" + KEY_PHASEID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_QUESTIONID
            + " TEXT," + KEY_USERID + " INTEGER," + KEY_PHASE + " TEXT)";

    private SQLiteDatabase db;
    private Cursor cursor;
    private Question question;
    private Answerstatus answerstatus;
    private int id;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_ANSWERSTATUS);
        db.execSQL(CREATE_TABLE_PHASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERSTATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHASE);

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

    public boolean addStatus(int status, String id, int order) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ANSWERSTATUS, status);
        contentValues.put(KEY_QUESTIONID, id);
        contentValues.put(KEY_QUESTIONORDER, order);
        Log.d(TAG, "addStatus: Adding " + status + " " + id
                + " " + order + " to " + TABLE_ANSWERSTATUS);

        long result = db.insert(TABLE_ANSWERSTATUS, null, contentValues);

        if (result == -1){
            Log.d(TAG, "insert failed");
            return false;
        }else {
            Log.d(TAG, "insert successful");
            return true;
        }
    }

    public boolean updateStatus(int status, String id, int order) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ANSWERSTATUS, status);
        Log.d(TAG, "addStatus: updating " + status + " " + id
                + " " + order + " to " + TABLE_ANSWERSTATUS);

        long result = db.update(TABLE_ANSWERSTATUS, contentValues, "questionorder="+order, null);

        if (result == -1){
            Log.d(TAG, "insert failed");
            return false;
        }else {
            Log.d(TAG, "insert successful");
            return true;
        }
    }

    public Question findFirstQuestion() {
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
        question = new Question(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        Log.d(TAG, question.getQuestionText());
        return question;
    }

    public Question findQuestionById(int number) {
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
        question = new Question(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        Log.d(TAG, question.getQuestionText());
        return question;
    }

    public Answerstatus getAnswerStatus(int questionOrder) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ANSWERSTATUS + " WHERE "
                + KEY_QUESTIONORDER + "=" + questionOrder;
        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        answerstatus = new Answerstatus(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        return answerstatus;
    }

    public Question findNextQuestion() {

        id++;
        db = this.getReadableDatabase();
        cursor = db.query(TABLE_QUESTION, new String[] {KEY_QUESTIONID, KEY_QUESTION,
                        KEY_ANSWER, KEY_QUESTIONORDER},
                KEY_QUESTIONORDER + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor !=null){
            Log.d(TAG, "cursor not null");
            cursor.moveToFirst();
        }
        question = new Question(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        Log.d(TAG, question.getQuestionText());
        return question;
    }

    public Question findPreviousQuestion() {
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
        question = new Question(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        Log.d(TAG, question.getQuestionText());
        return question;
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
        long cnt  = DatabaseUtils.queryNumEntries(db, TABLE_QUESTION);
        return cnt;
    }

    //return the data from db
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PHASE;
        data = db.rawQuery(query, null);
        return data;
    }
    /*
        public String getPost() {
            String str = "eitoimi";

            SQLiteDatabase db = this.getWritableDatabase();

            //insert lause, joka lisää onko kysymys empty, tried, done riippuen onko vastattu oikein ja onko välivaiheita.
            String answerstatusquery ="INSER INTO "

            String userquery = "SELECT " + KEY_USERNAME + " FROM " + TABLE_USER;
            data = db.rawQuery(userquery, null);

            if (data.moveToFirst()) {
                str = data.getString(data.getColumnIndex(KEY_USERNAME));
            }
            return str;
        */
    // ESIMERKKI
    public String getPost() {
        String str = "";

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_USERNAME + " FROM " + TABLE_USER + " WHERE " + KEY_USERNAME + " = 'Jaakko'";
        data = db.rawQuery(query, null);
        if (data.moveToFirst()) {
            str = data.getString(data.getColumnIndex(KEY_USERNAME));
        }
        return str;
    }

    public  void toLocalFromFirebase(String questionid, String question, String answer ,int questionorder){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO "+ TABLE_QUESTION +" (" + KEY_QUESTIONID + ", " + KEY_QUESTION +", " + KEY_ANSWER + ", " + KEY_QUESTIONORDER + ")Values ('" + questionid + "', '" + question + "', '"+ answer + "', " + questionorder +")");

        }
        catch(Exception e){
            System.out.println("dbHelper error:" + e);
        }
    }


}