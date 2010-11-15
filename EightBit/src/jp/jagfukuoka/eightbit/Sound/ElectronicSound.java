package jp.jagfukuoka.eightbit.Sound;

import jp.jagfukuoka.eightbit.musicalscore.ElectronicNote;

public abstract class ElectronicSound implements Sound{
	private int again;
	private int againCount;
	protected short[] wave;
	public void createAudio(ElectronicNote note){
		createAudio(note.time,note.interval,note.volume);
	}
	public void createAudio(int time,float interval,float volume){
		again = (int)(interval * time / 100);
		againCount = 0;
		this.wave = new short[(int) (Sound.SAMPLE_RATE / interval)];
		createWave(interval,volume);
	}
	protected abstract void createWave(float interval,float volume);
	@Override
	public short[] getAudio() {
		if(againCount<again){
			againCount++;
			return this.wave;
		}else{
			againCount = 0;
			return null;
		}
	}

	
}
