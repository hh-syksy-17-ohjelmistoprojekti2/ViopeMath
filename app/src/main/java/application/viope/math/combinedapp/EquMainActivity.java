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

        ActionBar myActionBar = getSupportActionBar();
        myActionBar.show();

        mDrawerList = findViewById(R.id.navList);
        mDrawerLayout = findViewById(R.id.drawer_layout);
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
        String[] exerciseArray = { "Equacões", "Matemático de aprendizagem ao consumidor", "Fórmulas", "Frações"}; //placeholder-array tehtäville, myöhemmin haetaan dynaamisesti jostain arrayhyn
        String[] linkArray = {}; //array linkeille, jotka näkyvät aina (nyt download/upload)

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
                    return !(position == perguntas || position == respostas);
                }

            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
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
                        intent = new Intent(EquMainActivity.this, EquListExercisesActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        EquMainActivity.this.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(EquMainActivity.this, ConsMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        EquMainActivity.this.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(EquMainActivity.this, ForMainPage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        EquMainActivity.this.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(EquMainActivity.this, ln7_ExerciseGroups.class);
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

    public void startLn7(View view) {
        Intent intent = new Intent(this, ln7_ExerciseGroups.class);
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