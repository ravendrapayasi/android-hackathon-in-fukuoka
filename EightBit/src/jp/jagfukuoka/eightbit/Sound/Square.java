package jp.jagfukuoka.eightbit.Sound;

public class Square implements Sound {

	private short[] audio;
	@Override
	public void createAudio(int time, int interval, float volume) {
		short[] squareWave = new short[SoundCommon.getSampleRate()];
		double t = 0.0;
		double dt = 1.0 / SoundCommon.getSampleRate();
		short maxValue = (short) ((Short.MAX_VALUE) * volume);
		short minValue = (short) ((Short.MIN_VALUE) * volume);

		for (int i = 0; i < squareWave.length; i++, t += dt) {
		    if (Math.sin(2.0 * Math.PI * t * interval) > 0) {
		        squareWave[i] = maxValue;
		    } else {
		        squareWave[i] = minValue;
		    }
		}
		this.audio = squareWave;
	}

	@Override
	public short[] getAudio() {
		return this.audio;
	}

}
