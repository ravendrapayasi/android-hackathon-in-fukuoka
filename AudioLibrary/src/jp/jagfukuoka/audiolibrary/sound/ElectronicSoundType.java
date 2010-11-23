package jp.jagfukuoka.audiolibrary.sound;

public enum ElectronicSoundType {
	SAW(0),
	SIN(1),
	SQUARE(2),
	TRIANGLE(3);
	private int value;
	private ElectronicSoundType(int value){
		this.value = value;
	}
	public int getValue(){
		return this.value;
	}
	public ElectronicSound toElectronicSound(){
		switch(this){
		case SAW:
			return new Saw();
		case SIN:
			return new Sin();
		case SQUARE:
			return new Square();
		case TRIANGLE:
			return new Triangle();
		}
		return null;
	}
	public static ElectronicSound toElectronicSound(int typeValue){
		ElectronicSoundType type = toElectronicSoundType(typeValue);
		return type.toElectronicSound();
	}
	public static ElectronicSoundType toElectronicSoundType(int typeValue){
		if(typeValue == SAW.getValue()){
			return ElectronicSoundType.SAW;
		}
		if(typeValue == SIN.getValue()){
			return ElectronicSoundType.SIN;
		}
		if(typeValue == SQUARE.getValue()){
			return ElectronicSoundType.SQUARE;
		}
		if(typeValue == TRIANGLE.getValue()){
			return ElectronicSoundType.TRIANGLE;
		}
		return null;
	}
}
