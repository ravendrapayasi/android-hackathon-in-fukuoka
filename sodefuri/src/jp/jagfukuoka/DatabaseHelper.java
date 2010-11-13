package jp.jagfukuoka;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper {
    DatabaseHelper(Context context) {
        super(context, "test.db", null, 1);
    }
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE test ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                + "name TEXT,"
                + "description TEXT"
                + ");");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	// TODO Auto-generated method stub
    	db.execSQL("DROP TABLE IF EXISTS test");
    	onCreate(db);
	}

}
