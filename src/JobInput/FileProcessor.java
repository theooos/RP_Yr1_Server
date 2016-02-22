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

            return Optional.of(fileContents);

        } catch (FileNotFoundException e) {
            return Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }


    }

}
