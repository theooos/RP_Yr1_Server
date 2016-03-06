import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import Objects.Move;
import Objects.MoveReport;

/*
 * @author Maria
 * A class that sends commands one by one to the robot and waits for execution
 */

public class RouteExecution {

	private ArrayList<Objects.SingleTask> tasks;
	
	public RouteExecution(ArrayList<Objects.SingleTask> tasks)
	{
		this.tasks=tasks;
	}
	
	public void run()
	{
		while(!tasks.isEmpty())
		{
			Point loc1=getRobotLocation();
			Point loc2=new Point(tasks.get(0).getItem().getX(),tasks.get(0).getItem().getY());
			Vector<Integer> v=OrderPicks.getPath(loc1, loc2);
			while(!v.isEmpty())
			{
				boolean commandsent=false;
				Point loc=getRobotLocation();
				int k=getRobotFacingDirection();
				Point newLoc = null;
				Move mv=null;
				switch(v.get(0))
				{
				case 0:					
					switch(k)
					{
					case 0:
						newLoc=new Point((int)loc.getX(),(int)loc.getY()+1 );
						mv=new Move('f', newLoc );
						break;
						
					case 1:
						newLoc=new Point((int)loc.getX(),(int)loc.getY()+1 );
						mv=new Move('b', newLoc );
						break;
					
					case 2:
						newLoc=new Point((int)loc.getX(),(int)loc.getY()+1 );
						mv=new Move('l', newLoc );
						break;
						
					
					case 3:
						newLoc=new Point((int)loc.getX(),(int)loc.getY()+1 );
						mv=new Move('r', newLoc );
						break;
					
					default:
						System.out.println("error - wrong value for robot facing direction");
					}
					break;
				
				case 1:
					switch(k)
					{
					case 0:
						newLoc=new Point((int)loc.getX(),(int)loc.getY()-1 );
						mv=new Move('b', newLoc );
						break;
						
					case 1:
						newLoc=new Point((int)loc.getX(),(int)loc.getY()-1 );
						mv=new Move('f', newLoc );
						break;
					
					case 2:
						newLoc=new Point((int)loc.getX(),(int)loc.getY()-1 );
						mv=new Move('r', newLoc );
						break;
						
					
					case 3:
						newLoc=new Point((int)loc.getX(),(int)loc.getY()-1 );
						mv=new Move('l', newLoc );
						break;
					
					default:
						System.out.println("error - wrong value for robot facing direction");
					}
					break;
				
				case 2:
					switch(k)
					{
					case 0:
						newLoc=new Point((int)loc.getX()+1,(int)loc.getY() );
						mv=new Move('r', newLoc );
						break;
						
					case 1:
						newLoc=new Point((int)loc.getX()+1,(int)loc.getY() );
						mv=new Move('l', newLoc );
						break;
					
					case 2:
						newLoc=new Point((int)loc.getX()+1,(int)loc.getY() );
						mv=new Move('f', newLoc );
						break;
						
					
					case 3:
						newLoc=new Point((int)loc.getX()+1,(int)loc.getY() );
						mv=new Move('b', newLoc );
						break;
					
					default:
						System.out.println("error - wrong value for robot facing direction");
					}
					break;
				
				case 3:
					switch(k)
					{
					case 0:
						newLoc=new Point((int)loc.getX()-1,(int)loc.getY() );
						mv=new Move('l', newLoc );
						break;
						
					case 1:
						newLoc=new Point((int)loc.getX()-1,(int)loc.getY() );
						mv=new Move('r', newLoc );
						break;
					
					case 2:
						newLoc=new Point((int)loc.getX()-1,(int)loc.getY() );
						mv=new Move('b', newLoc );
						break;
						
					
					case 3:
						newLoc=new Point((int)loc.getX()-1,(int)loc.getY() );
						mv=new Move('f', newLoc );
						break;
					
					default:
						System.out.println("error - wrong value for robot facing direction");
					}
					break;
				
				default:
					System.out.println("error - wrong value for robot move");
					
				}
				if(mv==null) 
				{
					System.out.println("could not create move object");
				}else
				{
					Commands com=new Commands("robot",mv);
					CommandHolder.add("robot", com);
					commandsent=true;
				}
				if(commandsent)
				{
					MoveReport report =waitForResponse();
					if(report.hasMoved())
					{
						v.remove(0);
						robotHasMoved(newLoc);
					}
				}				
			}
			//reched item
			
			//TODO send command to server to pick up items at current location
			
			
			//task succesful
			tasks.remove(0);
		}
	}
	
	private MoveReport waitForResponse()
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
	
	
	private Point getRobotLocation()
	{
		//TODO get robotlocation from the localization classes
		return null;
	}
	
	private int getRobotFacingDirection()
	{
		//TODO get robot facing direction from the localization classes
		return 0;
	}
	
}
