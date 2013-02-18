package edu.wpi.tmathmeyer.chat.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PrivateMessagePacket implements Packet{

	private String from;
	private String message;
	private int h,m,s;
	
	@Override
	public byte getPacketID() {
		return 0x05;
	}

	@Override
	public void write(DataOutputStream d) throws IOException {
		d.write(this.getPacketID());
		
		d.writeShort(from.length());
		d.writeBytes(from);
		
		d.writeShort(message.length());
		d.writeBytes(message);
		
		d.writeShort(h);
		d.writeShort(m);
		d.writeShort(s);
	}
	
	
	public PrivateMessagePacket(DataInputStream di) throws IOException{
		short fromLength = di.readShort();
		byte[] b = new byte[fromLength];
		for(int i=0;i<fromLength;i++)b[i]=di.readByte();
		from = new String(b);
		
		short msgLength = di.readShort();
		byte[] d = new byte[msgLength];
		for(int i=0;i<msgLength;i++)d[i]=di.readByte();
		message = new String(d);
				
		h = di.readShort();
		m = di.readShort();
		s = di.readShort();
		
	}
	
	public PrivateMessagePacket(String from, String message){
		this.from = from;
		this.message = message;
		Calendar c = GregorianCalendar.getInstance();
		this.h = c.get(Calendar.HOUR);
		this.m = c.get(Calendar.MINUTE);
		this.s = c.get(Calendar.SECOND);
	}

}
