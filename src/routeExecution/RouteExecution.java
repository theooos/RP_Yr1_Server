package routeExecution;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;

import Objects.AllPuppets;
import Objects.AllRobots;
import Objects.Direction;
import Objects.Job;
import Objects.WarehouseMap;
import Objects.Sendable.DropOffPoint;
import Objects.Sendable.Move;
import Objects.Sendable.MoveReport;
import Objects.Sendable.SendableObject;
import Objects.Sendable.SingleTask;
import jobInput.JobProcessor;
import jobSelection.Selection;
import routePlanning.pathFinding.PathFinding;
import warehouseInterface.GridMap;
import warehouseInterface.JobTable;


/*
 * @author Maria
 * A class that sends commands one by one to the robot and waits for execution
 */

public class RouteExecution extends Thread {

	private boolean running=true;
	private int nrOfRobots=0;
	private PathFinding pathfinder;
	private PriorityQueue<Job> priorityQueue;

	public RouteExecution( int nrOfRobots,WarehouseMap map)
	{
		this.nrOfRobots=nrOfRobots;
		this.pathfinder=new PathFinding(map);
		this.priorityQueue = Selection.createQueue();
	}

	/**
	 * Main method that sends commands and checks the status of the robots
	 */


	public void run()
	{
		boolean done=true;
		initVariables();
		while(running)
		{		
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
			for(int ir=0;ir<nrOfRobots;ir++){
				//check if all the robots are doing jobs and assign them jobs if not
				String name=this.getRobotName(ir);
				if(isFunctioning(name))
				{
					if(!this.IsRobotDoingAJob(name))
					{
						Job job=this.getJob();
						
						this.assignJob(name, job);
					}
				}
			}
			for(int ir=0;ir<nrOfRobots;ir++){
				//check if all the robots have a task they are doing or not and assign tasks to them
				String name=this.getRobotName(ir);
				if(isFunctioning(name))
				{
					if((!this.RobotHasATask(name))&&(!this.dropingItems(name)))
					{
						if(this.getCurrentTaskIndex(name)>=AllRobots.getRobot(name).currJob.getNumOfTasks())
						{
							//robot should go to dropoff
							AllRobots.getRobot(name).goingToDropOff=true;
							Vector<Direction> path=  pathfinder.GetPath(this.getRobotLocation(name),this.getJob().dropOff, GetTime()+1, AllRobots.getRobot(name));
							System.out.println("assigning task to drop off:"+path);
							this.assignTask( path,name);
							
						}else{
							
							Vector<Direction> task=this.getNextTask(name);
							System.out.println("assigning task to this task:"+this.getNextTask(name)+" stuff: "+task);
							System.out.println("index1: "+this.getCurrentTaskIndex(name));
							System.out.println("maxindex: "+AllRobots.getRobot(name).currJob.getNumOfTasks());
							if(task!=null){
							this.assignTask( task,name);
							}
						}
					}
				}
			}

			//now that we know all robots have jobs and tasks we can send commands
			//send the commands
			for(int ir=0;ir<nrOfRobots;ir++)
			{
				String name=this.getRobotName(ir);

				
				if(isFunctioning(name))
				{
					//check if robot reached the item
					if(AllRobots.getRobot(name).goingToDropOff)
					{
						if(this.getJob().dropOff.equals(this.getRobotLocation(name)))
						{
							//reached drop off
							if(!AllRobots.getRobot(name).droppingOff)
							{
								AllRobots.getRobot(name).droppingOff=true;
								
								AllPuppets.send(name,new DropOffPoint((int)this.getJob().dropOff.getX(),(int)this.getJob().dropOff.getY()));
							}
						}else{
							if(this.getTaskMoveIndex(name)<this.getCurrentTask(name).size()){
								Direction facingDir=this.getRobotFacingDirection(name);
								Direction moveDir=this.getCurrentTask(name).get(this.getTaskMoveIndex(name));
								
								Move nextmove=this.getMove(facingDir, moveDir,name);
								if(nextmove==null)System.out.println("error generating the move object");
								else
								{
									//send the move to the server
									AllRobots.getRobot(name).nextRobotLocation=nextmove.getNextLocation();
		
		
		
									AllRobots.getRobot(name).nextDir=this.getCurrentTask(name).get(this.getTaskMoveIndex(name));
									
									AllPuppets.send(name,nextmove);
		
									AllRobots.getRobot(name).waitingForMoveReport=true;					
									done=false;
									System.out.println("sent next move: "+ nextmove.toString());
									
								}
							}
							
						}
					}else{
						if(this.getItemLocation(this.getCurrentTaskIndex(name),name).equals(this.getRobotLocation(name)))
						{
							if(!AllRobots.getRobot(name).pickingUp&&!AllRobots.getRobot(name).droppingOff)
							{
								
								System.out.println("reached Item");
								AllRobots.getRobot(name).pickingUp=true;
								AllPuppets.send(name,getTask(name));
								
								/*
								if(this.getCurrentTaskIndex(name)==AllRobots.getRobot(name).currJob.getNumOfTasks()-1)
								{
									AllRobots.getRobot(name).droppingOff=true;
									
									AllPuppets.send(name,new DropOffPoint((int)getTask(name).getLocation().getX(),(int)getTask(name).getLocation().getY()));
								}else
								{
									AllRobots.getRobot(name).pickingUp=true;
									AllPuppets.send(name,getTask(name));
								}
								*/
							}
	
	
						}else
						{ //execute next move
							if(!AllRobots.getRobot(name).waitingForMoveReport)
							{
								Direction facingDir=this.getRobotFacingDirection(name);
								//System.out.println(name);
								int blah = this.getTaskMoveIndex(name);
								//System.out.println(blah);
								int blah2 = this.getCurrentTask(name).size();
								//System.out.println(blah2);
								if(blah>=blah2)
								{
									System.out.println("out of bounds"+this.getItemLocation(this.getCurrentTaskIndex(name),name)+" robot loc: "+ this.getRobotLocation(name));
								}else{
									Direction moveDir=this.getCurrentTask(name).get(this.getTaskMoveIndex(name));
								
									Move nextmove=this.getMove(facingDir, moveDir,name);
									if(nextmove==null)System.out.println("error generating the move object");
									else
									{
										//send the move to the server
										AllRobots.getRobot(name).nextRobotLocation=nextmove.getNextLocation();
			
			
			
										AllRobots.getRobot(name).nextDir=this.getCurrentTask(name).get(this.getTaskMoveIndex(name));
										
										AllPuppets.send(name,nextmove);
			
										AllRobots.getRobot(name).waitingForMoveReport=true;					
										done=false;
										System.out.println("sent next move: "+ nextmove.toString());
										
									}
								}
							}
						}
					}
				}
			}

			//commands have been sent now to wait for movereports
			
			
			while(!done)
			{
				done=true;
				for(int ir=0;ir<nrOfRobots;ir++)
				{
					String name=this.getRobotName(ir);
					if(isFunctioning(name))
					{
						if(this.isWaitingForMR(name)){
					
							
							if(hasMoved(name)){
								AllRobots.getRobot(name).waitingForMoveReport=false;
								this.robotHasMoved(AllRobots.getRobot(name).nextRobotLocation, name,AllRobots.getRobot(name).nextDir);
								
								//if(AllRobots.getRobot(name).currDirectionsIndex<AllRobots.getRobot(name).directions.size())
								AllRobots.getRobot(name).currDirectionsIndex++;
								AllRobots.getRobot(name).hasMoved=false;
								System.out.println("robot has moved");
							}else done=false;
						}
					}
				}
				
			}


			// check if any of the robots have picked up their items			
			for(int ir=0;ir<nrOfRobots;ir++)
			{
				String name=this.getRobotName(ir);
				if(this.isFunctioning(name)){
					if(AllRobots.getRobot(name).pickingUp){
						if(this.hasPickedUpItem(name))
						{
							AllRobots.getRobot(name).pickingUp=false;
							
							AllRobots.getRobot(name).currTaskIndex++;
							/*
							if(AllRobots.getRobot(name).currTaskIndex+1<AllRobots.getRobot(name).currJob.getNumOfTasks())
							{
								
							}*/
							this.setHasATask(name, false);
							System.out.println("robot completed task");
						}
					}
				}
			}

			/*
			//check if any of the robots have reached dropOff
			for(int ir=0;ir<nrOfRobots;ir++)
			{
				String name=this.getRobotName(ir);
				if(this.isGoingToDropoff(name)){
					if(this.getRobotLocation(name).equals(this.getDropOffLocation(name)))
					{

						// use Theo's new class for sending objects to robots when he finally makes it
						AllPuppets.send(name,"dropItems");

						this.setDropingItems(name, true);
					}					
				}				
			}
			 */

			//check if any of the robots have dropped the items at the dropOff point
			for(int ir=0;ir<nrOfRobots;ir++)
			{
				String name=this.getRobotName(ir);
				if(this.isFunctioning(name)){
					if(this.dropingItems(name)){
						if(this.finishedDropingItems(name))
						{
							//job complete!!
							System.out.println("job complete");
							this.initVariables(name);
							//notify whoever needs to be notified about the fact that the job is complete


						}
					}
				}
			}


		}
	}

