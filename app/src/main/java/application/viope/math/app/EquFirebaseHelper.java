package application.viope.math.app;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import application.viope.math.app.bean.EquUser;

import static application.viope.math.app.EquAppNetStatus.context;


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
    //private EquDatabaseHelper dbHelper;

    public void Post(String username) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("equUser");
        //dbHelper = new EquDatabaseHelper(context);
        //EquDatabaseHelper dbHelper = new EquDatabaseHelper();
// Creating new equUser node, which returns the unique key value
// new equUser node would be /users/$userid/
        String userid = mDatabase.push().getKey();

// creating equUser object
        //EquUser equUser = new EquUser(dbHelper.getPost());
        EquUser equUser = new EquUser(username);


// pushing equUser to 'users' node using the userId
        mDatabase.child(userid).setValue(equUser);

    }


    public void Get() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("question");

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                EquUser equUser = dataSnapshot.getValue(EquUser.class);
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String questionid = (String) messageSnapshot.getRef().getKey().toString();
                    String question = (String) messageSnapshot.child("question").getValue().toString();
                    String answer = (String) messageSnapshot.child("answer").getValue().toString();
                    String questionorder = (String) messageSnapshot.child("questionorder").getValue().toString();

                    //System.out.println(questionid + question + answer + questionorder);
                    dbContextHelper.toLocalFromFirebase(questionid, question, answer, Integer.parseInt(questionorder));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        //System.out.println(mDatabase.orderByValue().toString());
        //for (int i = 0; i < lista.size(); i++) {
        //    System.out.println(lista.get(i));
        //}
        //return new EquQuestion(questionid, question, answer, questionorder);
    }
}

