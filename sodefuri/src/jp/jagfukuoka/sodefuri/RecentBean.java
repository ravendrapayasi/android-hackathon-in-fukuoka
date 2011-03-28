package jp.jagfukuoka.sodefuri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import jp.jagfukuoka.sodefuri.server.twitter.TwitterRequest;

/**
 * すれ違った一つのデータ
 * @author shikajiro
 *
 */
public class RecentBean {
	private String screenName;
	private String image;
	private Date date;
	private String profile;
	
	public RecentBean(String screenName, Date date, String profile, String image) {
		this.screenName = screenName;
		this.date = date;
		this.profile = profile;
		this.image = image;
	}
	
	public static List<RecentBean> createBeans(Context context, List<String> list){
		List<RecentBean> beans = new ArrayList<RecentBean>();
		for (String str : list) {
			String image = TwitterRequest.getImageUrl(context, str);
			String profile = TwitterRequest.getProfile(context, str);
			RecentBean recentBean = new RecentBean(str, new Date(), profile, image);
			beans.add(recentBean);
		}
		return beans;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getProfile() {
		return profile;
	}
}
