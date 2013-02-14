package edu.wpi.tmathmeyer.chat.server.ops;

import edu.wpi.tmathmeyer.chat.protocol.CommandPacket;
import edu.wpi.tmathmeyer.chat.server.Client;
import edu.wpi.tmathmeyer.chat.server.Server;

public class LeaveGroupOperator implements CommandOperator {

	@Override
	public void register() {
		Server.se.addCommandOperator(this, "leave");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		Server.se.operateCommand(new CommandPacket("join", "HOME", (byte) 0x00), sender);
	}

}
