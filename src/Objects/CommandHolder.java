package Objects;

import java.util.ArrayList;

/**
 * 
 * @author theo
 *
 */
public class CommandHolder {

	private ArrayList<Commands> commands = new ArrayList<Commands>();
	
	public synchronized Commands pop(){
		Commands nextComm = commands.get(0);
		commands.remove(0);
		return nextComm;
	}
	
	public synchronized void add(String person, Object command){
		Commands newComm = new Commands(person, command);
		commands.add(newComm);
	}
}