	/**
	 * A method that generates a Move object based on the direction the robot needs to go
	 * @param facingDir the direction the robot is facing
	 * @param moveDir the direction the robot needs to move
	 * @return the Move object
	 */

	private Move getMove(Direction facingDir, Direction moveDir,String name)
	{
		Point newLoc = null;
		Objects.Sendable.Move mv=null;
		Point loc=getRobotLocation(name);
		switch(moveDir)
		{
		case NORTH:					
			switch(facingDir)
			{
			case NORTH:
				newLoc=new Point((int)loc.getX(),(int)loc.getY()-1 );
				mv=new Objects.Sendable.Move('f', newLoc );
				break;

			case SOUTH:
				newLoc=new Point((int)loc.getX(),(int)loc.getY()-1 );
				mv=new Objects.Sendable.Move('b', newLoc );
				break;

			case EAST:
				newLoc=new Point((int)loc.getX(),(int)loc.getY()-1 );
				mv=new Objects.Sendable.Move('l', newLoc );
				break;


			case WEST:
				newLoc=new Point((int)loc.getX(),(int)loc.getY()-1 );
				mv=new Objects.Sendable.Move('r', newLoc );
				break;

			default:
				System.out.println("error - wrong value for robot facing direction");
			}
			break;

		case SOUTH:
			switch(facingDir)
			{
			case NORTH:
				newLoc=new Point((int)loc.getX(),(int)loc.getY()+1 );
				mv=new Objects.Sendable.Move('b', newLoc );
				break;

			case SOUTH:
				newLoc=new Point((int)loc.getX(),(int)loc.getY()+1 );
				mv=new Objects.Sendable.Move('f', newLoc );
				break;

			case EAST:
				newLoc=new Point((int)loc.getX(),(int)loc.getY()+1 );
				mv=new Objects.Sendable.Move('r', newLoc );
				break;


			case WEST:
				newLoc=new Point((int)loc.getX(),(int)loc.getY()+1 );
				mv=new Objects.Sendable.Move('l', newLoc );
				break;

			default:
				System.out.println("error - wrong value for robot facing direction");
			}
			break;

		case EAST:
			switch(facingDir)
			{
			case NORTH:
				newLoc=new Point((int)loc.getX()+1,(int)loc.getY() );
				mv=new Objects.Sendable.Move('r', newLoc );
				break;

			case SOUTH:
				newLoc=new Point((int)loc.getX()+1,(int)loc.getY() );
				mv=new Objects.Sendable.Move('l', newLoc );
				break;

			case EAST:
				newLoc=new Point((int)loc.getX()+1,(int)loc.getY() );
				mv=new Objects.Sendable.Move('f', newLoc );
				break;


			case WEST:
				newLoc=new Point((int)loc.getX()+1,(int)loc.getY() );
				mv=new Objects.Sendable.Move('b', newLoc );
				break;

			default:
				System.out.println("error - wrong value for robot facing direction");
			}
			break;

		case WEST:
			switch(facingDir)
			{
			case NORTH:
				newLoc=new Point((int)loc.getX()-1,(int)loc.getY() );
				mv=new Objects.Sendable.Move('l', newLoc );
				break;

			case SOUTH:
				newLoc=new Point((int)loc.getX()-1,(int)loc.getY() );
				mv=new Objects.Sendable.Move('r', newLoc );
				break;

			case EAST:
				newLoc=new Point((int)loc.getX()-1,(int)loc.getY() );
				mv=new Objects.Sendable.Move('b', newLoc );
				break;


			case WEST:
				newLoc=new Point((int)loc.getX()-1,(int)loc.getY() );
				mv=new Objects.Sendable.Move('f', newLoc );
				break;

			default:
				System.out.println("error - wrong value for robot facing direction");
			}
			break;

		default:
			System.out.println("error - wrong value for robot move");
		}

		return mv;

	}

