package jp.jagfukuoka.eightbit.Sound;

import java.util.LinkedList;

import jp.jagfukuoka.eightbit.musicalscore.ElectronicNote;
import jp.jagfukuoka.eightbit.musicalscore.Note;
import android.util.Log;

public class DemoMusic implements Sound{

	private ElectronicNote[] music;
	public DemoMusic(){
		music = new ElectronicNote[33];
		int i=0;
		music[i++] = new ElectronicNote(5,Note.A5,0);
		music[i++] = new ElectronicNote(4,Note.A5,1f);
		music[i++] = new ElectronicNote(1,Note.A5,0);
		music[i++] = new ElectronicNote(4,Note.A5,1f);
		music[i++] = new ElectronicNote(1,Note.A5,0);
		music[i++] = new ElectronicNote(9,Note.A5,1f);
		music[i++] = new ElectronicNote(1,Note.A5,0);
		music[i++] = new ElectronicNote(9,Note.A5,1f);
		music[i++] = new ElectronicNote(1,Note.A5,0);
		music[i++] = new ElectronicNote(4,Note.A5,1f);
		music[i++] = new ElectronicNote(1,Note.A5,0);

		music[i++] = new ElectronicNote(5,Note.G5,1f);
		music[i++] = new ElectronicNote(5,Note.A5,1f);
		music[i++] = new ElectronicNote(5,Note.B5,1f);
		music[i++] = new ElectronicNote(5,Note.A5,1f);
		music[i++] = new ElectronicNote(5,Note.A5,0f);
		music[i++] = new ElectronicNote(5,Note.A5,1f);
		music[i++] = new ElectronicNote(5,Note.B5,1f);
		music[i++] = new ElectronicNote(5,Note.C6S,1f);

		music[i++] = new ElectronicNote(5,Note.A5,0);		
		music[i++] = new ElectronicNote(4,Note.A5,1f);
		music[i++] = new ElectronicNote(1,Note.A5,0);
		music[i++] = new ElectronicNote(4,Note.A5,1f);
		music[i++] = new ElectronicNote(1,Note.A5,0);
		music[i++] = new ElectronicNote(9,Note.A5,1f);
		music[i++] = new ElectronicNote(1,Note.A5,0);
		music[i++] = new ElectronicNote(9,Note.A5,1f);
		music[i++] = new ElectronicNote(1,Note.A5,0);
		music[i++] = new ElectronicNote(10,Note.C6S,1f);

		music[i++] = new ElectronicNote(5,Note.D6,1f);
		music[i++] = new ElectronicNote(5,Note.C6S,1f);
		music[i++] = new ElectronicNote(10,Note.A5,1f);
		music[i++] = new ElectronicNote(20,Note.G5S,1f);

		this.sound.createAudio(music[0]);
	}
	private ElectronicSound sound = new Saw();
	private int i = 0;

	public short[] getAllAudio(){
		LinkedList list = new LinkedList();
		for(short[] waves = getSepalateAudio();waves!=null;waves = getSepalateAudio()){
			for(short wave : waves){
				list.add(wave);
			}
		}
		short[] array= new short[list.size()];
		i=0;
		for(Object item:list){
			array[i] = (Short)item;
		}
		return array;
	}
	@Override
	public short[] getAudio() {
		return getSepalateAudio();
	}
	private short[] getSepalateAudio(){
		short[] sound = this.sound.getAudio();
		if(sound==null){
			i++;
			if(i>=music.length){
				i = 0;
				return null;
			}
			this.sound.createAudio(music[i]);
			sound = this.sound.getAudio();
		}
		return sound;
	}

}
