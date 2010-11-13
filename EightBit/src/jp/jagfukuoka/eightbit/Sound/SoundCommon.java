package jp.jagfukuoka.eightbit.Sound;

public class SoundCommon {
	static protected int getSampleRate(){return 44100;}
	static protected short[] getAudioBlankArray(int size){	
		return new short[getSampleRate() * size / 10];		
	}
}
