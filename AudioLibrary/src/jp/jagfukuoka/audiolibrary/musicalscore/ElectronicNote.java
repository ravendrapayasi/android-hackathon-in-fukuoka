package jp.jagfukuoka.audiolibrary.musicalscore;

public class ElectronicNote {
	public int time;
	public float interval;
	public float volume;
	
	public ElectronicNote(int time, float interval, float volume){
		this.time = time;
		this.interval = interval;
		this.volume = volume;
	}
	public boolean equals(Note n){
		if(this.time == n.time && this.interval== n.interval && this.volume == n.volume){
			return true;
		}
		return false;
	}
}
