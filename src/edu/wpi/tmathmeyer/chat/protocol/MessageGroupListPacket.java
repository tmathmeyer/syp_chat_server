package edu.wpi.tmathmeyer.chat.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessageGroupListPacket implements Packet{
	
	private byte[] groups;
	private String[] groupNames;
	private short[] lengths;
	
	/**
	 * EXAMPLE PACKET
	 * 
	 * [----packetID: 0x03---------------] [---client list packet--------------]
	 * [----messageGroupCount: 2---------] [---the number of groups 2 (short)--]
	 * [----messageGroup: {0x00,0x01}----] [---the channels -------------------]
	 * [----groupNameLengths: {5,8,7,12}-] [---the lengths of the channel names]
	 * [----groupNames: {OvTvO,...}------] [---the channel names---------------]
	 * 
	 * 
	 * @param in the data input stream
	 * @throws IOException if no data can be read from the data input stream, an IO exception is thrown
	 */
	public MessageGroupListPacket(DataInputStream in) throws IOException {
		short length = in.readShort();
		this.groups = new byte[length];
		this.lengths = new short[length];
		this.groupNames = new String[length];
		for (int i = 0; i < length; i++)groups[i] = in.readByte();
		for (int i = 0; i < length; i++)lengths[i] = in.readShort();
		for (int i = 0; i < length; i++){
			byte[] b = new byte[lengths[i]];
			for (int j = 0; j < lengths[i]; j++)
				b[j] = in.readByte();
			groupNames[i] = new String(b);
		}
	}
	
	public MessageGroupListPacket(byte[] ids, String[] names){
		this.groups = ids;
		this.groupNames = names;
	}
	
	public byte getPacketID(){
		return 0x03;
	}

	/**
	 * @return the groups
	 */
	public byte[] getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(byte[] groups) {
		this.groups = groups;
	}

	/**
	 * @return the groupNames
	 */
	public String[] getGroupNames() {
		return groupNames;
	}

	/**
	 * @param groupNames the groupNames to set
	 */
	public void setGroupNames(String[] groupNames) {
		this.groupNames = groupNames;
	}

	/**
	 * @return the lengths
	 */
	public short[] getLengths() {
		return lengths;
	}

	/**
	 * @param lengths the lengths to set
	 */
	public void setLengths(short[] lengths) {
		this.lengths = lengths;
	}

	@Override
	public void write(DataOutputStream d) throws IOException {
		d.writeByte(0x03);
		d.writeShort(this.groupNames.length);
		d.flush();
		for(byte b : this.groups)d.writeByte(b);
		d.flush();
		for(String n : this.groupNames)d.writeShort(n.length());
		d.flush();
		for(String n : this.groupNames)d.writeBytes(n);
		d.flush();
	}
}
