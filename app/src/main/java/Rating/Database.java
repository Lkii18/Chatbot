package Rating;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Chatbot.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "place";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_COMMENT_ID1 = "rating_id";

    private static final String TABLE_NAME2 = "comment";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_COMMENT2 = "comment";
    private static final String COLUMN_NAME2 = "name";
    private static final String COLUMN_RATING2 = "rating";
    private static final String COLUMN_USERNAME = "username";
    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_RATING + " FLOAT, " +
                COLUMN_COMMENT_ID1 + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_ADDRESS + " TEXT);";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + TABLE_NAME2 +
                " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME2 + " TEXT, " +
                COLUMN_RATING2 + " FLOAT, " +
                COLUMN_COMMENT2 + " TEXT);";

        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void rating(String name,String username,float rating,String comment) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME2, name);
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_RATING2, rating);
        cv.put(COLUMN_COMMENT2, comment);

        long result = db.insert(TABLE_NAME2, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if(db !=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor readComment(String name){
        String query = "SELECT * FROM " + TABLE_NAME2 +" WHERE "+ COLUMN_NAME2 + " ='" +name +"'";
        System.out.println("query" + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if(db !=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

}
