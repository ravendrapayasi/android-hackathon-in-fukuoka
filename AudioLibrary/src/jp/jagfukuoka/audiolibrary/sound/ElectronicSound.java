package jp.jagfukuoka.audiolibrary.sound;

import jp.jagfukuoka.audiolibrary.musicalscore.ElectronicNote;
import android.util.Log;

public abstract class ElectronicSound implements Sound{
	private int again;
	private int againCount;
	private short[] wave;
	private ElectronicNote waveNote;
	public void createAudio(ElectronicNote note){
		if( waveNote == null || !waveNote.equals(note)){
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
		againCount = 0;
		if(note.volume == 0f ){
			int size;
			if (note.time >0){
				size = Sound.SAMPLE_RATE * note.time /100;
				again = 1;
			}else{
				size = (int) (Sound.SAMPLE_RATE / note.interval);
				again = -1;
			}
			short[] blankValue = new short[size];
			for(int i=0;i<size;i++){
				blankValue[i] = 0;
			}
			return blankValue;
		}
		if(note.time <= 0){
			again = -1;
		}else{
			again = (int)(note.interval * note.time / 100);
		}
		short[] value = createWave(note.interval,note.volume);
		if(again != -1 && note.interval < singleWave){
			return value;
		}
		
		//1000 mhz を超えるとパフォーマンスが怪しいので周期数を増やす
		int repeat = (int) Math.ceil(note.interval/singleWave);
		
		int length = value.length;
		short[]values = new short[value.length * repeat];
		int size =0;
		for(int i=0;i<repeat;i++){
		    System.arraycopy(value.clone(), 0, values, size, length);
		    size += length;
		}
		return values;

	}
	private final float singleWave = 500f;
	
	
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
