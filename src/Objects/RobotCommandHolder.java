package Objects;

import java.util.ArrayList;

/**
 * 
 * @author theo
 *
 */
public class RobotCommandHolder {

	private ArrayList<RobotCommands<String, Object>> commands = new ArrayList<RobotCommands<String, Object>>();
	
	public synchronized RobotCommands<String,Object> pop(){
		RobotCommands<String, Object> nextComm = commands.get(0);
		commands.remove(0);
		return nextComm;
	}
	
	public synchronized void add(String person, Object command){
		RobotCommands<String, Object> newComm = new RobotCommands<String, Object>(person, command);
		commands.add(newComm);
	}
}
