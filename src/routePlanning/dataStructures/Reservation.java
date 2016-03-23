package routePlanning.dataStructures;

import java.awt.Point;

public class Reservation {
	private int time;
	private Point node;
	
	public String toString()
	{
		String s="reservation at "+node+" for time "+time+"\n"; 
		return s;
	}
	
	public Reservation(int time, Point node) {
		this.time = time;
		this.node = node;
	}
	
	public int getTime() {
		return time;
	}
	
	public Point getNode() {
		return node;
	}
}
