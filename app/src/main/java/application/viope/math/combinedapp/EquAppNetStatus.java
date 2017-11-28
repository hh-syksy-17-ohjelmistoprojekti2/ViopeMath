package application.viope.math.combinedapp;

/**
 * Created by a1600519 on 24.10.2017.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class EquAppNetStatus {

    private static EquAppNetStatus instance = new EquAppNetStatus();
    static Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected = false;

    public static EquAppNetStatus getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }
    /* ESIMERKKI TOIMINNALLISUUDELLE
    if (EquAppNetStatus.getInstance(this).isOnline()) {
    //yhteys
    Toast t = Toast.makeText(this,"You are online!!!!",8000).show();

} else {
    //ei yhteyttä
    Toast t = Toast.makeText(this,"You are not online!!!!",8000).show();
    Log.v("Home", "############################You are not online!!!!");
}
     */
}