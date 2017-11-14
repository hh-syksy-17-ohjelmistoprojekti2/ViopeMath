package application.viope.math.app;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import application.viope.math.app.bean.User;
import static application.viope.math.app.AppNetStatus.context;


/**
 * Created by a1600519 on 30.10.2017.
 */



public class FirebaseHelper {

    private DatabaseReference kDatabase;
    private String testQuestion;
    private ArrayList<String> lista;

    DatabaseHelper dbContextHelper = new DatabaseHelper(context);


    public String getTestQuestion() {
        return testQuestion;
    }

    public DatabaseHelper dbHelper;
    //private DatabaseHelper dbHelper;

    public void Post(String username) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("user");
        //dbHelper = new DatabaseHelper(context);
        //DatabaseHelper dbHelper = new DatabaseHelper();
// Creating new user node, which returns the unique key value
// new user node would be /users/$userid/
        String userid = mDatabase.push().getKey();

// creating user object
        //User user = new User(dbHelper.getPost());
        User user = new User(username);


// pushing user to 'users' node using the userId
        mDatabase.child(userid).setValue(user);

    }


    public void Get() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("question");

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
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
        //return new Question(questionid, question, answer, questionorder);
    }
}

