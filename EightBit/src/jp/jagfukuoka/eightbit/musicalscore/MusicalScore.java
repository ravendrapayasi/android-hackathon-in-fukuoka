package jp.jagfukuoka.eightbit.musicalscore;

import jp.jagfukuoka.eightbit.Sound.Sound;

public class MusicalScore implements Sound{
    public float sixteenthNote;
    public float eightNote;
    public float quaterNote;
    public float halfNote;
    public float wholeNote;
    public Note[] notes;
    
    public MusicalScore(){
    	ChangeTempo(60);
    }
    public void CreateMusic(Note[] notes){
    	this.notes = notes;
    }
    public void ChangeTempo(int tempo){
    	sixteenthNote = 15/tempo;
    	eightNote = sixteenthNote*2;
    	quaterNote = eightNote * 2;
    	halfNote = quaterNote * 2;
    	wholeNote = halfNote * 2;
    }
	@Override
	public short[] getAudio() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
