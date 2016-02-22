package JobInput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Process a file.
 */
public class FileProcessor {

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

    public static void processJobFile(String fileName) {

        Optional<List<String>> jobs = readFile(fileName);

        if(jobs.isPresent()) {
            System.err.println("Error processing job file.");
            return;
        }

        for(String jobStr : jobs.get()) {
            String[] jobArr = jobStr.split(","); 
            Job newJob = new Job(Integer.parseInt(jobArr[0]));
            for(int i = 1; i < jobArr.length; i+=2) {
                newJob.addItem(Item.get(jobArr[i]), Integer.parseInt(jobArr[i+1]));
            }
            Job.jobs.add(newJob);
        }

    }

}
