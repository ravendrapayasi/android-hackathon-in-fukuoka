package jp.jagfukuoka.eightbit.Sound;

public class Sin implements Sound {

	private byte[] audio;
	@Override
	public void createAudio(int time,int interval){
		
		byte[] sinWave = new byte[44100 * time];
		double freq = interval;
		double t = 0.0;
		double dt = 1.0 / 44100;

		for (int i = 0; i < sinWave.length; i++, t += dt) {
		    sinWave[i] = (byte) (Short.MAX_VALUE * Math.sin(2.0 * Math.PI * t * freq));
		}
		this.audio = sinWave;
	}
	@Override
	public byte[] getAudio(){
		return this.audio;
	}

}
