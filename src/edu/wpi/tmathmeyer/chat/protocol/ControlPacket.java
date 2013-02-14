package edu.wpi.tmathmeyer.chat.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ControlPacket implements Packet {

	public byte data;
	
	@Override
	public byte getPacketID() {
		return 0x15;
	}

	@Override
	public void write(DataOutputStream d) throws IOException {
		d.writeByte(0x15);
		d.writeByte(data);
	}
	
	public ControlPacket(byte data){
		this.data = data;
	}

	public ControlPacket(DataInputStream in) throws Exception{
		data = in.readByte();
	}
	
	/*
	 * 0x00 login failed
	 * 0x01 login successful
	 */

}
