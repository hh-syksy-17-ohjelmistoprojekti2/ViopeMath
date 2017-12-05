package application.viope.math.combinedapp;

import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import application.viope.math.combinedapp.bean.ForDatabaseHelper;


public class ForMainPage extends AppCompatActivity {
    ForDatabaseHelper myDb;

    TextView putdata;
    Button economyButton, areaButton, geometryButton, fraction3Button, GetQuestions;

    DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference().child("formulas");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_activity_main_page);
        myDb = new ForDatabaseHelper(this);
        initializeComponents();


        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);

        getSupportActionBar().

                setDisplayShowTitleEnabled(false);
        menu.setIcon(R.drawable.for_logo1);
        menu.setBackgroundDrawable(

                getResources().

                        getDrawable(R.drawable.for_actionbar_gradient));
    }

    private void initializeComponents() {
        economyButton = (Button) findViewById(R.id.economybutton);
        areaButton = (Button) findViewById(R.id.areabutton);
        geometryButton = (Button) findViewById(R.id.geometrybutton);
        fraction3Button = (Button) findViewById(R.id.fraction3Button);
        economyButton.setOnClickListener(economyButtonOnClickListener);
        // geometryButton.setOnClickListener(geometryButtonOnClickListener);
        GetQuestions = (Button) findViewById(R.id.GetQuestions);
        GetQuestions.setOnClickListener(getDataOnclickListener);
        //putdata = (TextView) findViewById(R.id.putdata);


    }

    private View.OnClickListener economyButtonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //To change body of implemented methods use File | Settings | File Templates.
            Intent intent = new Intent(getApplicationContext(), ForFormulaActivity2.class);
            startActivity(intent);
        }
    };


/*
    private View.OnClickListener geometryButtonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //To change body of implemented methods use File | Settings | File Templates.
            Intent intent = new Intent(getApplicationContext(), EconomyActivity.class);
            startActivity(intent);
        }
    };
*/

    private View.OnClickListener getDataOnclickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            myDb.clearQuestions();
            getFireBaseData();

        }
    };

    private void getFireBaseData() {
        questionRef.child("questions").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String QId = dataSnapshot.child("qid").getValue().toString();
                // int QId = Integer.parseInt(id);
                String question =  dataSnapshot.child("question").getValue().toString();
                String answer =  dataSnapshot.child("answer").getValue().toString();
                String offeredanswer =  dataSnapshot.child("offeredanswer").getValue().toString();
                String questionimage =  dataSnapshot.child("questionimage").getValue().toString();

                myDb.InsertIntoDB(QId,question, answer, offeredanswer, questionimage);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}