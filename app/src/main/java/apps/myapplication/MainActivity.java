package apps.myapplication;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import  android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.location.LocationManager;
import android.location.Location;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {

    private Location location1;
    private double total_distance;
    private double speed;
    private boolean start;
    private boolean change;
    private  long time;
    private long start_time;
    private TextView tspeed;
    private TextView tt_distance;
    private TextView ttime;
    private TextView taspeed;
    private DBHelper db;
    private long total_time;

    private final static String dbname = "merathan.db";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public MainActivity() {
        total_distance = 0;
        start = false;
        speed = 0;
        change = true;
        time = (long) (System.currentTimeMillis());
        start_time = (long) (System.currentTimeMillis());
        total_time = 0;

    }


    public void updatelatitude() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            System.out.println(1);

        } else {

            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1634);

            System.out.println(2);

        }

    }
    public void selfStop(View view){
        start = false;
    }
    public void selfHistory(View view){

        Intent myIntent = new Intent(view.getContext(), History.class);
        startActivityForResult(myIntent, 0);




        }

    public void selfSave(View view) {


        try {

            db.insertMerathan(String.valueOf(time), String.valueOf(String.format( "%.2f", total_distance )),  String.valueOf(total_time ));

        } catch (Exception e) {

            System.out.println(e);
        }

    }


    public void selfDestruct(View view) {
        try {
            LocationManager locateManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabled = locateManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
// check if enabled and if not send user to the GPS settings
            if (!enabled) {
                System.out.println("location is not on");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            } // B
            start = true;
        }
        catch(Exception e)
        {

            System.out.println(e);
        }

    }


    public void selfReset(View view) {
        total_distance = 0;
        start = false;
        speed = 0;
        total_time = 0;
        time = (long) (System.currentTimeMillis());
        start_time = (long) (System.currentTimeMillis());
        tspeed.setText("0 Meter/Sec");
        tt_distance.setText("0 Meter");
        taspeed.setText("0 Meter/Sec");
        ttime.setText("00 : 00 : 00");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            db = new DBHelper(this);

        } catch (Exception e) {
            System.out.println(e);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        taspeed = (TextView) findViewById(R.id.textView5);
        tt_distance = (TextView) findViewById(R.id.textView2);
        tspeed = (TextView) findViewById(R.id.textView8);
        ttime = (TextView) findViewById(R.id.textView10);


        setSupportActionBar(toolbar);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);

        } else {


            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1634);


        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private final LocationListener locationListener = new LocationListener() {
        @TargetApi(Build.VERSION_CODES.N)
        public void onLocationChanged(Location location) {

            String b;
            if (change) {

                b = "PointA";
                change = !change;
                System.out.println(7);

            } else {

                b = "PointB";
                change = !change;
                System.out.println(8);


            }
            Location location2 = new Location(b);
            location2.setLongitude(location.getLongitude());
            location2.setLatitude(location.getLatitude());
            try {
                if (start) {

                    double distance = location1.distanceTo(location2);
                    total_distance += distance;

                    long time2 = (long) (System.currentTimeMillis());
                    speed = (distance * 1000 / (time2 - time));
                    time = time2;

//                    DecimalFormat df = new DecimalFormat("#.##");
                    tspeed.setText(String.valueOf(String.format("%.2f", speed)) + " Meter/Sec");
                    tt_distance.setText(String.valueOf(String.format("%.2f", total_distance)) + " Meter");
                    long temp_time = time - start_time;
                    total_time = temp_time/1000;
                    taspeed.setText(String.valueOf(String.format("%.2f", (total_distance * 1000) / temp_time)) + " Meter/Sec");
                    temp_time = temp_time / 1000;
                    ttime.setText(String.valueOf(temp_time / 3600) + " : " + String.valueOf((temp_time / 60) % 60) + " : " + String.valueOf(temp_time % 60));


                }
            } catch (Exception e) {

                System.out.println(e);
            }

            location1 = location2;
        }

        @Override
        public void onProviderDisabled(String a) {

        }

        @Override
        public void onProviderEnabled(String a) {
        }

        @Override
        public void onStatusChanged(String a, int b, Bundle c) {
        }
    };

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1634: {
                // If request is cancelled, the result arrays are empty.
                System.out.println(10);

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);


                } else {
                    System.out.println("Location permission denied.");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://apps.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://apps.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}