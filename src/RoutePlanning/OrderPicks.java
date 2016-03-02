package RoutePlanning;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Optional;



public class OrderPicks {

	
	
	public ArrayList<Objects.SingleTask> items; //the list of items in the job
	public ArrayList<Objects.SingleTask> orderedItems; //the ordered list of items
	public ArrayList<Point2D> dropOffs;
	public Point2D dropOff;
	private boolean running=true;
	private boolean canceled=false;
	
	
	public OrderPicks(ArrayList<Objects.SingleTask> items,ArrayList<Point2D> dropOffs)
	{
		this.dropOffs=dropOffs;
		this.items=items;
		planOrder();
	}
	
	/**
	 * cancels methods that are trying to order the jobs
	 */
	public void cancel()
	{
		canceled=true;
	}
	
	public ArrayList<Objects.SingleTask> getOrder()
	{
		if(running) return null;
		return orderedItems;
	}
	
	
	/**
	 * method that plans the order of the picks in the job using Nearest Insertion Heuristic
	 */	
	private void planOrder()
	{
		int miny=-1;
		ArrayList<Objects.SingleTask> orderedItemsForMiny=orderedItems;
		int minTotalDistance=-1;
		for(int y=0;y<=dropOffs.size();y++)
		{
			dropOff=dropOffs.get(y);
			//picking the first item (the one closest to the robot)
			int min=0,minI=0;
			for(int i=0;i<=items.size();i++)
			{
				int x=getRouteDist(getRobotLocation(),items.get(i).getItem().getLocation());
				if(min==0 || min>x)
				{
					if(canceled) return;
					min=x;
					minI=i;
				}
			}
			orderedItems.add(items.get(minI));
			items.remove(minI);
			
			while(!items.isEmpty())
			{
				if(canceled) return;
				minI=findClosestPick();
				findInsertLocation(minI);
				items.remove(minI);
			}
			int x=getDistance();
			if(x<minTotalDistance || minTotalDistance==-1)
			{
				miny=y;
				minTotalDistance=x;
				orderedItemsForMiny=orderedItems;
			}
		}
		orderedItems=orderedItemsForMiny;
		running=false;
	}
	
	
	
	
	/**
	 * A method that inserts an items in the best possible location
	 * @param index the index of the item(from the unordered list) to be inserted
	 */	
	private void findInsertLocation(int index)
	{
		int minj=-1,minD=-1;
		for(int j=1;j<=orderedItems.size();j++)
		{
			orderedItems.add(j, items.get(index));
			int dist=this.getDistance();
			if(minD==-1 || dist<minD)
			{
				if(canceled) return;
				minD=dist;
				minj=j;
			}
			orderedItems.remove(j);
		}
		orderedItems.add(minj, items.get(index));		
	}
	
	
	
	/**
	 *  A method to find the best pick to choose next
	 * @return the index of the pick closest to any of the picks already selected
	 */	
	private int findClosestPick()
	{
		int min=-1,minI=-1;
		for(int i=0;i<=items.size();i++)
		{
			for(int j=0;j<=orderedItems.size();j++)
			{
				int x=getRouteDist(orderedItems.get(j).getItem().getLocation(),items.get(i).getItem().getLocation());
				if(min==-1 || min>x)
				{
					if(canceled) return -1;
					min=x;
					minI=i;
				}
			}
		}
		return minI;
	}
	
	
	/**
	 * Method that calculates the total distance for the current route between all already selected picks
	 * @return the distance
	 */
	private int getDistance()
	{
		int sum=getRouteDist(getRobotLocation(),items.get(0).getItem().getLocation());;
		for(int i=1;i<=orderedItems.size();i++)
		{
			if(canceled) return -1;
			sum+=getRouteDist(items.get(i-1).getItem().getLocation(),items.get(i).getItem().getLocation());;
		}
		
		sum+=getRouteDist(items.get(orderedItems.size()).getItem().getLocation(),dropOff);;
		return sum;
	}
	
	
	/**
	 * Method to be used by other classes that require the total distance for a job
	 * @return -1 if the order has not yet been completed so other classes can't get incorrect distance
	 * @return if the order has been computed, will return the total distance
	 */	
	public int getFinalDistance()
	{
		if(running)	return -1;
		else return getDistance();
	}
	
	
	private int getRouteDist( Point2D loc1, Point2D loc2)
	{		
		return 0;		
	}
	
	private Point2D getRobotLocation()
	{
		return null;
	}
	
	
}
