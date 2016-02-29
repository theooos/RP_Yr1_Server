package JobInput;

public class Test {

	public static void main(String[] args) {
		
		FileProcessor.processBothFiles("items.csv", "locations.csv");
		FileProcessor.processJobFile("jobs.csv");
		
		System.out.println(Item.items.size() + " " + Job.currentJobs.size());
		
	}
	
}
