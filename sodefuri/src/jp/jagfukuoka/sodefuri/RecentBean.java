package jp.jagfukuoka.sodefuri;

import java.util.Date;

/**
 * すれ違った一つのデータ
 * @author shikajiro
 *
 */
public class RecentBean {
	private String screenName;
	private String image;
	private Date date;

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
}
