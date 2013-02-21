package edu.wpi.tmathmeyer.chat.test;

public class PseudoOutputStream {
PseudoIO pio;
	
	public PseudoOutputStream(PseudoIO pio){
		this.pio = pio;
	}
	
	
	public void writeBytes(String s){
		for (byte b : s.getBytes()){
			//System.out.println(b);
			pio.addByte(b);
		}
	}
	
	public void writeByte(int i){
		pio.addByte((byte) i);
	}
	
	public void writeShort(short s){
		this.pio.addShort(s);
	}


	public void writeShort(int length) {
		this.writeShort((short)length);
	}
	
	public void flush(){}
}
