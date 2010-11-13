package jp.jagfukuoka.eightbit.Sound;

public class Saw implements Sound {

	private short[] audio;
	@Override
	public void createAudio(int time, int interval, int volume) {
		short[] sawWave = new short[time];
		double a = 1.0 / time;
		double t = 0.0;
		double dt = 1.0 / SoundCommon.getSampleRate();
		short shortVolume = (short) ((Short.MAX_VALUE)/100 * volume);

		for (int i = 0; i < sawWave.length; i++, t += dt) {
			sawWave[i] = (short) (shortVolume * 2 * (t / a - Math.floor(t / a + 0.5)));
		}

		this.audio = sawWave;
	}

	@Override
	public short[] getAudio() {
		// TODO Auto-generated method stub
		return this.audio;
	}

}
