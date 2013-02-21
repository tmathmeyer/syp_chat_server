package edu.wpi.tmathmeyer.chat.test;

import java.io.DataInputStream;
import java.io.InputStream;

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
