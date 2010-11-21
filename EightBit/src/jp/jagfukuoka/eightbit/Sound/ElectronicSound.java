package jp.jagfukuoka.eightbit.Sound;

import jp.jagfukuoka.eightbit.musicalscore.ElectronicNote;

public abstract class ElectronicSound implements Sound{
	private int again;
	private int againCount;
	private short[] wave;
	private ElectronicNote waveNote;
	public void createAudio(ElectronicNote note){
		if(waveNote == null || !waveNote.equals(note)){
			waveNote = note;
			this.wave = createWaveShorts(note);			
		}
		isStop = false;
	}
	
	public void createAudio(int time,float interval,float volume){
		createAudio(new ElectronicNote(time,interval,volume));
	}
	public void stop(){
		isStop = true;
	}
	private short[] createWaveShorts(ElectronicNote note){
		if(note.time <= 0){
			again = -1;
		}else{
			again = (int)(note.interval * note.time / 100);
		}
		againCount = 0;
		return createWave(note.interval,note.volume);		
	}
	
	
	private boolean isStop;
	protected abstract short[] createWave(float interval,float volume);
	@Override
	public short[] getAudio() {

		if(isStop){
			return null;
		}
		if(again==-1){
			return this.wave;
		}
		if(againCount<again){
			againCount++;
			return this.wave;
		}
		againCount = 0;
		return null;
	}

	
}
