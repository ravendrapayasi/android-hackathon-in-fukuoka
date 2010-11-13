package jp.jagfukuoka.eightbit.Sound;

public class Sin implements Sound {

	private short[] audio;
	@Override
	public void createAudio(int time,float interval,float volume){
		
		short[] sinWave = SoundCommon.getAudioBlankArray(time);
		double freq = interval;
		double t = 0.0;
		double dt = 1.0 / SoundCommon.getSampleRate();
		short shortVolume = (short) ((Short.MAX_VALUE) * volume);

		for (int i = 0; i < sinWave.length; i++, t += dt) {
		    sinWave[i] = (short) (shortVolume * Math.sin(2.0 * Math.PI * t * freq));
		}
		this.audio = sinWave;
	}
	@Override
	public short[] getAudio(){
		return this.audio;
	}

}
