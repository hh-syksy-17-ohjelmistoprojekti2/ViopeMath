package application.viope.math.combinedapp;


import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import application.viope.math.combinedapp.bean.ln7_Exercise;

import static android.content.ContentValues.TAG;

public class ln7_FDatabase {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference questions = database.getReference("fracroots").child("questions");
    private List<ln7_Exercise> lista = new ArrayList<ln7_Exercise>();

    public void setExercises(Context context){

        final EquDatabaseHelper db = new EquDatabaseHelper (context);

        // Read from the database
        questions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    lista.add(child.getValue(ln7_Exercise.class));
                }
                db.setExercisesFirebase_fr(lista);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}

