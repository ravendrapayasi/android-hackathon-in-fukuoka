package jp.jagfukuoka.audiolibrary;

import jp.jagfukuoka.audiolibrary.Buffer;
import jp.jagfukuoka.audiolibrary.sound.Sound;

public class AudioMachine2 extends AudioMachine{
	public AudioMachine2(){
		super();
	}
	public AudioMachine2(boolean isAllReady){
		super(isAllReady);
	}
	public AudioMachine2(boolean isAllReady,int bufferSize){
		super(isAllReady,bufferSize);
	}
		
	
	protected class Player extends AudioMachine.Player{
		protected Player(Buffer buffer,UseWave guest){
			super(buffer);
			this.guest = guest;
		}
		protected Player(Buffer buffer) {
			super(buffer);
			// TODO Auto-generated constructor stub
		}
		@Override
		protected void loopWork(short[] audio){
			guest.setWave(audio);
		}
		private UseWave guest;
	}
	protected class PlayerNobuffer extends AudioMachine.PlayerNobuffer{
		private UseWave guest;
		protected PlayerNobuffer(Sound sound,UseWave guest){
			super(sound);
			this.guest = guest;
		}
		@Override
		protected void loopWork(short[] audio){
			guest.setWave(audio);
		}
		
	}

	public void Play(final Sound sound,UseWave guest){
		if(!isAllReady){
			init();
		}
		if(isStop){
			Buffer buffer = new Buffer();
			Decoder decoder = new Decoder(buffer, sound);
			Thread decodeThread = new Thread(decoder);
			decodeThread.start();
			Player player = new Player(buffer, guest);
			Thread playerThread = new Thread(player);
			playerThread.start();
		}

	}

	public void PlayNobuffer(final Sound sound,UseWave guest){
		if(!isAllReady){
			init();
		}
		if(isStop){
			PlayerNobuffer player = new PlayerNobuffer(sound,guest);
			Thread playerThread = new Thread(player);
			playerThread.start();
		}

	}
}
