package routeExecution;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import Objects.Direction;
import Objects.Sendable.Move;
import routePlanning.orderPicks.OrderPicks;


/*
 * @author Maria
 * A class that sends commands one by one to the robot and waits for execution
 */

public class RouteExecution extends Thread {

	
	
	private boolean running=true;
	private int nrOfRobots=0;
	
	private ArrayList<Objects.Sendable.SingleTask> tasks;
	
	public RouteExecution(int nrOfRobots)
	{
		this.nrOfRobots=nrOfRobots;
	}
	
	/**
	 * this is the main method that sends commands and checks the status of the robots
	 */
	
	
	public void run()
	{
		while(running)
		{
			initVariables();
			
			for(int ir=0;ir<nrOfRobots;ir++){
				//check if all the robots are doing jobs and assign them jobs if not
				String name=this.getRobotName(ir);
				if(!this.IsRobotDoingAJob(name))
				{
					ArrayList<Objects.Sendable.SingleTask> job=this.getJob();
					this.assignJob(name, job);
				}
			}
			for(int ir=0;ir<nrOfRobots;ir++){
				//check if all the robots have a task they are doing or not and assign tasks to them
				String name=this.getRobotName(ir);
				if(!this.isGoingToDropoff(name)){
					if(!this.RobotHasATask(name))
					{
						Vector<Direction> task=this.getNextTask(name);
						this.assignTask( task,name);
					}
				}else
				{
					//TODO robot needs to go to the dropOff point
				}
			}
			
			//now that we know all robots have jobs and tasks we can send commands
			//send the commands
			for(int ir=0;ir<nrOfRobots;ir++)
			{
				String name=this.getRobotName(ir);
				
				//check if robot reached the item
				if(this.getItemLocation(this.getCurrentTaskIndex(name),name)==this.getRobotLocation(name))
				{
					
					
					//TODO sent object to robot to tell it to pick up item
					
					
				
				}else
				{ //execute next move
					Direction facingDir=this.getRobotFacingDirection(name);
					Direction moveDir=this.getCurrentTask(name).get(this.getTaskMoveIndex(name));
					Move nextmove=this.getMove(facingDir, moveDir,name);
					if(nextmove==null)System.out.println("error generating the move object");
					else
					{
						//send the move to the server
						
						
						//TODO use Theo's new class for sending objects to robots 
						
						
						this.setWaitingForMR(name);
						
						
					}
				
				}
				
			}
			
			//commands have been sent now to wait for movereports
			boolean notYetDone=false;
			while(notYetDone&&running)
			{
				for(int ir=0;ir<nrOfRobots;ir++)
				{
					String name=this.getRobotName(ir);
					notYetDone=notYetDone&&this.hasMoved(name);
				}				
			}
			
			
			// check if any of the robots have picked up their items			
			for(int ir=0;ir<nrOfRobots;ir++)
			{
				String name=this.getRobotName(ir);
				if(this.isPickingUpItem(name)){
					if(this.hasPickedUpItem(name))
					{
						this.setHasATask(name, false);
					}
				}				
			}
			
			//check if any of the robots have reached dropOff
			for(int ir=0;ir<nrOfRobots;ir++)
			{
				String name=this.getRobotName(ir);
				if(this.isGoingToDropoff(name)){
					if(this.getRobotLocation(name).equals(this.getDropOffLocation(name)))
					{
						
						//TODO send command to drop items
						
						
						this.setDropingItems(name, true);
					}					
				}				
			}
			
			//check if any of the robots have dropped the items at the dropOff point
			for(int ir=0;ir<nrOfRobots;ir++)
			{
				String name=this.getRobotName(ir);
				if(this.dropingItems(name)){
					if(this.finishedDropingItems(name))
					{
						//job complete!!
						//TODO notify whoever needs to be notified about the fact that the job is complete
						//TODO remove the job from the queue
						//TODO reset the variables for the robot in robot info
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

	private boolean finishedDropingItems(String name)
	{
		return true;
		//TODO return the variable from robot info
	}
	
	private boolean dropingItems(String name)
	{
		return true;
		//TODO return the variable from robot info
	}
	
	private void setDropingItems(String name,boolean value)
	{
		//TODO change variables in robot info
	}
	
	private Point getDropOffLocation(String name)
	{
		return null;
		//TODO return the variable from robot info
	}
	
	
	private void setHasATask(String name,boolean value)
	{
		//TODO change variables in robot info
	}
	
	private boolean isPickingUpItem(String name)
	{
		//TODO return the boolean variable from robot info
		return true;
	}
	
	private boolean hasPickedUpItem(String name)
	{
		return true;
		//TODO return the boolean variable from robot info
	}
	
	private void initVariables()
	{
		//TODO make all variables regarding movement false in robot info
	}
	
	private boolean hasMoved(String name)
	{
		return true;
		//TODO return the boolean variable from robot info
	}
	
	private boolean isWaitingForMR(String name)
	{
		return true;
		//TODO return the boolean variable from robot info
	}
	
	private void setWaitingForMR(String name)
	{
		//TODO set the boolean variable to true in robot info
	}
	
	private boolean isGoingToDropoff(String name)
	{
		//TODO get info from AllRobots
		return false;
		
	}
	
	private void assignTask(Vector<Direction> task,String name)
	{
		this.setHasATask(name, true);
		//TODO change info in AllRobots to show it's now doing a task
	}
	
	private int getCurrentTaskIndex(String name)
	{
		return 0;
		//TODO get the index of the SingleTask object from the job arraylist that the robot is doing now
	}
	
	private Vector<Direction> getNextTask(String name)
	{
		//TODO get info from AllRobots after it's implemented
		//TODO computer the route for it using path finding and allocate times for it
		return null;
	}
	
	private Vector<Direction> getCurrentTask(String name)
	{
		//TODO get info from AllRobots after it's implemented
		return null;
	}
	
	private int getTaskMoveIndex(String name)
	{
		//TODO get info from AllRobots after it's implemented
		return 0;
	}
	
	private boolean RobotHasATask(String name)
	{
		return true;
		//TODO get info from AllRobots after it's implemented
	}
	
	private void assignJob(String name,ArrayList<Objects.Sendable.SingleTask> job)
	{
		//TODO change info in AllRobots to show that the robot is now doing a job
	}
	
	
	private ArrayList<Objects.Sendable.SingleTask> getJob()
	{
		//TODO get the next job in the priority queue
		return null;
	}
	
	public void stopThread()
	{
		running=false;
	}
	
	
	private String getRobotName(int index)
	{
		//TODO get info from AllRobots after it's implemented
		return null;
	}
	
	
	private boolean IsRobotDoingAJob(String name)
	{
		//TODO get info from AllRobots after it's implemented
		return true;
	}
	
	
	private Point getItemLocation(int index,String robotName)
	{
		
		//TODO get item location somehow
		return null;
	}
	
	
	private Objects.Sendable.MoveReport waitForResponse()
	{
		boolean waiting=true;
		while(waiting)
		{
			
		}
		return null;
		//TODO get move report from server somehow
	}
	
	private void robotHasMoved(Point newLoc)
	{
		//TODO notify other classes about the change in the robot location
	}
	
	
	private Point getRobotLocation(String name)
	{
		//TODO get robotlocation from the localization classes
		return null;
	}
	
	private Direction getRobotFacingDirection(String name)
	{
		//TODO get robot facing direction from the localization classes
		return null;
	}
	
}
