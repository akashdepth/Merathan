package apps.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.location.LocationManager;
import android.location.Location;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.icu.text.DecimalFormat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private  Location location1;
    private double total_distance;
    private double speed;
    private  boolean start;
    private  boolean change;
    private  int time;
    private  int start_time;
    private  TextView tspeed;
    private  TextView tt_distance;
    private  TextView ttime;
    private  TextView taspeed;


    public  MainActivity(){

        total_distance = 0;
        start = false;
        speed = 0;
        change = true;
        time = (int) (System.currentTimeMillis());
        start_time = (int)(System.currentTimeMillis());
    }
    public void updatelatitude()
    {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1 , locationListener);

        } else {

            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1634);

        }


    }
    public void selfDestruct(View view) {
        start = true;
        time = (int) (System.currentTimeMillis());
        start_time = (int)(System.currentTimeMillis());
        tspeed.setText("0 Meter/Sec");
        tt_distance.setText("0 Meter");
        taspeed.setText("0 Meter/Sec");
        ttime.setText("00 : 00: 00");

    }
    public  void selfReset(View view){
        total_distance = 0;
        start = false;
        speed = 0;
        time = (int) (System.currentTimeMillis());
        start_time = (int)(System.currentTimeMillis());
        tspeed.setText("0 Meter/Sec");
        tt_distance.setText("0 Meter");
        taspeed.setText("0 Meter/Sec");
        ttime.setText("00 : 00: 00");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        taspeed = (TextView) findViewById(R.id.textView5);
        tt_distance = (TextView) findViewById(R.id.textView2);
        tspeed = (TextView) findViewById(R.id.textView8);
        ttime = (TextView) findViewById(R.id.textView10);


        setSupportActionBar(toolbar);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1 , locationListener);

        } else {

            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1634);

        }

    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String b;
            if(change){

                b = "PointA";
                change = !change;
            }
            else
            {

                b = "PointB";
                change = !change;

            }
            Location location2 = new Location(b);
            location2.setLongitude(location.getLongitude());
            location2.setLatitude(location.getLatitude());
            if(start){

                double distance  = location1.distanceTo(location2);
                total_distance += distance;
                int time2 =  (int) (System.currentTimeMillis());
                speed = (distance *1000/(time2 - time));
                time = time2;
                DecimalFormat df = new DecimalFormat("#.##");
              tspeed.setText(String.valueOf(df.format(speed))+" Meter/Sec");
                tt_distance.setText(String.valueOf(df.format(total_distance))+" Meter");
                int temp_time = time - start_time;
                taspeed.setText(String.valueOf(df.format((total_distance*1000)/temp_time))+" Meter/Sec");
                temp_time=temp_time/1000;
                ttime.setText(String.valueOf(temp_time/3600)+" : "+String.valueOf((temp_time/60)%60)+" : "+String.valueOf(temp_time%60));
                System.out.println(total_distance);

            }
            location1 = location2;
        }
        @Override
        public  void onProviderDisabled(String a){

        }

        @Override
        public  void onProviderEnabled(String a){}

        @Override
        public void onStatusChanged(String a,int b,Bundle c){}
    };

    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
            switch (requestCode) {
                case 1634: {
                    // If request is cancelled, the result arrays are empty.
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
