package jp.jagfukuoka.eightbit.musicalscore;

public class ElectronicNote {
	public int time;
	public float interval;
	public float volume;
	
	public ElectronicNote(int time, float interval, float volume){
		this.time = time*3;
		this.interval = interval;
		this.volume = volume;
	}
}
