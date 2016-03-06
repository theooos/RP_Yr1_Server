
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;

import Objects.Sendable.SingleTask;

/*
 * @author Maria
 * Orders a list of items in a job and returns vectors of integers with the moves required to take by the robot
 */


public class OrderPicks {

	
	
	public ArrayList<Objects.Sendable.SingleTask> items; //the list of items in the job
	public ArrayList<Objects.Sendable.SingleTask> orderedItems; //the ordered list of items
	public ArrayList<Point2D> dropOffs;
	public Point2D dropOff;
	private boolean running=true;
	private boolean canceled=false;
	
	
	public OrderPicks(ArrayList<Objects.Sendable.SingleTask> items,ArrayList<Point2D> dropOffs)
	{
		this.dropOffs=dropOffs;
		this.items=items;
		orderedItems=new ArrayList<Objects.Sendable.SingleTask>();
		planOrder();
	}
	
	public String toString()
	{
		String s="";
		for(int i=0;i<orderedItems.size();i++)
		{
			s=s+orderedItems.get(i).toString()+"  ";
			
		}
		
		return s;
	}
	
	/**
	 * cancels methods that are trying to order the jobs
	 */
	public void cancel()
	{
		canceled=true;
	}
	
	public ArrayList<Objects.Sendable.SingleTask> getOrder()
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
		ArrayList<Objects.Sendable.SingleTask> orderedItemsForMiny=new ArrayList<Objects.Sendable.SingleTask>();
		int minTotalDistance=-1;
		for(int y=0;y<dropOffs.size();y++)
		{
			dropOff=dropOffs.get(y);
			//picking the first item (the one closest to the robot)
			int min=0,minI=0;
			for(int i=0;i<items.size();i++)
			{				
				Point loc=this.getItemLocation(items.get(i).getItemID());
				int x=getRouteDist(dropOff,loc);
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
				orderedItemsForMiny.clear();
				
				orderedItemsForMiny.addAll(orderedItems);
				
				
			}
			items=orderedItems;
			
			orderedItems.clear();
		}
		
		orderedItems.addAll(orderedItemsForMiny);
		
		running=false;
	}
	
	
	
	
	/**
	 * A method that inserts an items in the best possible location
	 * @param index the index of the item(from the unordered list) to be inserted
	 */	
	private void findInsertLocation(int index)
	{
		int minj=-1,minD=-1;
		for(int j=0;j<orderedItems.size();j++)
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
		orderedItems.add( items.get(index));
		int dist=this.getDistance();
		if(minD==-1 || dist<minD)
		{
			if(canceled) return;
			minD=dist;
			minj=orderedItems.size();
		}
		orderedItems.remove(orderedItems.size()-1);
		orderedItems.add(minj, items.get(index));		
	}
	
	
	
	/**
	 *  A method to find the best pick to choose next
	 * @return the index of the pick closest to any of the picks already selected
	 */	
	private int findClosestPick()
	{
		int min=-1,minI=-1;
		for(int i=0;i<items.size();i++)
		{
			for(int j=0;j<orderedItems.size();j++)
			{
				Point loci=this.getItemLocation(items.get(i).getItemID());
				Point locj=this.getItemLocation(orderedItems.get(j).getItemID());
				int x=getRouteDist(locj,loci);
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
		Point loc=this.getItemLocation(orderedItems.get(0).getItemID());
		int sum=getRouteDist(dropOff,loc);;
		for(int i=1;i<orderedItems.size();i++)
		{
			if(canceled) return -1;
			loc=this.getItemLocation(orderedItems.get(i-1).getItemID());
			Point loci=this.getItemLocation(orderedItems.get(i).getItemID());
			sum+=getRouteDist(loc,loci);;
		}
		
		
		loc=this.getItemLocation(orderedItems.get(orderedItems.size()-1).getItemID());
		sum+=getRouteDist(loc,dropOff);;
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
		
		return getPathImaginary(loc1,loc2).size();		
	}
	
	public static Vector<Integer> getPathImaginary(Point2D loc1, Point2D loc2)
	{
		//TODO get path between 2 locations on the map in an imaginary case in which there are no other robots around
		return null;
	}
	public static Vector<Integer> getPath(Point2D loc1, Point2D loc2)
	{
		//TODO get path between 2 locations on the map normally, at a certain time
		return null;
	}
	
	
	private Point getItemLocation(String ItemID)
	{
		
		//TODO get item location somehow
		return null;
	}
	
}