	private SingleTask getTask(String name)
	{
		int index=AllRobots.getRobot(name).currTaskIndex;
		return AllRobots.getRobot(name).currJob.getTaskAtIndex(index).get();

	}


	private boolean finishedDropingItems(String name)
	{
		return AllRobots.getRobot(name).finishedDroppingItems;
	}

	private boolean dropingItems(String name)
	{
		return AllRobots.getRobot(name).droppingOff;
	}

	private void setDropingItems(String name,boolean value)
	{
		AllRobots.getRobot(name).droppingOff=value;
	}
	/*
	private Point getDropOffLocation(String name)
	{
		return null;

	}
	 */

	private void setHasATask(String name,boolean value)
	{
		AllRobots.getRobot(name).hasATask=value;
	}

	private boolean isPickingUpItem(String name)
	{
		return AllRobots.getRobot(name).pickingUp;
	}

	private boolean hasPickedUpItem(String name)
	{
		return AllRobots.getRobot(name).hasCompletedTask;
	}


	private boolean isFunctioning(String name)
	{
		return AllRobots.getRobot(name).functioning;
	}

	private void initVariables()
	{
		for(int i=0;i<this.nrOfRobots;i++)
		{
			String name=this.getRobotName(i);
			AllRobots.getRobot(name).isDoingJob=false;
			AllRobots.getRobot(name).pickingUp=false;
			AllRobots.getRobot(name).droppingOff=false;
			AllRobots.getRobot(name).hasATask=false;
			AllRobots.getRobot(name).finishedDroppingItems=false;
			AllRobots.getRobot(name).waitingForMoveReport=false;
			AllRobots.getRobot(name).hasMoved=false;
			AllRobots.getRobot(name).hasCompletedTask=false;
			AllRobots.getRobot(name).currDirectionsIndex=0;
			AllRobots.getRobot(name).currTaskIndex=0;
			AllRobots.getRobot(name).goingToDropOff=false;
		}

	}

