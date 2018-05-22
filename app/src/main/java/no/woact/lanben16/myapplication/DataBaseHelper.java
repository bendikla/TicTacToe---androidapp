package no.woact.lanben16.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "highscores.db";
    public static final String TABLE_NAME = "highscore_data";
    public static final String COL0 = "_id";
    public static final String COL1 = "WINNERS";
    public static final String COL2 = "SCORE";

    public DataBaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE highscore_data" + " (_id INT PRIMARY KEY AUTOINCREMENT, WINNERS TEXT, SCORE INT)");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public boolean addData(String winner, int wins){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(2);
        contentValues.put(COL1, "Player: " + winner);
        contentValues.put(COL2, "Wins: "   + wins);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result== -1){
            return false;
        }
        else {
            return true;
        }
    }

    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void deleteDb(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE 1" +  " ORDER BY " + COL2 + " DESC", null);
        return data;
    }

}
