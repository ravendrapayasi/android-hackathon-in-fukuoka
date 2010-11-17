package jp.jagfukuoka.eightbit.Sound;

public class Thereminvox implements Sound{

	ElectronicSound electronicSound;
	public Thereminvox(ElectronicSound electronicSound){
		this.electronicSound = electronicSound;
	}
	@Override
	public short[] getAudio() {
		return this.electronicSound.getAudio();
	}

}
