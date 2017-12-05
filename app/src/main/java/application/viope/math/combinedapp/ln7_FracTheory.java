package application.viope.math.combinedapp;


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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class ln7_FracTheory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public WebView helpWebView;
    public ImageButton goToHome;
    public ImageButton goToExercises;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            setContentView(R.layout.ln7_activity_frac_theory);

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setContentView(R.layout.ln7_land_activity_frac_theory);
        }

        setDrawerMenu();
        setNavigationViewListener();
        jsControls();
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

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setContentView(R.layout.ln7_land_activity_frac_theory);
            setDrawerMenu();
            setNavigationViewListener();
            jsControls();
            createButtons();

        } else {

            setContentView(R.layout.ln7_activity_frac_theory);
            setDrawerMenu();
            setNavigationViewListener();
            jsControls();
            createButtons();
        }
    }

    public void createButtons() {
        // onClick- listeners to Home- and Fraction buttons
        goToHome = (ImageButton) findViewById(R.id.home_bt);
        goToHome.setOnClickListener((new View.OnClickListener() {
            public void onClick (View v) {
                Intent homeintent = new Intent(ln7_FracTheory.this, EquMainActivity.class);
                startActivity(homeintent);
            }
        }));

        goToExercises = (ImageButton) findViewById(R.id.goBackToExercises_bt);
        goToExercises.setOnClickListener((new View.OnClickListener() {
            public void onClick (View v) {
                Intent fracintent = new Intent(ln7_FracTheory.this, ln7_ExerciseGroups.class);
                startActivity(fracintent);
            }
        }));
    }

    public void jsControls () {
        // Enables javascript and zooming controls, sets content to TheoryView.
        helpWebView = (WebView) findViewById(R.id.wvTheory);
        helpWebView.getSettings().setJavaScriptEnabled(true);
        helpWebView.getSettings().setBuiltInZoomControls(true);

        String url = "</style>"
                + "<script type='text/x-mathjax-config'>"
                + "MathJax.Hub.Config({"
                + "showMathMenu: false,"
                + "jax: ['input/TeX','output/HTML-CSS'],"
                + "displayAlign: \"left\","
                + "displayIndent: \"2em\","
                + "extensions: ['tex2jax.js','MathMenu.js','MathZoom.js'],"
                + "tex2jax: { inlineMath: [ ['$','$'] ], processEscapes: true },"
                + "TeX: {extensions:['AMSmath.js','AMSsymbols.js', 'noUndefined.js']" + "             }"
                + "  });"
                + "</script>"
                + "<script type='text/javascript' src='file:///android_asset/MathJax.js'>"
                + "</script>"
                + "<p style=\"line-height:1.5; padding: 16 16\" text-align=\"left !important\" display=\"inline !important\">"
                + "<span >";

        url += getResources().getString(R.string.theoryText);

        url += "</span></p>";

        helpWebView.loadDataWithBaseURL("file:///android_asset/ln7_theory.html", url, "text/html", "utf-8", "");
        helpWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!url.startsWith("file:///android_asset/ln7_theory.html")) return;
                if (android.os.Build.VERSION.SDK_INT < 19) {
                    helpWebView.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                } else {
                    helpWebView.evaluateJavascript("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);",null);
                }
            }
        });
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
                MenuIntents = new Intent(ln7_FracTheory.this, EquMainActivity.class);
                startActivity(MenuIntents);
                return true;

            case R.id.navi_Excerices:
                MenuIntents = new Intent(ln7_FracTheory.this, ln7_ExerciseGroups.class);
                startActivity(MenuIntents);
                return true;

            case R.id.navi_Theory:
                MenuIntents = new Intent(ln7_FracTheory.this, ln7_FracTheory.class);
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

