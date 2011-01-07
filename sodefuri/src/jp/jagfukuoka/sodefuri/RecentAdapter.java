package jp.jagfukuoka.sodefuri;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecentAdapter extends ArrayAdapter<RecentBean> {

	private List<RecentBean> objects;
	private LayoutInflater inflater;

	public RecentAdapter(Context context, int resource, List<RecentBean> objects) {
		super(context, resource, objects);
		this.objects = objects;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ビューを受け取る
		View view = convertView;
		if (view == null) {
			// 受け取ったビューがnullなら新しくビューを生成
			view = inflater.inflate(R.layout.list_item, null);
			// 背景画像をセットする
//			view.setBackgroundResource(R.drawable.back);
		}
		// 表示すべきデータの取得
		RecentBean item = objects.get(position);
		if (item != null) {
			TextView screenName = (TextView) view.findViewById(R.id.toptext);
			screenName.setTypeface(Typeface.DEFAULT_BOLD);

			ImageView twitterIcon = (ImageView) view.findViewById(R.id.twitter_icon);
			if(twitterIcon != null){
				try {
					URL url = new URL(item.getImage());
					InputStream is = (InputStream) url.getContent();
					Drawable drawable = Drawable.createFromStream(is, "src");
					twitterIcon.setImageDrawable(drawable);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// スクリーンネームをビューにセット
			if (screenName != null) {
				screenName.setText(item.getScreenName());
			}

			// テキストをビューにセット
			TextView text = (TextView) view.findViewById(R.id.bottomtext);
			if (text != null) {
				text.setText(item.getDate().toString());
			}
		}
		return view;
	}

}
