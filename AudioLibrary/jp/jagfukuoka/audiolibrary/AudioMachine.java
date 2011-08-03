package jp.jagfukuoka.audiolibrary;

import jp.jagfukuoka.audiolibrary.Buffer;
import jp.jagfukuoka.audiolibrary.sound.Sound;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;

public class AudioMachine {
	public AudioMachine(){
		this.isAllReady = false;
		mHandler = new Handler();
	}
	private int twice = 1;
	protected boolean isAllReady;
	public AudioMachine(boolean isAllReady){
		this.isAllReady = isAllReady;
		if(isAllReady){
			this.init();
		}		
		mHandler = new Handler();
	}
	public AudioMachine(boolean isAllReady,int bufferSize){
		this.isAllReady = isAllReady;
		this.twice = bufferSize;
		if(isAllReady){
			this.init();
		}
		mHandler = new Handler();
	}
	public void setTwice(int value){this.twice = value;}
	Handler mHandler;
	
	AudioTrack track;
	protected void init(){
		int bufferSize = android.media.AudioTrack.getMinBufferSize(Sound.SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT) * this.twice;
		this.track = new AudioTrack(AudioManager.STREAM_MUSIC, Sound.SAMPLE_RATE, Sound.CHANNEL_CONFIGURATION, Sound.ENCODING, bufferSize, AudioTrack.MODE_STREAM);
		try{
			this.track.play();
		}catch(Exception err){			
		}
	}
	private boolean isUpdateVolume = false;
	private float volume = 1f;
	public float SetVolume(float volume){
		this.volume = volume;
		this.isUpdateVolume=true;
		return volume;
	}
	
	protected class Decoder implements Runnable{
		private Buffer buffer;
		private Sound sound;
		protected Decoder (Buffer buffer,Sound sound){
			this.buffer=buffer;
			this.sound= sound;
		}
		@Override
		public void run() {
			short[] audio;
			do {
				try{
					audio = sound.getAudio();
					buffer.putValue(audio);
				}catch(Exception err){
					break;
				}
			}while (audio != null);
		}
	}
	protected class Player implements Runnable{
		private Buffer buffer;
		protected Player(Buffer buffer){
			this.buffer = buffer;
		}
		protected void loopWork(short[] audio){}
		@Override
		public void run(){
			short[] audio = buffer.getValue();
			isStop = false;
			while(audio!=null && !isStop){
				try{
					synchronized(mHandler){
						loopWork(audio);
						track.write(audio, 0, audio.length);
						if(isUpdateVolume){
							track.setStereoVolume(volume, volume);
							isUpdateVolume = false;
						}
						audio = buffer.getValue();
					}
				}catch(Exception err){
					break;
				}
			}
			isStop = true;
			if(!isAllReady){
				try{
					synchronized(mHandler){
						track.release();
					}
				}catch(Exception err){}
			}
		}
	}
	protected class PlayerNobuffer implements Runnable{
		private Sound sound;
		protected PlayerNobuffer(Sound sound){
			this.sound = sound;
		}
		protected void loopWork(short[] audio){}

		@Override
		public void run() {
			short[] audio = sound.getAudio();
			isStop = false;
			while (audio != null && !isStop) {
				loopWork(audio);				
				synchronized(mHandler){
					track.write(audio, 0, audio.length);
					if(isUpdateVolume){
						track.setStereoVolume(volume, volume);
						isUpdateVolume = false;
					}
					audio = sound.getAudio();
				}
			};
			isStop = true;
			if(!isAllReady){
				synchronized(mHandler){
					track.release();
				}
			}
		}
		
	}

	public void Play(final Sound sound){
		if(!isAllReady){
			init();
		}
		if(isStop){
			Buffer buffer = new Buffer();
			Decoder decoder = new Decoder(buffer, sound);
			Thread decodeThread = new Thread(decoder);
			decodeThread.start();
			Player player = new Player(buffer);
			Thread playerThread = new Thread(player);
			playerThread.start();
		}

	}

	public void PlayNobuffer(final Sound sound){
		if(!isAllReady){
			init();
		}
		if(isStop){
			PlayerNobuffer player = new PlayerNobuffer(sound);
			Thread playerThread = new Thread(player);
			playerThread.start();
		}

	}

	protected boolean isStop = true;
	public void Stop(){
		if (!isStop){
			synchronized(mHandler){
				track.stop();
				isStop = true;
			}
		}
		if(isAllReady){		
			synchronized(mHandler){
				track.release();
			}
		}
	}
	
	public void Release(){
		try{
			synchronized(mHandler){
				if(!isStop){
					track.stop();
					isStop = true;
					if(!isAllReady){
						track.release();
					}
				}
				if(isAllReady){
					track.release();
				}
				track = null;
			}
			mHandler = null;
 
		}catch(Exception err){
			return;
		}
	}
}
