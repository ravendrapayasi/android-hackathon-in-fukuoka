package jp.jagfukuoka.eightbit.Sound;

public interface Sound {
	void createAudio(int time,float interval,float volume);
	short[] getAudio();
}
