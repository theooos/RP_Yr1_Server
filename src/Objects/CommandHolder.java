package Objects;

import java.util.ArrayList;

/**
 * 
 * @author theo
 *
 */
public class CommandHolder {

	private ArrayList<Commands<String, Object>> commands = new ArrayList<Commands<String, Object>>();
	
	public synchronized Commands<String,Object> pop(){
		Commands<String, Object> nextComm = commands.get(0);
		commands.remove(0);
		return nextComm;
	}
	
	public synchronized void add(String person, Object command){
		Commands<String, Object> newComm = new Commands<String, Object>(person, command);
		commands.add(newComm);
	}
}