	public void initVariables(String name)
	{

		AllRobots.getRobot(name).isDoingJob=false;
		AllRobots.getRobot(name).pickingUp=false;
		AllRobots.getRobot(name).droppingOff=false;
		AllRobots.getRobot(name).hasATask=false;
		AllRobots.getRobot(name).finishedDroppingItems=false;
		AllRobots.getRobot(name).waitingForMoveReport=false;
		AllRobots.getRobot(name).hasMoved=false;
		AllRobots.getRobot(name).hasCompletedTask=false;
		AllRobots.getRobot(name).currDirectionsIndex=0;
		AllRobots.getRobot(name).currTaskIndex=0;
		AllRobots.getRobot(name).goingToDropOff=false;

	}

	private boolean hasMoved(String name)
	{
		return AllRobots.getRobot(name).hasMoved;
	}

	private boolean isWaitingForMR(String name)
	{
		return AllRobots.getRobot(name).waitingForMoveReport;
	}




	private void assignTask(Vector<Direction> task,String name)
	{
		this.setHasATask(name, true);
		AllRobots.getRobot(name).currDirectionsIndex=0;
		AllRobots.getRobot(name).hasATask=true;
		//System.out.println(AllRobots.getRobot(name).currJob);
		//System.out.println("task: "+ task);
		//System.out.println(AllRobots.getRobot(name).directions);
		//System.out.println(Arrays.toString(task.toArray()));
		AllRobots.getRobot(name).directions=(Vector<Direction>) task.clone();
	}

