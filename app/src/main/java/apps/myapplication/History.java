package apps.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import  java.text.SimpleDateFormat;
import java.text.DateFormat;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.Gravity;
import  android.graphics.Color;
import java.util.Date;

public class History extends Activity {

    private DBHelper db;
    private  TableRow tbrow;
    private TableLayout stk;
    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.history_main);
        stk = (TableLayout) findViewById(R.id.table_main);

        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" (DD-MM-YY HH-MM) ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Speed ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Distance ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" H:M:S ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        try {

            db = new DBHelper(this);

            Cursor res = db.getData();
            if (res.getCount() == 0) {

                System.out.println("Number of elements are zero");

            } else {

                while (res.moveToNext()) {

                    DateFormat sdf = new SimpleDateFormat("dd-M-yyyy h:m");
                    tbrow = new TableRow(this);

                    TextView t1v = new TextView(this);
                    t1v.setText(sdf.format(new Date(Long.parseLong(res.getString(1)) ))+" ");
                    t1v.setTextColor(Color.WHITE);
                    t1v.setGravity(Gravity.CENTER);
                    tbrow.addView(t1v);
                    TextView t2v = new TextView(this);
                    t2v.setText(String.format("%.2f", (Float.valueOf(res.getString(2))) / ( Integer.valueOf(res.getString(3)) ))+" M/S  " );
                    t2v.setTextColor(Color.WHITE);
                    t2v.setGravity(Gravity.CENTER);
                    tbrow.addView(t2v);
                    TextView t3v = new TextView(this);
                    t3v.setText(res.getString(2)+" M  ");
                    t3v.setTextColor(Color.WHITE);
                    t3v.setGravity(Gravity.CENTER);
                    tbrow.addView(t3v);
                    TextView t4v = new TextView(this);
                    t4v.setText(String.valueOf(Integer.valueOf(res.getString(3)) / 3600) + " : " + String.valueOf((Integer.valueOf(res.getString(3)) / 60) % 60) + " : " + String.valueOf(Integer.valueOf(res.getString(3)) % 60));
                    t4v.setTextColor(Color.WHITE);
                    t4v.setGravity(Gravity.CENTER);
                    tbrow.addView(t4v);
                    stk.addView(tbrow);

                }

            }
        }catch (Exception e){

            System.out.println(e);
        }
        Button next = (Button) findViewById(R.id.Button02);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });



    }

    public void selfClear(View view){

        if(db.Clear()) {
            System.out.println("cleared");
            stk.removeAllViews();
        }
        else
            System.out.println("Not cleared");


    }
}