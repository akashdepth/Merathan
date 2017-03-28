package apps.myapplication;

/**
 * Created by akash on 24/03/17.
 */

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Hashtable;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.DatabaseUtils;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String Merathan_TABLE_NAME = "merathan";
    public static final String Merathan_COLUMN_ID = "id";
    public static final String Merathan_COLUMN_TIMESTAMP = "timestamp";
    public static final String Merathan_COLUMN_DISTANCE = "distance";
    public static final String Merathan_COLUMN_SPEED = "speed";
    private HashMap hp;

    public DBHelper(Context context) {

        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(
                "create table merathan " +
                        "(id integer primary key autoincrement, timestamp text,distance text,speed text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS merathan");

        onCreate(db);

    }

    public boolean insertMerathan (String  TIMESTAMP, String DISTANCE, String SPEED) {
        System.out.println(this);

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();


        contentValues.put("timestamp", TIMESTAMP);


        contentValues.put("distance", DISTANCE);

        contentValues.put("speed", SPEED);


        db.insert("merathan" , null , contentValues );

        return true;
    }


    public  boolean Clear(){


            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL("DROP TABLE IF EXISTS merathan");
            onCreate(db);
            return true;


    }
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from merathan ", null );
        return res;
    }





}