package JobInput;
import java.util.ArrayList;
import java.util.List;

import Objects.Job;

public class Test {

	public static void main(String[] args) {

        WarehouseMap map = new WarehouseMap(10, 10, "res/drops.csv");

        JobProcessor.processItemFiles("res/items.csv", "res/locations.csv");
        JobProcessor.processJobFiles("res/jobs.csv", "res/cancellations.csv");

        List<Job> jl = new ArrayList<Job>(JobProcessor.getAllJobs().values());
        int count = 0;
        for(Job j : jl) {
        	count += j.cancelled() ? 1 : 0;
        }
        System.out.println(count);
        
		}
	
}

