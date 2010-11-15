package jp.jagfukuoka.eightbit.Sound;

public class Saw extends ElectronicSound {

	@Override
	protected void createWave(float interval, float volume) {
		double a = 1.0 / interval;
		double t = 0.0;
		double dt = 1.0 / Sound.SAMPLE_RATE;
		short shortVolume = (short) (Short.MAX_VALUE * volume);
		int waveLength = super.wave.length;		
		for (int i = 0; i < waveLength; i++, t += dt) {
			super.wave[i] = (short) (shortVolume * 2 * (t / a - Math.floor(t / a + 0.5)));
		}		
	}

}
