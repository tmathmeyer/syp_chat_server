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
	private String messageHex,userHex;
	
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
		
		d.writeChars(userHex);
		d.writeChars(messageHex);
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
		
		char[] uh = new char[6];
		char[] mh = new char[6];
		for(int i=0;i<6;i++)uh[i]=di.readChar();
		for(int i=0;i<6;i++)mh[i]=di.readChar();
		
		this.messageHex = new String(mh);
		this.userHex = new String(uh);
		
	}
	
	public PrivateMessagePacket(String from, String message){
		this.from = from;
		this.message = message;
		Calendar c = GregorianCalendar.getInstance();
		this.h = c.get(Calendar.HOUR);
		this.m = c.get(Calendar.MINUTE);
		this.s = c.get(Calendar.SECOND);
	}

	/**
	 * @return the messageHex
	 */
	public String getMessageHex() {
		return messageHex;
	}

	/**
	 * @param messageHex the messageHex to set
	 */
	public void setMessageHex(String messageHex) {
		this.messageHex = messageHex;
	}

	/**
	 * @return the userHex
	 */
	public String getUserHex() {
		return userHex;
	}

	/**
	 * @param userHex the userHex to set
	 */
	public void setUserHex(String userHex) {
		this.userHex = userHex;
	}

}
