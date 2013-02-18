package edu.wpi.tmathmeyer.chat.server.command;

import edu.wpi.tmathmeyer.chat.server.Client;
import edu.wpi.tmathmeyer.chat.server.Server;

public class LockGroupOperator implements CommandOperator{
	@Override
	public void register() {
		Server.se.addCommandOperator(this, "lock");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		sender.getCurrentGroup().closeGroup(sender);
	}
}
