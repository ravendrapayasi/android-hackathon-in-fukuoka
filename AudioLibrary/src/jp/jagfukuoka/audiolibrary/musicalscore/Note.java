package jp.jagfukuoka.audiolibrary.musicalscore;


public abstract class Note {
	public float time;
	public float interval;
	public float volume;
	public final static float C5 = 523.25f;
	public final static float C5S = 554.37f;
	
    public final static float D5 = 587.33f;
    public final static float E5 = 659.26f;
    public final static float F5 = 698.46f;
    public final static float F5S = 739.98f;    
    public final static float G5 = 783.99f;
    public final static float G5S = 830.61f;
    public final static float A5 = 880.00f;
    public final static float B5 = 987.77f;
    public final static float C6 = C5*2;
    public final static float C6S = C5S*2;
    
    public final static float D6 = D5*2;
    public final static float E6 = E5*2;    
    public final static float F6 = F5*2;
    public final static float F6S = F5S*2;
    public final static float G6 = G5*2;
    public final static float G6S = G5S*2;

	public Note(float time, float interval, float volume){
		this.time = time;
		this.interval = interval;
		this.volume = volume;
	}

}
