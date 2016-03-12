package Objects;

import java.util.ArrayList;

import Networking.Puppet;
import Objects.Sendable.SendableObject;

public class AllPuppets {

	private static ArrayList<Puppet> puppets = new ArrayList<Puppet>();
	
	public static synchronized void send(String name, SendableObject obj){
		for(Puppet pup : puppets){
			if(pup.getName().equals(name)){
				pup.send(obj);
				return;
			}
		}
		System.out.println("Could not find puppet " + name + " and send it: " + obj.parameters());
	}
	
	public static synchronized ArrayList<Puppet> getPuppets(){
		return puppets;
	}
	
	public static synchronized Puppet getPuppet(String name){
		for(Puppet pup : puppets){
			if(pup.getName().equals(name)){
				return pup;
			}
		}
		return null;
	}
	
	public static synchronized void addPuppet(Puppet pup){
		puppets.add(pup);
	}

	public static synchronized boolean checkExist(String name) {
		for(Puppet pup : puppets){
			if(pup.getName().equals(name)){
				return true;
			}
		}
		return false;
	}

}
