package jp.jagfukuoka.eightbit.Sound;
import android.media.AudioFormat;

public interface Sound {
	short[] getAudio();
	static final public int SAMPLE_RATE = 44100;
	static final public int CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_CONFIGURATION_DEFAULT;
	static final public int ENCODING = AudioFormat.ENCODING_DEFAULT;
	

}
