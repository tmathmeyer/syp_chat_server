package edu.wpi.tmathmeyer.chat.server.command;

public class Commands {
	public Commands(){
		new JoinGroupOperator().register();
		new CreateGroupOperator().register();
		new LeaveGroupOperator().register();
		new OpenGroupOperator().register();
		new LockGroupOperator().register();
		new InviteOperator().register();
		new ChangeMessageColorOperator().register();
	}
}
