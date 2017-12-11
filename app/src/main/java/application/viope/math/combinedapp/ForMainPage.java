package application.viope.math.combinedapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import application.viope.math.combinedapp.bean.ForDatabaseHelper;
import application.viope.math.combinedapp.bean.ForQuestions;

public class ForMainPage extends AppCompatActivity {
    ForDatabaseHelper myDb;

    TextView putdata;
    Button economyButton, areaButton, geometryButton, fraction3Button, GetQuestions, SyncResults;
    final Context context = this;
    int count;


    DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference().child("formulas");;

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
        SyncResults = (Button) findViewById(R.id.sendResults);
        SyncResults.setOnClickListener(syncResultsOnclickListener);
        //putdata = (TextView) findViewById(R.id.putdata);


    }

    private View.OnClickListener economyButtonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ArrayList<ForQuestions> jau = myDb.getQuestions();

            if (jau != null) {
                count = jau.size();
            }
            if (count > 0) {
                Intent intent = new Intent(getApplicationContext(), ForFormulaActivity2.class);
                startActivity(intent);
            }
            else
            {
                new AlertDialog.Builder(ForMainPage.this)
                        .setTitle("Erro!")
                        .setMessage("Faça o download de perguntas primeiro")
                        .setCancelable(true)
                        .setPositiveButton("Está bem", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                            }
                        }).show();
            }
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
            int size = getFireBaseData();
            if (size >0){
                new AlertDialog.Builder(ForMainPage.this)
                        .setTitle("Feito!")
                        .setMessage("Perguntas baixadas: " + size)
                        .setCancelable(true)
                        .setPositiveButton("Está bem", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                            }
                        }).show();
            }
        }
    };

    private View.OnClickListener syncResultsOnclickListener = new View.OnClickListener() {

        public void onClick(View v) {

            String res = myDb.getResults() + "next set: " ;
            sendResults();

            new AlertDialog.Builder(ForMainPage.this)
                    .setTitle("Feito!")
                    .setMessage("Resultados enviados!")
                    .setCancelable(true)
                    .setPositiveButton("Está bem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).show();

        }
    };







    private void sendResults(){
        String res = myDb.getResults();
        questionRef.child("results").setValue(res);
    }

    private int  getFireBaseData() {

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
                ArrayList<ForQuestions> jau = myDb.getQuestions();
                count =jau.size();

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
        return count;
    }


}