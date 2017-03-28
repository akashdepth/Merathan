package apps.myapplication;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Merathan.db";
    public static final String TABLE_NAME = "merathan_table";
    public static final String col1 = "ID";
    public static final String col2 = "DISTANCE";
    public static final String col3 = "SPEED";
    public static final String col4 = "TIMESTAMP";

    public DatabaseHelper(Context context){

        super(context,DATABASE_NAME, null,1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +" (ID  INTEGER PRIMARY KEY AUTOINCREMENT , DISTANCE , SPEED , TIMESTAMP);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(sqLiteDatabase);
    }
}
