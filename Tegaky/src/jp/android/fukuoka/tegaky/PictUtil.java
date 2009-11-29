package jp.android.fukuoka.tegaky;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class PictUtil {
	public File getSavePath() {
	    File path;
	    if (hasSDCard()) { // SD card
	        path = new File(getSDCardPath() + "/Tegaky/");
	        path.mkdir();
	    } else { 
	        path = Environment.getDataDirectory();
	    }
	    return path;
	}
	public String getCacheFilename() {
	    File f = getSavePath();
	    return f.getAbsolutePath() + "/cache.png";
	}

	public Bitmap loadFromFile(String filename) {
	    try {
	        File f = new File(filename);
	        if (!f.exists()) { return null; }
	        Bitmap tmp = BitmapFactory.decodeFile(filename);
	        return tmp;
	    } catch (Exception e) {
	        return null;
	    }
	}
	public Bitmap loadFromCacheFile() {
	    return this.loadFromFile(getCacheFilename());
	}
	public void saveToCacheFile(Bitmap bmp) {
	    this.saveToFile(getCacheFilename(),bmp);
	}
	public void saveToFile(String filename,Bitmap bmp) {
	    try {
	        FileOutputStream out = new FileOutputStream(filename);
	        bmp.compress(CompressFormat.PNG, 100, out);
	        out.flush();
	        out.close();
	    } catch(Exception e) {}
	}

	public boolean hasSDCard() { // SDカードがあるか?
	    String status = Environment.getExternalStorageState();
	    return status.equals(Environment.MEDIA_MOUNTED);
	}
	public String getSDCardPath() {
	    File path = Environment.getExternalStorageDirectory();
	    return path.getAbsolutePath();
	}

}