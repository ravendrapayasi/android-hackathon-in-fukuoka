package jp.jagfukuoka.eightbit;

import jp.jagfukuoka.eightbit.Sound.*;
import android.app.Activity;
import android.os.Bundle;

public class main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.audio = new AudioMachine();
        Sound sound = new Sin();
        sound.createAudio(20,400);
        this.audio.Play(sound);
    }
    private AudioMachine audio;
    @Override
    public void onStop(){
    	this.audio.Stop();
    }
}