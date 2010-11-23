package jp.jagfukuoka.audiolibrary.sound;

public class Sin extends ElectronicSound {

	@Override
	protected short[] createWave(float interval, float volume) {
		short[] wave = new short[(int) (Sound.SAMPLE_RATE / interval)];
		
		double freq = interval;
		double t = 0.0;
		double dt = 1.0 / Sound.SAMPLE_RATE;
		short shortVolume = (short) ((Short.MAX_VALUE) * volume);
		int waveLength = wave.length;		
		for (int i = 0; i < waveLength; i++, t += dt) {
		    wave[i] = (short) (shortVolume * Math.sin(2.0 * Math.PI * t * freq));
		}
		return wave;
	}

}
