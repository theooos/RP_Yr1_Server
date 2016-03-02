package Objects;

/**
 * 
 * @author theo
 *
 * @param <Person> The person type, we'll use String.
 * @param <Command> The command type, we'll use Object.
 */
public class RobotCommands<Person, Command> {

	private Person person;
	private Command command;
	
	public RobotCommands(Person person, Command command) {
		this.person = person;
		this.command = command;
	}

	public Person getPerson(){
		return person;
	}
	
	public Command getCommand(){
		return command;
	}
}
