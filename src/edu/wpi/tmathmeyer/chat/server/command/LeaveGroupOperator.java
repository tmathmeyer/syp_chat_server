package edu.wpi.tmathmeyer.chat.server.command;

import edu.wpi.tmathmeyer.chat.server.Client;
import edu.wpi.tmathmeyer.chat.server.Server;
import edu.wpi.tmathmeyer.protocol.chat.CommandPacket;

public class LeaveGroupOperator implements CommandOperator {

	@Override
	public void register() {
		Server.se.addCommandOperator(this, "leave");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		if (sender.getCurrentGroup().getID() == 0x00)System.out.println("no leaving the homegroup");
		else Server.se.operateCommand(new CommandPacket("join", "B HOME", (byte) 0x00), sender);
	}

}
