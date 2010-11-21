package jp.jagfukuoka.eightbit.Sound;

public class Saw extends ElectronicSound {

	@Override
	protected short[] createWave(float interval, float volume) {
		short[] wave = new short[(int) (Sound.SAMPLE_RATE / interval)];
		double a = 1.0 / interval;
		double t = 0.0;
		double dt = 1.0 / Sound.SAMPLE_RATE;
		short shortVolume = (short) (Short.MAX_VALUE * volume);
		int waveLength = wave.length;		
		for (int i = 0; i < waveLength; i++, t += dt) {
			wave[i] = (short) (shortVolume * 2 * (t / a - Math.floor(t / a + 0.5)));
		}		
		return wave;
	}

}
