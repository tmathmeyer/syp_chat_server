package server.api.ops;

import server.api.Client;
import server.api.Server;

public class LeaveGroupOperator implements CommandOperator {

	@Override
	public void register() {
		Server.se.addCommandOperator(this, "leave");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		for(int i = 0; i < args.length; i++)
			Server.se.remUserFromGroup(sender, args[i]);
	}

}
