package application.viope.math.combinedapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


public class ConsMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConsOnFragmentInteractionListener {

    private EquDatabaseHelper mydb;
    int qsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new EquDatabaseHelper(getBaseContext());
        qsize = mydb.ConsGetAllQuestions().size();
        creatingView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
      pressingBackButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cons_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    private void displaySelectedScreen(int id) {
       viewFromNavigationDrawer(id);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
                displaySelectedScreen(id);
        return true;
    }
    // ------------ METHODS ----------------------

    public void creatingView() {

        setContentView(R.layout.cons_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_frontpage);

    }

    private void pressingBackButton() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void viewFromNavigationDrawer(int id) {
        Fragment fragment= null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (qsize < 1) {
            switch (id) {
                case R.id.nav_frontpage:
                    fragment = new ConsFirstPage();
                    break;
                case R.id.nav_formulapage:
                    fragment = new ConsFormulaPage();
                    ft.addToBackStack(null);
                    break;
                case R.id.nav_exercisepage:
                    Toast.makeText(getApplicationContext(),"Download questions from settingspage", Toast.LENGTH_LONG).show();
                    break;
                case R.id.nav_settingspage:
                    fragment = new ConsSettingsPage();
                    ft.addToBackStack(null);
                    break;
            }
        }else {
            switch (id) {
                case R.id.nav_frontpage:
                    fragment = new ConsFirstPage();
                    break;
                case R.id.nav_formulapage:
                    fragment = new ConsFormulaPage();
                    ft.addToBackStack(null);
                    break;
                case R.id.nav_exercisepage:
                    fragment = new ConsExercisePage();
                    ft.addToBackStack(null);
                    break;
                case R.id.nav_settingspage:
                    fragment = new ConsSettingsPage();
                    ft.addToBackStack(null);
                    break;
            }

        }

        if(fragment != null) {
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById  (R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}



