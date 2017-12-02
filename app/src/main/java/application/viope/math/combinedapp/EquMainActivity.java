package application.viope.math.combinedapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class EquMainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mAdapter;
    private EquDatabaseHelper dbHelper;
    private EquFirebaseHelper FbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.equ_activity_main);

        dbHelper = new EquDatabaseHelper(this);
/*
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
        dbHelper.addStatus(1, "esimerkkiid",1);
        dbHelper.addStatus(0, "esimerkkiiid",2);
        dbHelper.addStatus(0, "esimerkkiiiid",3);
        dbHelper.addStatus(0, "testi4",4);
        dbHelper.addStatus(0, "testi5",5);
        dbHelper.addStatus(0, "testi6",6);
        dbHelper.addStatus(0, "testi7",7);
        dbHelper.addStatus(0, "testi8",8);
        dbHelper.addStatus(0, "testi9",9);
        dbHelper.addStatus(0, "testi10",10);
        dbHelper.addStatus(0, "testi11",11);*/

        ActionBar myActionBar = getSupportActionBar();
        myActionBar.show();

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        addDrawerItems();

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                supportInvalidateOptionsMenu();
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.equ_menu);
    }

    private void addDrawerItems() {
        String[] exerciseArray = { "Exercício 1", "Exercício 2", "Exercício 3"}; //placeholder-array tehtäville, myöhemmin haetaan dynaamisesti jostain arrayhyn
        String[] linkArray = {"Baixar as perguntas", "Carregar as respostas"}; //array linkeille, jotka näkyvät aina (nyt download/upload)

        //yhdistetään arrayt, jotta sisällöt voi laittaa linkkeinä draweriin
        final String[] drawerArray = new String[exerciseArray.length + linkArray.length];
        System.arraycopy(exerciseArray, 0, drawerArray, 0, exerciseArray.length);
        System.arraycopy(linkArray, 0, drawerArray, exerciseArray.length, linkArray.length);

        class MenuAdapter extends ArrayAdapter<String> {

            int perguntas = drawerArray.length - 2; //normilinkkien sijainnit listassa
            int respostas = drawerArray.length - 1;

            public MenuAdapter(
                    Context context, int textViewResId, String[] strings) {
                super(context, textViewResId, strings);
            }

            public boolean areAllItemsEnabled() {
                return false;
            }

            public boolean isEnabled(int position) {
                if (EquAppNetStatus.getInstance(EquMainActivity.this).isOnline()) {
                    return true;
                } else {
                    if (position == perguntas || position == respostas) {
                        return false;
                    } else {
                        return true;
                    }
                }

            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                if(isEnabled(position)) {
                    tv.setBackgroundColor(getResources().getColor(R.color.bgLight));
                    tv.setTextColor(getResources().getColor(R.color.black));
                } else {
                    tv.setBackgroundColor(getResources().getColor(R.color.disabledBg));
                    tv.setTextColor(getResources().getColor(R.color.disabledText));
                }
                return view;
            }
        }

        mAdapter = new MenuAdapter(this, android.R.layout.simple_list_item_1, drawerArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(EquMainActivity.this, EquListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        EquMainActivity.this.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(EquMainActivity.this, EquListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        EquMainActivity.this.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(EquMainActivity.this, EquListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        EquMainActivity.this.startActivity(intent);
                        break;
                }
            }
        });
    }

    public void startExcercise(View view) {
        Intent intent = new Intent(this, EquListExercisesActivity.class);
        //Intent intent = new Intent(this, EquTarkistuslogiikkaActivity.class);
        startActivity(intent);
    }

    public void startBonsai(View view) {
        Intent intent = new Intent(this, ConsMainActivity.class);
        startActivity(intent);
    }

    public void startFormulas(View view) {
        Intent intent = new Intent(this, ForMainPage.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            mAdapter.notifyDataSetChanged();
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}