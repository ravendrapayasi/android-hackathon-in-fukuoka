package jp.jagfukuoka.eightbit.Sound;

public class Sin extends ElectronicSound {

	private short[] audio;
	@Override
	protected void createWave(float interval, float volume) {
		double freq = interval;
		double t = 0.0;
		double dt = 1.0 / Sound.SAMPLE_RATE;
		short shortVolume = (short) ((Short.MAX_VALUE) * volume);
		int waveLength = super.wave.length;		
		for (int i = 0; i < waveLength; i++, t += dt) {
		    super.wave[i] = (short) (shortVolume * Math.sin(2.0 * Math.PI * t * freq));
		}
	}

}
