package jp.android.fukuoka.scorecaster;

import java.util.List;

import jp.android.fukuoka.scorecaster.R.id;
import jp.android.fukuoka.scorecaster.db.DatabaseHelper;
import jp.android.fukuoka.scorecaster.db.Score;
import jp.android.fukuoka.scorecaster.db.ScoreDao;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScoreCaster extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //　画面表示の初期化
        this.initialize();
        
        //////////////////////////////////////////////////////
        // 得点関連の動作定義
        //////////////////////////////////////////////////////
		// Aチームスコアボタンの動作定義
        final Button aScoreButton = (Button) findViewById(id.ATeamScoreButton);
		final TextView aScore = (TextView) findViewById(id.ATeamScore);
		aScoreButton.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				int score = Integer.valueOf(aScore.getText().toString());
				aScore.setText(String.valueOf(score + 1));
			}
		});
        
		// Bチームスコアボタンの動作定義
		final Button bScoreButton = (Button) findViewById(id.BTeamScoreButton);
		final TextView bScore = (TextView) findViewById(id.BTeamScore);
		bScoreButton.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				int score = Integer.valueOf(aScore.getText().toString());
				bScore.setText(String.valueOf(score + 1));
			}
		});
        
        //////////////////////////////////////////////////////
        // タイマー関連の動作定義
        //////////////////////////////////////////////////////
        // ボタンの動作定義
        final Button start = (Button) findViewById(id.Start);
        final Button stop = (Button) findViewById(id.Stop);
        final Button reset = (Button) findViewById(id.Reset);
		
		// スタートボタンの動作定義
        start.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				start.setVisibility(View.GONE);
				stop.setVisibility(View.VISIBLE);
				
				// TODO カウントダウンの開始
			}
		});
		
		// ストップボタンの動作定義
        stop.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				start.setVisibility(View.VISIBLE);
				stop.setVisibility(View.GONE);
				
				// TODO カウントダウンの停止
			}
		});
        
		// リセットボタンの動作定義
        reset.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				initialize();
			}
		});
    }

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0,0,0, "設定").setIcon(android.R.drawable.ic_menu_preferences);
		menu.add(0,1,1, "DB追加").setIcon(android.R.drawable.ic_menu_edit);
		menu.add(0,2,2, "DB取得").setIcon(android.R.drawable.ic_menu_edit);
		menu.add(0,3,3, "スコア一覧").setIcon(android.R.drawable.ic_menu_edit);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		switch (item.getItemId()) {
			case 0://設定
				//設定画面への遷移
				break;
			case 1://DBテスト追加
				DatabaseHelper dbHelper = new DatabaseHelper(this);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ScoreDao scoreDao = new ScoreDao(db);
				Score score = new Score(10, 15);
				scoreDao.insert(score);
				Log.v("debug", "insert: " + String.format("score1: %d, score2: %d", 
						score.getScore_team1(),score.getScore_team2()));
				break;
			case 2://DBテスト取得
				DatabaseHelper dbHelper2 = new DatabaseHelper(this);
				SQLiteDatabase db2 = dbHelper2.getWritableDatabase();
				ScoreDao scoreDao2 = new ScoreDao(db2);
				List<Score> scoreList = scoreDao2.findAll();
				Log.v("debug", "size: " + scoreList.size());
				for (Score score2 : scoreList) {
					Log.v("debug", String.format("id: %d, score1: %d, score2: %d", 
							score2.getRowid(), score2.getScore_team1(),score2.getScore_team2()));
				}
				break;
			case 3://スコア一覧画面
				
		}
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void initialize() {
		// 得点の初期化
		TextView aScore = (TextView) findViewById(id.ATeamScore);
		TextView bScore = (TextView) findViewById(id.BTeamScore);
		aScore.setText("0");
		bScore.setText("0");
		
		// 時間の初期化
		TextView time = (TextView) findViewById(id.Time);
		// TODO SharedPreferenceからの時間読み込み
		
		// ボタン表示の初期化
		Button start = (Button) findViewById(id.Start);
		Button stop = (Button) findViewById(id.Stop);
		start.setVisibility(View.VISIBLE);
		stop.setVisibility(View.GONE);
	}
}