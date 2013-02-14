package edu.wpi.tmathmeyer.chat.server.ops;

import edu.wpi.tmathmeyer.chat.server.Client;

public interface CommandOperator {
	public void register();
	public void parseCommand(Client sender, String[] args);
}
