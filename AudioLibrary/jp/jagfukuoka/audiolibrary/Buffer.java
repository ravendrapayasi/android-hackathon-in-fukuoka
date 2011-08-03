package jp.jagfukuoka.audiolibrary;

public class Buffer {
	private short[] value;
	private boolean isEmpty = true;
	public synchronized void putValue(short[] value){
		while(!isEmpty){
			try{
				wait();
			}catch(InterruptedException e){}
		}
		isEmpty = false;
		this.value = value;
		notifyAll();
	}
	public synchronized short[] getValue(){
		while(isEmpty){
			try{
				wait();
			}catch(InterruptedException e){}
		}
		isEmpty = true;
		notifyAll();
		return value;
	}
}
