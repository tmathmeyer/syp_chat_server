package edu.wpi.tmathmeyer.chat.server.ops;

public class Commands {
	public Commands(){
		new JoinGroupOperator().register();
		new CreateGroupOperator().register();
		new LeaveGroupOperator().register();
	}
}
