package application.viope.math.combinedapp;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ln7_ExerciseGroups extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mToggle;
    private ln7_FDatabase firebase = new ln7_FDatabase();
    private EquDatabaseHelper db = new EquDatabaseHelper (this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check the orientation of the device
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.ln7_activity_exercise_groups);

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.ln7_land_activity_exercise_groups);
        }

        setDrawerMenu();
        setNavigationViewListener();
        setQuestions();
        createButtons();
    }



    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setContentView(R.layout.ln7_land_activity_exercise_groups);
            setQuestions();
            setDrawerMenu();
            setNavigationViewListener();
            createButtons();

        } else {

            setContentView(R.layout.ln7_activity_exercise_groups);
            setQuestions();
            setDrawerMenu();
            setNavigationViewListener();
            createButtons();
        }
    }

    public void setQuestions() {
        firebase.setExercises(this);
    }

    public void createButtons() {
        //onClick- listeners to Roots, Fractions and Theory buttons
        ImageButton rootButton = (ImageButton)findViewById(R.id.root_bt);
        // ! DO NOT DELETE ! Icon made by [Freepik] from www.flaticon.com
        rootButton.setOnClickListener((new View.OnClickListener() {
            public void onClick (View v) {
                Intent roots_intent = new Intent(ln7_ExerciseGroups.this, ln7_Exercises_roots.class);

                if (db.getAllExercises_fr().size() <= 0) {
                    showToast("Vá para o ponto de acesso wifi!");
                }else {
                    startActivity(roots_intent);
                }
            }
        }));

        ImageButton fracButton = (ImageButton)findViewById(R.id.frac_bt);
        fracButton.setOnClickListener((new View.OnClickListener() {
            public void onClick (View v) {
                Intent frac_intent = new Intent(ln7_ExerciseGroups.this, ln7_Exercises_frac.class);
                if (db.getAllExercises_fr().size() <= 0) {
                    showToast("Vá para o ponto de acesso wifi!");
                }else {
                    startActivity(frac_intent);
                }
            }
        }));

    }

    public void showToast(String thetext) {
        int length = Toast.LENGTH_LONG;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, thetext, length);
        toast.show();
    }

    public void setDrawerMenu() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ln7_actionbar_gradient_violet));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        Intent MenuIntents = getIntent();
        switch (item.getItemId()) {

            case R.id.navi_Home:
                MenuIntents = new Intent(ln7_ExerciseGroups.this, EquMainActivity.class);
                startActivity(MenuIntents);
                return true;

            case R.id.navi_Excerices:
                MenuIntents = new Intent(ln7_ExerciseGroups.this, ln7_ExerciseGroups.class);
                startActivity(MenuIntents);
                return true;

            case R.id.navi_Theory:
                MenuIntents = new Intent(ln7_ExerciseGroups.this, ln7_FracTheory.class);
                startActivity(MenuIntents);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void setNavigationViewListener(){

        NavigationView nView = (NavigationView) findViewById(R.id.nav_view);
        nView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

