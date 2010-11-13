package jp.jagfukuoka.eightbit.Sound;

public class Triangle implements Sound {

	private short[] audio;
	@Override
	public void createAudio(int time, float interval, float volume) {
		short[] triangleWave = SoundCommon.getAudioBlankArray(time);

		double a = 1.0 / interval;
		double t = 0.0;
		double dt = 1.0 / SoundCommon.getSampleRate();
		short maxValue = (short) (Short.MAX_VALUE * volume);

		for (int i = 0; i < triangleWave.length; i++, t += dt) {
		    triangleWave[i] = (short) (Math.abs((maxValue * 2 * (t / a - Math.floor(t / a + 0.5)))) * 2 - maxValue);
		}
		this.audio = triangleWave;
	}

	@Override
	public short[] getAudio() {
		return this.audio;
	}


}
