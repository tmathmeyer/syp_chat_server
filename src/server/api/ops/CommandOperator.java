package server.api.ops;

import server.api.Client;

public interface CommandOperator {
	public void register();
	public void parseCommand(Client sender, String[] args);
}
