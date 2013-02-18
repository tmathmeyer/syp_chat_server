package edu.wpi.tmathmeyer.chat.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UsersPacket implements Packet{
	private byte groupID;
	private String[] users;
	byte[][] userInformation;
	
	/**
	 * EXAMPLE PACKET
	 * 
	 * [----packetID: 0x09---------------] [---client list packet--------------]
	 * [----usersCount: 4----------------] [---the number of groups 2 (short)--]
	 * [----userNameLengths: {3,3,3,3}---] [---the lengths of the user names---]
	 * [----userNames: {'tim','tom'...}--] [---the user names------------------]
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
			byte[] b = new byte[lengths[i]];
			for(int j = 0; j < lengths[i]; j++){
				b[j] = di.readByte();
			}
			this.users[i] = new String(b);
		}
	}
	
	
	/**
	 * 
	 * @param users the users in the group
	 * @param groupID the ID of the group as created by the clientManager
	 */
	public UsersPacket(String[] users, byte groupID){
		this.groupID = groupID;
		this.users = users;
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


	@Override
	public void write(DataOutputStream d) throws IOException {
		d.writeByte(0x09);
		d.writeByte(this.groupID);
		d.writeShort(users.length);
		for(String n : users)d.writeShort(n.length());
		for(String n : users)d.writeBytes(n);
		d.flush();
	}
}