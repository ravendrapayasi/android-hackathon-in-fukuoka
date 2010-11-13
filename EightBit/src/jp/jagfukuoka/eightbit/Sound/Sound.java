package jp.jagfukuoka.eightbit.Sound;

public interface Sound {
	void createAudio(int time,int interval,float volume);
	short[] getAudio();
}
