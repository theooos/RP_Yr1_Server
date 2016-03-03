package JobInput;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Objects.Item;
import Objects.Job;

/**
 * Class that carries out all the necessary processing and stores the data.
 */
public class JobProcessor {

    private Map<Integer, Job> jobs;
    private Map<String, Item> items;

    /**
     * Create a new processor.
     */
    public JobProcessor(){
        jobs = new HashMap<Integer, Job>();
        items = new HashMap<String, Item>();
    }

    /**
     * Get an item from its ID.
     * @param id The item id.
     * @return The item.
     */
    public Item getItem(String id) {
        return items.get(id);
    }
    
    /**
     * Get a job from its ID.
     * @param id The job id.
     * @return The job.
     */
    public Job getJob(int id) {
        return jobs.get(id);
    }

    /**
     * Get all the jobs.
     * @return All the jobs.
     */
    public Map<Integer, Job> getAllJobs() {
        return jobs;
    }

    /**
     * Get all the items.
     * @return All the items.
     */
    public Map<String, Item> getAllItems() {
        return items;
    }

    /**
     * Read a file into a list of lines.
     * @param fileName The file to be read.
     * @return The contents of the file as a list of strings.
     */
    public static Optional<List<String>> readFile(String fileName) {
        try { 
            List<String> fileContents = new ArrayList<String>();
            
            FileReader fr = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fr);

            String line;
            while((line = reader.readLine()) != null) {
                fileContents.add(line); 
            }

            fr.close();
            reader.close();

            return Optional.of(fileContents);
        } catch (FileNotFoundException e) {
            return Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Process the job files.
     * @param jobFile The job file.
     * @param cancelFile The cancellation file.
     */
    public void processJobFiles(String jobFile, String cancelFile) {

        Optional<List<String>> jobs = readFile(jobFile);
        Optional<List<String>> cancellations = readFile(cancelFile);

        if(!jobs.isPresent()) {
            System.err.println("Error processing job file.");
            return;
        }

        if(!cancellations.isPresent()) {
            System.err.println("Error processing cancellations file.");
            return;
        }

        for(String jobStr : jobs.get()) {
            // Each data element is separated by a comma.
            String[] jobArr = jobStr.split(","); 

            // Create a new job.
            Job newJob = new Job();;

            // Add each the item and the quantity of the item to the job.
            for(int i = 1; i < jobArr.length; i+=2) {
                newJob.addTask(Item.items.get(jobArr[i]), Integer.parseInt(jobArr[i+1]));
            }

            // Add a job to the list.
            jobs.put(Integer.parseInt(jobArr[0]), newJob);
        }

        for(String cancelStr : cancellations.get()) {
            String[] cancelArr = cancelStr.split(",");
            if(cancelArr[1].equals("1"))
                jobs.get(cancelArr[0]).cancelled();
        }

    }

    /**
     * Process the item files.
     * @param itemFile The item file.
     * @param locFile The location file.
     */
    public void processItemFiles(String itemFile, String locFile) {

        Optional<List<String>> items = readFile(itemFile);
        Optional<List<String>> locations = readFile(locFile);

        if(!items.isPresent()) {
            System.err.println("Error processing items file.");
            return;
        }
       
        if(!locations.isPresent()) {
        	System.err.println("Error processing locations file");
        	return;
        }
        

        for(String itemStr : items.get()) {
           
        	// Each data element is separated by a comma.
            String[] itemArr = itemStr.split(","); 
            
            for(String locationStr : locations.get()){
            	
            	String[] locationArr = locationStr.split(",");
            
            	if(Integer.parseInt(itemArr[0]) == Integer.parseInt(locationArr[0])){
		           
            		// Create a new item 
		            Item newItem = new Item( 
		            		new Point(Integer.parseInt(locationArr[1]), 
		            		Integer.parseInt(locationArr[2])), 
		            		Double.parseDouble(itemArr[1]), 
		            		Double.parseDouble(itemArr[2]));

            

		            // Add an item object to the list.
		            items.put(itemArr[0], newItem);
                    break;
            
            	}
            }
        }

    }

}
