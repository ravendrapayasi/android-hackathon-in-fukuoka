package jp.jagfukuoka.sodefuri.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * すれ違ったbluetoothのmac_addressを保存するContentProvider
 * 
 * @author shikajiro
 * 
 */
public class RecentContentProvider extends ContentProvider {
	public static final Uri CONTENT_URI = Uri
			.parse("content://jp.jagfukuoka.sodefuri.provider.recentcontentprovider");
	public static final String MAC_ADDRESS = "mac_address";
	public static final String SCREEN_NAME = "screen_name";
	public static final String TIME = "time";
	
	DatabaseHelper databaseHelper;

	@Override
	public boolean onCreate() {
		databaseHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentvalues) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.insert("test", null, contentvalues);
		getContext().getContentResolver().notifyChange(uri, null);
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables("test");
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues contentvalues, String s,
			String[] as) {
		// TODO
		throw new UnsupportedOperationException(
				"Not supported by this provider");
	}

	@Override
	public int delete(Uri uri, String s, String[] as) {
		databaseHelper.onUpgrade(databaseHelper.getWritableDatabase(), 0, 0);
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	/**
	 * DBへアクセスするヘルパークラス
	 * 
	 * @author shikajiro
	 * 
	 */
	private class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, "test.db", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE test (" + BaseColumns._ID
					+ " INTEGER PRIMARY KEY,"
					+ RecentContentProvider.MAC_ADDRESS + " TEXT" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS test");
			onCreate(db);
		}
	}
}
