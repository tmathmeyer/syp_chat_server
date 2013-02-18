package edu.wpi.tmathmeyer.chat.server.command;

import edu.wpi.tmathmeyer.chat.server.Client;
import edu.wpi.tmathmeyer.chat.server.Server;

public class OpenGroupOperator  implements CommandOperator{
	@Override
	public void register() {
		Server.se.addCommandOperator(this, "open");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		sender.getCurrentGroup().openGroup(sender);
	}
}