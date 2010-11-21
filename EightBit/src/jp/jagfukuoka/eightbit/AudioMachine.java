package jp.jagfukuoka.eightbit;


import jp.jagfukuoka.eightbit.Sound.Sound;

import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class AudioMachine {
	
	public AudioMachine(){
		this.init();
	}
	
	AudioTrack track;
	private void init(){		
		int bufferSize = 16000;//android.media.AudioTrack.getMinBufferSize(Sound.SAMPLE_RATE, Sound.CHANNEL_CONFIGURATION, Sound.ENCODING);
		this.track = new AudioTrack(AudioManager.STREAM_MUSIC, Sound.SAMPLE_RATE, Sound.CHANNEL_CONFIGURATION, Sound.ENCODING, bufferSize, AudioTrack.MODE_STREAM);		
		this.track.play();		
	}
	public void restart(){
		this.track.play();
	}
	public void SetVolume(float volume){
		this.track.setStereoVolume(volume, volume);
	}
	private class Decoder implements Runnable{

		private Buffer buffer;
		private Sound sound;
		private Decoder (Buffer buffer,Sound sound){
			this.buffer=buffer;
			this.sound= sound;
		}
		@Override
		public void run() {
			short[] audio;
			do {
				audio = sound.getAudio();
				buffer.putValue(audio);
			}while (audio != null);
		}
	}
	private class Player implements Runnable{
		private Buffer buffer;
		private Player(Buffer buffer){
			this.buffer = buffer;
		}
		@Override
		public void run(){
			short[] audio = buffer.getValue();
			while(audio!=null){
				track.write(audio, 0, audio.length);						
				audio = buffer.getValue();
			}
			isStop = true;
			track.stop();
			track.release();
		}
	}
	
	public void Play(final Sound sound){
		if(isStop){
			init();
			this.isStop = false;		
		}
		Buffer buffer = new Buffer();
		Decoder decoder = new Decoder(buffer, sound);
		Thread decodeThread = new Thread(decoder);
		decodeThread.start();
		Player player = new Player(buffer);
		Thread playerThread = new Thread(player);
		playerThread.start();

	}

	public void Pause(){
		if(!isStop){
			track.pause();
		}
	}
	private boolean isStop = false;
	public void Stop(){
		if (!isStop){
			track.stop();
			track.release();
			isStop = true;
		}
	}
}
