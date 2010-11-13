package jp.jagfukuoka.eightbit.Sound;

public interface Sound {
	void createAudio(int time,int interval,int volume);
	short[] getAudio();
}
