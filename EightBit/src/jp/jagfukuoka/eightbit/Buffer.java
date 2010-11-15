package jp.jagfukuoka.eightbit;

public class Buffer {
	private short[] value;
	private boolean isEmpty = true;
	public synchronized void putValue(short[] value){
		while(!isEmpty){
			try{
				wait();
			}catch(InterruptedException e){}
		}
		notifyAll();
		isEmpty = false;
		this.value = value;
	}
	public synchronized short[] getValue(){
		while(isEmpty){
			try{
				wait();
			}catch(InterruptedException e){}
		}
		notifyAll();
		isEmpty = true;
		return value;
	}
}
