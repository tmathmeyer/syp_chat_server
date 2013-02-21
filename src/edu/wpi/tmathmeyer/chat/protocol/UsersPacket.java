package edu.wpi.tmathmeyer.chat.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UsersPacket implements Packet{
	private byte groupID;
	private String[] users;
	private String[] userColors,messageColors;
	byte[][] userInformation;
	
	/**
	 * EXAMPLE PACKET
	 * 
	 * [----packetID: 0x09---------------] [---client list packet--------------]
	 * [----usersCount: 4----------------] [---the number of groups 2 (short)--]
	 * [----userNameLengths: {3,3,3,3}---] [---the lengths of the user names---]
	 * [----userNames: {'tim','tom'...}--] [---the user names------------------]
	 * [----userColors ------------------] [---the colors of each user's name--]
	 * 
	 * 
	 * @param di the data input stream
	 * @throws IOException if no data can be read from the data input stream, an IO exception is thrown
	 */
	public UsersPacket(DataInputStream di) throws IOException{
		this.groupID = di.readByte();
		short count = di.readShort();
		
		this.setUsers(new String[count]);
		short[] lengths = new short[count];
		for (int i = 0; i < count; i++) lengths[i] = di.readShort();
		for (int i = 0; i < count; i++){
			char[] b = new char[lengths[i]];
			for(int j = 0; j < lengths[i]; j++){
				b[j] = di.readChar();
			}
			this.users[i] = new String(b);
		}
		
		userColors = new String[count];
		for (int i = 0; i < count; i++){
			 char[] b = new char[6];
			for(int j = 0; j < 6; j++){
				b[j] = di.readChar();
			}
			this.userColors[i] = new String(b);
		}
		
		
		messageColors = new String[count];
		for (int i = 0; i < count; i++){
			 char[] b = new char[6];
			for(int j = 0; j < 6; j++){
				b[j] = di.readChar();
			}
			this.messageColors[i] = new String(b);
		}
	}
	
	@Override
	public void write(DataOutputStream d) throws IOException {
		d.writeByte(0x09);
		d.writeByte(this.groupID);
		d.writeShort(users.length);
		for(String n : users)d.writeShort(n.length());
		for(String n : users)d.writeChars(n);
		for(String n : userColors)d.writeChars(n);
		for(String n : messageColors)d.writeChars(n);
		d.flush();
	}
	
	
	/**
	 * 
	 * @param users the users in the group
	 * @param groupID the ID of the group as created by the clientManager
	 */
	public UsersPacket(String[] users, byte groupID, String[] colors, String[] Colors2){
		this.groupID = groupID;
		this.users = users;
		this.userColors = colors;
		this.messageColors = Colors2;
	}
	
	@Override
	public byte getPacketID() {
		return 0x09;
	}


	/**
	 * @return the groupID
	 */
	public byte getGroupID() {
		return groupID;
	}


	/**
	 * @param groupID the groupID to set
	 */
	public void setGroupID(byte groupID) {
		this.groupID = groupID;
	}


	/**
	 * @return the users
	 */
	public String[] getUsers() {
		return users;
	}


	/**
	 * @param users the users to set
	 */
	public void setUsers(String[] users) {
		this.users = users;
	}


	
}