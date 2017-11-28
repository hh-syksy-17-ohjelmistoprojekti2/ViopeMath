package application.viope.math.combinedapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import application.viope.math.combinedapp.bean.ConsAnswer;


public class ConsSettingsPage extends Fragment {

    View myView;
    private EquDatabaseHelper mydb;
    String allAnswers="";


    ListView listview;
    ArrayList<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter;
    DatabaseReference qref;
    boolean connected = false;
    ConsMainActivity mainActivity = new ConsMainActivity();
    public int questionsize = 0;
    public int firebasequestions = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings_page, container, false);
        final ImageButton uploadBtn = myView.findViewById(R.id.uploadBtn);
        final ImageButton downloadBtn = myView.findViewById(R.id.downloadBtn);
        final ProgressBar progressBar = myView.findViewById(R.id.progressBar);
        mydb = new EquDatabaseHelper(getContext());
        progressBar.setVisibility(View.GONE);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line,list);
        qref = FirebaseDatabase.getInstance().getReference();
        questionsize = mydb.ConsGetAllQuestions().size();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("answers");
        final EditText text = myView.findViewById(R.id.todoText);
        for (ConsAnswer answer : mydb.ConsGetAllElements()){
            allAnswers += answer.toString() + " ";
        }



        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected(getActivity()) == true){
                    if (text.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please enter your name or student number", Toast.LENGTH_LONG).show();
                    } else if(allAnswers.isEmpty()){
                        Toast.makeText(getContext(), "No saved answers", Toast.LENGTH_LONG).show();
                    }

                    else {
                        DatabaseReference childRef = myRef.push();
                        childRef.child("answers").setValue(allAnswers);
                        childRef.child("name").setValue(text.getText().toString());
                        progressBar.setVisibility(View.VISIBLE);
                        new CountDownTimer(3000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                uploadBtn.setBackgroundResource(R.drawable.bb_ic_upload_app_green_24dp);
                            }

                            public void onFinish() {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), getString(R.string.upload_complete), Toast.LENGTH_LONG).show();
                                uploadBtn.setBackgroundResource(R.drawable.bb_ic_upload_app_white_24dp);
                            }
                        }.start();
                    }
                }else{
                    Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_LONG).show();
                }
            }

        });

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isConnected(getActivity()) == true) {
                    qref.child("questions").addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        System.out.println("FIREBAAAAAAAAASSSI"+dataSnapshot.getChildrenCount());
                        System.out.println(firebasequestions);

                            if (mydb.ConsGetAllQuestions().isEmpty()) {
                                Intent intent = new Intent(getContext(), ConsMainActivity.class);
                                startActivity(intent);
                            }

                                String s_id = dataSnapshot.child("q_id").getValue().toString();
                                int id = Integer.parseInt(s_id);
                                String question = dataSnapshot.child("question").getValue().toString();
                                String answer = dataSnapshot.child("r_answer").getValue().toString();
                                double f_answer = Double.parseDouble(answer.replace(",", "."));
                                String hint = dataSnapshot.child("hint").getValue().toString();
                                mydb.ConsInsertQuestion(id, question, f_answer, hint);

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



                progressBar.setVisibility(View.VISIBLE);
                new CountDownTimer(3000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        downloadBtn.setBackgroundResource(R.drawable.bb_ic_get_app_green_24dp);
                    }

                    public void onFinish() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), getString(R.string.download_complete), Toast.LENGTH_LONG).show();
                        downloadBtn.setBackgroundResource(R.drawable.bb_ic_get_app_white_24dp);
                    }
                }.start();
            }else{
                    Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else return false;
    }


}

