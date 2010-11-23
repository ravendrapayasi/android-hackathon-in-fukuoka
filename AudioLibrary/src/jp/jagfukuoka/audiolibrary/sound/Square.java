package jp.jagfukuoka.audiolibrary.sound;

public class Square extends ElectronicSound {
	
	@Override
	protected short[] createWave(float interval, float volume) {
		short[] wave = new short[(int) (Sound.SAMPLE_RATE / interval)];
		double t = 0.0;
		double dt = 1.0 / Sound.SAMPLE_RATE;
		short maxValue = (short) ((Short.MAX_VALUE) * volume);
		short minValue = (short) ((Short.MIN_VALUE) * volume);
		
		int waveLength = wave.length;
		for (int i = 0; i < waveLength; i++, t += dt) {
		    if (Math.sin(2.0 * Math.PI * t * interval) > 0) {
		        wave[i] = maxValue;
		    } else {
		        wave[i] = minValue;
		    }
		}
		return wave;
	}


}
