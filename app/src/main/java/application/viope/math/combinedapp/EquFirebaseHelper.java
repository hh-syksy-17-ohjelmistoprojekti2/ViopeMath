package application.viope.math.combinedapp;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import application.viope.math.combinedapp.bean.EquAnswerstatus;
import application.viope.math.combinedapp.bean.EquUser;

import static application.viope.math.combinedapp.EquAppNetStatus.context;


/**
 * Created by a1600519 on 30.10.2017.
 */



public class EquFirebaseHelper {

    private DatabaseReference kDatabase;
    private String testQuestion;
    private ArrayList<String> lista;

    EquDatabaseHelper dbContextHelper = new EquDatabaseHelper(context);



    public String getTestQuestion() {
        return testQuestion;
    }

    public EquDatabaseHelper dbHelper;


    public String getKey(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("equations");
        return mDatabase.child("equ_user").push().getKey();
    }
    //Moves username,answerstatus from local database to Firebase. Also changes answerstatus to empty, tried or done depending on its status.
    public void Post(ArrayList<EquAnswerstatus> AnswerstatusList, String username, String userid) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("equations");

        for(int i=0;i<AnswerstatusList.size();i++){
            Log.d("Data test from local", AnswerstatusList.get(i).getQuestionId() + " " + AnswerstatusList.get(i).getAnswerStatus());
            int answerstatus = AnswerstatusList.get(i).getAnswerStatus() ;
            String answerstatusString = "empty";
            if(answerstatus==1){
                answerstatusString = "tried";
            }else if(answerstatus==2){
                answerstatusString = "done";
            }
            mDatabase.child("equ_user").child(userid).child("answerstatus").child(AnswerstatusList.get(i).getQuestionId()).setValue(answerstatusString);
        }
        mDatabase.child("equ_user").child(userid).child("username").setValue(username);

    }

    //Gets questionid, question, answer, questionorder from Firebase and moves it to local database.Also puts answerstatus to 0.
    public void Get() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("equations").child("equ_question");

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                EquUser equUser = dataSnapshot.getValue(EquUser.class);
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    try {
                        int status = 0;
                        String questionid = messageSnapshot.getRef().getKey().toString();
                        String question = messageSnapshot.child("question").getValue().toString();
                        String answer = messageSnapshot.child("answer").getValue().toString();
                        String questionorder = messageSnapshot.child("questionorder").getValue().toString();


                        Log.d("Data test ", questionid + question + answer + questionorder);
                        dbContextHelper.toLocalFromFirebase(questionid, question, answer, Integer.parseInt(questionorder));
                        dbContextHelper.addStatus(questionid, status);
                    }
                    catch(Exception e){
                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }




        });
    }
}

