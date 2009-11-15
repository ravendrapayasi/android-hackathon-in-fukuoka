package jp.android.fukuoka.scorecaster;

import java.util.ArrayList;
import java.util.List;

import jp.android.fukuoka.scorecaster.db.DatabaseHelper;
import jp.android.fukuoka.scorecaster.db.Score;
import jp.android.fukuoka.scorecaster.db.ScoreDao;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScoreActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
		ListView listview_diary = (ListView)findViewById(R.id.listview_score);

		DatabaseHelper dbHelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ScoreDao scoreDao = new ScoreDao(db);
		List<Score> scoreList = scoreDao.findAll();
		db.close();
		
		ArrayList<String> strList = new ArrayList<String>();
		for (Score score2 : scoreList) {
			strList.add(String.format("id: %d, score1: %d, score2: %d", 
					score2.getRowid(), score2.getScore_team1(),score2.getScore_team2()));
		}
		String[] strArray = new String[strList.size()];
		String[] values = strList.toArray(strArray);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text, values){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				return view;
			}
		};
    }
}