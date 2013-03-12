package edu.wpi.tmathmeyer.chat.test;

public class PseudoInputStream{
	
	PseudoIO pio;
	
	public PseudoInputStream(PseudoIO pio){
		this.pio = pio;
	}
	
	
	public byte readByte(){
		return pio.getByte();
	}
	
	public short readShort(){
		return pio.getShort();
	}

}
