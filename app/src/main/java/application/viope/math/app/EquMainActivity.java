package application.viope.math.app;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class EquMainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mAdapter;
    private EquDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.equ_activity_main);

        dbHelper = new EquDatabaseHelper(this);
        dbHelper.addTestDataForShowingQuestion("2(3x-4)+5=3(x+1)", "2", "esimerkkiid", 1);
        dbHelper.addTestDataForShowingQuestion("3(2x+3)-5=-4(-x+3)", "-8", "esimerkkiiid", 2);
        dbHelper.addTestDataForShowingQuestion("(3(5x-2))/4+4=(4(4x-3))/2", "2", "esimerkkiiiid", 3);
        dbHelper.addTestDataForShowingQuestion("testi4", "4", "testi4", 4);
        dbHelper.addTestDataForShowingQuestion("testi5", "5", "testi5", 5);
        dbHelper.addTestDataForShowingQuestion("testi6", "6", "testi6", 6);
        dbHelper.addTestDataForShowingQuestion("testi7", "7", "testi7", 7);
        dbHelper.addTestDataForShowingQuestion("testi8", "8", "testi8", 8);
        dbHelper.addTestDataForShowingQuestion("testi9", "9", "testi9", 9);
        dbHelper.addTestDataForShowingQuestion("testi10", "10", "testi10", 10);
        dbHelper.addTestDataForShowingQuestion("testi11", "11", "testi11", 11);
        dbHelper.addStatus(0, "esimerkkiid",1);
        dbHelper.addStatus(0, "esimerkkiiid",2);
        dbHelper.addStatus(0, "esimerkkiiid",3);
        dbHelper.addStatus(0, "testi4",4);
        dbHelper.addStatus(0, "testi5",5);
        dbHelper.addStatus(0, "testi6",6);
        dbHelper.addStatus(0, "testi7",7);
        dbHelper.addStatus(0, "testi8",8);
        dbHelper.addStatus(0, "testi9",9);
        dbHelper.addStatus(0, "testi10",10);
        dbHelper.addStatus(0, "testi11",11);

    }



    public void startExcercise(View view) {
        Intent intent = new Intent(this, EquListExercisesActivity.class);
        //Intent intent = new Intent(this, TarkistuslogiikkaActivity.class);
        startActivity(intent);
    }


}