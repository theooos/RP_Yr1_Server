package RoutePlanning;
import java.awt.Point;
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
		orderedItems=new ArrayList<Objects.SingleTask>();
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
		ArrayList<Objects.SingleTask> orderedItemsForMiny=new ArrayList<Objects.SingleTask>();
		int minTotalDistance=-1;
		for(int y=0;y<dropOffs.size();y++)
		{
			dropOff=dropOffs.get(y);
			//picking the first item (the one closest to the robot)
			int min=0,minI=0;
			for(int i=0;i<items.size();i++)
			{
				int xx=items.get(i).getItem().getX();
				int yy=items.get(i).getItem().getY();
				Point loc=new Point(xx,yy);
				int x=getRouteDist(getRobotLocation(),loc);
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
				int xx=items.get(i).getItem().getX();
				int yy=items.get(i).getItem().getY();
				Point loci=new Point(xx,yy);
				int xxj=items.get(i).getItem().getX();
				int yyj=items.get(i).getItem().getY();
				Point locj=new Point(xxj,yyj);
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
		int xx=orderedItems.get(0).getItem().getX();
		int yy=orderedItems.get(0).getItem().getY();
		Point loc=new Point(xx,yy);
		int sum=getRouteDist(getRobotLocation(),loc);;
		for(int i=1;i<orderedItems.size();i++)
		{
			if(canceled) return -1;
			xx=orderedItems.get(i-1).getItem().getX();
			yy=orderedItems.get(i-1).getItem().getY();
			loc=new Point(xx,yy);
			int xxi=orderedItems.get(i).getItem().getX();
			int yyi=orderedItems.get(i).getItem().getY();
			Point loci=new Point(xxi,yyi);
			sum+=getRouteDist(loc,loci);;
		}
		
		
		xx=orderedItems.get(orderedItems.size()-1).getItem().getX();
		yy=orderedItems.get(orderedItems.size()-1).getItem().getY();
		loc=new Point(xx,yy);
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
		return 0;		
	}
	
	private Point2D getRobotLocation()
	{
		return null;
	}
	
	
}
