package jp.jagfukuoka.eightbit.Sound;

public class Rest implements Sound{
	private short[] wave;
	public void createAudio(int time){
		this.wave = new short[time * Sound.SAMPLE_RATE];
		int waveLength = wave.length;
		for(int i=0;i<waveLength;i++){
			this.wave[i] =0;
		}
	}
	@Override
	public short[] getAudio() {
		// TODO Auto-generated method stub
		return this.wave;
	}

}
