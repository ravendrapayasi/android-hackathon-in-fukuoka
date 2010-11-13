package jp.jagfukuoka;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class RecentContentProvider extends ContentProvider {
	DatabaseHelper databaseHelper;
	@Override
	public boolean onCreate() {
		databaseHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
	    SQLiteDatabase db = databaseHelper.getReadableDatabase();
	    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	    qb.setTables("test"); //テーブル名
	    Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null);
	    return c;	
	}

	@Override
	public int update(Uri uri, ContentValues contentvalues, String s, String[] as) {
		throw new UnsupportedOperationException("Not supported by this provider");
	}


	@Override
	public Uri insert(Uri uri, ContentValues contentvalues) {
	    SQLiteDatabase db = databaseHelper.getWritableDatabase();
	    db.insert("test", null, contentvalues); //testがテーブル名
	    return null;	}

	@Override
	public int delete(Uri uri, String s, String[] as) {
		throw new UnsupportedOperationException("Not supported by this provider");
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}
}
