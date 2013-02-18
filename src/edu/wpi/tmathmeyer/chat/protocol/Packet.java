package edu.wpi.tmathmeyer.chat.protocol;

import java.io.DataOutputStream;
import java.io.IOException;

public interface Packet {
	/**
	 * 
	 * @return the ID of the packet
	 */
	public byte getPacketID();
	
	
	/**
	 * 
	 * @param d the dataoutputstream for the packet data to be written to
	 * @throws IOException a closed or malformed DoS
	 */
	public void write(DataOutputStream d) throws IOException;
	
	
	/**
	 * 
	 * loginPacket : 0x00
	 * 
     * messagePacketListPacket : 0x01
     * 
     * messageGroupPacket : 0x02
     * 
     * messageGroupList : 0x03
     * 
     * commandPacket : 0x04
     * 
     * privateMessagePacket : 0x05
     * 
     * usersPacket : 0x09
     * 
     * controlPacket : 0x15
	 * 
	 */
}
