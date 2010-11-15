package jp.jagfukuoka.eightbit.Sound;

public class SoundCommon {
	static protected short[] getAudioBlankArray(int size){	
		return new short[Sound.SAMPLE_RATE * size / 16];		
	}
	static protected short[] getAudioBlankArray(float interval){	
		return new short[(int) (Sound.SAMPLE_RATE / interval)];		
	}
	
}
