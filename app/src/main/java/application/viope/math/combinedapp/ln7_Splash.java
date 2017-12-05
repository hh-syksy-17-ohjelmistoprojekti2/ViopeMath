package application.viope.math.combinedapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ln7_Splash extends AppCompatActivity {

    // Redirects to the Index page after showing ln7_Splash screen.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, EquMainActivity.class);
        startActivity(intent);
        finish();
    }


}