	private int getCurrentTaskIndex(String name)
	{
		return AllRobots.getRobot(name).currTaskIndex;
	}

	private int GetTime(){
		return 0;
		//TODO
	}

	private Vector<Direction> getNextTask(String name)
	{
		if(getCurrentTaskIndex(name)>=AllRobots.getRobot(name).currJob.getNumOfTasks()){
			System.out.println("no more tasks "+AllRobots.getRobot(name).currJob.getNumOfTasks()+" current task index: "+getCurrentTaskIndex(name));
			System.out.println(AllRobots.getRobot(name).currJob);
			return new Vector<Direction>();
		}else{
			SingleTask nextTask=AllRobots.getRobot(name).currJob.getTaskAtIndex(getCurrentTaskIndex(name)).get();
			Vector<Direction> path=  pathfinder.GetPath(this.getRobotLocation(name),nextTask.getLocation(), GetTime()+1, AllRobots.getRobot(name));
			//System.out.println("got a task");

			return path;
		}
	}

	private Vector<Direction> getCurrentTask(String name)
	{
		return AllRobots.getRobot(name).directions;
	}

	private int getTaskMoveIndex(String name)
	{

		return AllRobots.getRobot(name).currDirectionsIndex;
	}

	private boolean RobotHasATask(String name)
	{
		return AllRobots.getRobot(name).hasATask;
	}

	private void assignJob(String name,Job job)
	{
		System.out.println("assigning job");
		AllRobots.getRobot(name).currJob=job;
		AllRobots.getRobot(name).isDoingJob=true;
		double reward = 0.0;
		int count = 0;
		for(SingleTask task : job.getTasks())
			if(!task.getItemID().equals("dropOff")){
				reward += JobProcessor.getItem(task.getItemID()).getReward();// * task.getQuantity();
				count++;
				System.out.println("the number of tasks is " + (job.getNumOfTasks() - 1));
				//System.out.println("this is count: " + count);
				System.out.println("this is reward: " + reward);
			}
		JobTable.addJob(job.getJobID(), String.valueOf(reward), name);
	}


	private Job getJob()
	{
		//System.out.println(priorityQueue);
		return priorityQueue.remove();

	}

	public void stopThread()
	{
		running=false;
	}


	private String getRobotName(int index)
	{
		while(AllRobots.getAllRobots().isEmpty())
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return AllRobots.getAllRobots().get(index).getName();
	}


	private boolean IsRobotDoingAJob(String name)
	{

		return AllRobots.getRobot(name).isDoingJob;
	}


	private Point getItemLocation(int index,String name)
	{

		return AllRobots.getRobot(name).currJob.getTaskAtIndex(index).get().getLocation();
	}

	/*
	private Objects.Sendable.MoveReport waitForResponse()
	{
		boolean waiting=true;
		while(waiting)
		{

		}
		return null;

	}
	 */
	private void robotHasMoved(Point newLoc,String name,Direction newDir)
	{

		AllRobots.getRobot(name).setPosition(newLoc);
		AllRobots.getRobot(name).setDirection(newDir);
		GridMap.refresh();
	}


	private Point getRobotLocation(String name)
	{

		return AllRobots.getRobot(name).getPosition();
	}

	private Direction getRobotFacingDirection(String name)
	{

		return AllRobots.getRobot(name).getDirection();
	}

	public void addMoveReport(String name, MoveReport comm) {
		System.out.println("i have a move: " + comm);
		AllRobots.getRobot(name).hasMoved=comm.hasMoved();

	}

	public void addCompleteReport(String name, SendableObject comm) {
		if(this.isPickingUpItem(name)){
			AllRobots.getRobot(name).hasCompletedTask=true;
		}
		if(this.dropingItems(name)){
			AllRobots.getRobot(name).finishedDroppingItems=true;
			
		}
	}

}
