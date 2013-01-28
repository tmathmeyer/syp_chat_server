package protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessagePacket implements Packet{

	private DataInputStream in;
	private short messageLength;
	private byte messageGroup;
	private short hour=0, minute=0, second=0;
	private String message, username;
	
	
	/**
	 * EXAMPLE PACKET
	 * 
	 * [----packetID: 0x01--------] [---message packet---------]
	 * [----messageGroup: 0x00----] [---the default channel----]
	 * [----hour: 5---------------] [---time-------------------]
	 * [----minute: 14------------] [---time-------------------]
	 * [----second: 37------------] [---time-------------------]
	 * [----messageLength: 10-----] [---ten character message--]
	 * [----message: Hey there!---] [---Hey there!-------------]
	 * 
	 * END EXAMPLE
	 * @param in the DataInputStream from which byte information is being read
	 * @throws IOException if no data can be read from the data input stream, an IO exception is thrown
	 */
	public MessagePacket(DataInputStream in) throws IOException{
		int ID = (int) (Math.random()*10000);
		System.out.println("a message packet has been created with ID: "+ID);
		this.in = in;
		this.setMessageGroup(in.readByte());
		this.setHour(in.readShort());
		this.setMinute(in.readShort());
		this.setSecond(in.readShort());
		this.setMessageLength(in.readShort());
		char[] msg = new char[this.getMessageLength()];
		for(int i = 0; i < msg.length; i++)
			msg[i] = in.readChar();
		this.setMessage(new String(msg));
		short unameLength = in.readShort();
		char[] user = new char[unameLength];
		for(int i = 0; i < user.length; i++)
			user[i] = in.readChar();
		this.setUsername(new String(user));
		System.out.println("the packet ID: "+ID+" has been recieved in full");
	}
	
	/**
	 * 
	 * @return the type of packet;
	 */
	public byte getPacketID() {
		return 0x01;
	}
	
	/**
	 * 
	 * @return the length in characters of the message
	 */
	public short getMessageLength(){
		return this.messageLength;
	}

	/**
	 * @param messageLength the messageLength to set
	 */
	public void setMessageLength(short messageLength) {
		this.messageLength = messageLength;
	}

	/**
	 * @return the hour
	 */
	public short getHour() {
		return hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(short hour) {
		this.hour = hour;
	}

	/**
	 * @return the minute
	 */
	public short getMinute() {
		return minute;
	}

	/**
	 * @param minute the minute to set
	 */
	public void setMinute(short minute) {
		this.minute = minute;
	}

	/**
	 * @return the second
	 */
	public short getSecond() {
		return second;
	}

	/**
	 * @param second the second to set
	 */
	public void setSecond(short second) {
		this.second = second;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the messageGroup
	 */
	public byte getMessageGroup() {
		return messageGroup;
	}

	/**
	 * @param messageGroup the messageGroup to set
	 */
	public void setMessageGroup(byte messageGroup) {
		this.messageGroup = messageGroup;
	}
	
	/**
	 * 
	 * @param writer writes the packet through the socket's provided DataOutputStream
	 * @throws IOException if the socket connection has died
	 */
	public void write(DataOutputStream writer) throws IOException{
		writer.writeByte(0x01);
		writer.writeByte(0x00);
		writer.writeShort(this.getHour());
		writer.writeShort(this.getMinute());
		writer.writeShort(this.getSecond());
		writer.writeShort(message.length());
		writer.writeChars(message);
		writer.writeShort(this.username.length());
		writer.writeChars(this.username);
		writer.flush();
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public MessagePacket setUsername(String username) {
		this.username = username;
		return this;
	}

	
	public String toString(){
		return this.getMessage();
	}
}
