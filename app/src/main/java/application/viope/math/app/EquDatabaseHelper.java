package application.viope.math.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import application.viope.math.app.bean.EquAnswerstatus;
import application.viope.math.app.bean.EquQuestion;


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
            + TABLE_USER + "(" + KEY_USERID + " TEXT PRIMARY KEY," + KEY_USERNAME
            + " TEXT)";

    private static final String CREATE_TABLE_ANSWERSTATUS = "CREATE TABLE "
            + TABLE_ANSWERSTATUS + "(" + KEY_QUESTIONID + " TEXT," + KEY_USERID
            + " TEXT," + KEY_ANSWERSTATUS + " INTEGER, PRIMARY KEY("+ KEY_QUESTIONID +", " + KEY_USERID +"), "
            + "FOREIGN KEY (" + KEY_QUESTIONID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTIONID + "), "
            + "FOREIGN KEY (" + KEY_USERID + ") REFERENCES " + TABLE_USER + "(" + KEY_USERID + "))";

    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE "
            + TABLE_QUESTION + "(" + KEY_QUESTIONID + " TEXT PRIMARY KEY," + KEY_QUESTION
            + " TEXT," + KEY_ANSWER + " INTEGER," + KEY_QUESTIONORDER + " INTEGER)";

    private static final String CREATE_TABLE_PHASE = "CREATE TABLE "
            + TABLE_PHASE + "(" + KEY_PHASEID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_QUESTIONID
            + " TEXT," + KEY_USERID + " INTEGER," + KEY_PHASE + " TEXT, "
            + "FOREIGN KEY (" + KEY_QUESTIONID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTIONID + "))";

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
        ContentValues contentValues2 = new ContentValues();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ANSWERSTATUS, status);
        contentValues.put(KEY_QUESTIONID, id);
        contentValues2.put(KEY_QUESTIONORDER, order);
        Log.d(TAG, "addStatus: Adding " + status + " " + id
                + " " + order + " to " + TABLE_ANSWERSTATUS);

        long result = db.insert(TABLE_ANSWERSTATUS, null, contentValues);
        //long result2 = db.insert(TABLE_QUESTION, null, contentValues2);

        if (result == -1 /*|| result2 == -1*/){
            Log.d(TAG, "insert failed");
            return false;
        }else {
            Log.d(TAG, "insert successful");
            return true;
        }

    }
    public void testiAdd(String questionid, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+ TABLE_ANSWERSTATUS + " (" + KEY_QUESTIONID + ", " + KEY_ANSWERSTATUS + ")Values ('" + questionid + "', '"+ status +"')");
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
        equAnswerstatus = new EquAnswerstatus(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
        return equAnswerstatus;
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


}
