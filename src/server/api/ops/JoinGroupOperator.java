package server.api.ops;

import server.api.Client;
import server.api.Server;

public class JoinGroupOperator implements CommandOperator {

	@Override
	public void register() {
		Server.se.addCommandOperator(this, "join");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		for(int i = 0; i < args.length; i++)
			Server.se.addUserToGroup(sender, args[i]);
	}

}
