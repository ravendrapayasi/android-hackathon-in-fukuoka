package jp.jagfukuoka.eightbit.Sound;

import java.util.Random;

public class WhiteNoise implements Sound {

	private byte[] audio;
	@Override
	public void createAudio(int time,int interval) {
		// TODO Auto-generated method stub
		byte[] whiteNoise = new byte[44100*time];
		Random r = new Random();
		r.nextBytes(whiteNoise);
		this.audio = whiteNoise;
	}
	@Override
	public byte[] getAudio(){
		return this.audio;
	}

}
