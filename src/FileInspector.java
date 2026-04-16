import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;

public class FileInspector {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        int numWords = 0;
        int numChars = 0;

        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            System.out.println("No file selected. Exiting.");
            System.exit(0);
        }

        File selectedFile = chooser.getSelectedFile();

        // try-with-resources ensures the BufferedReader is closed automatically
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            String[] words;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                words = line.split(" ");
                numWords += words.length;
                for(int i =0; i<words.length; i++)
                    numChars += words[i].length();
            }
            // you would have to close the file here if you didn't use try-with-resources'
        }
        // Because of the java inheritance chain, you have to test for the more specific exception first
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            e.printStackTrace();
        }
        // This catches every IO exception and must be after the more specific ones
        catch (IOException e) {
            e.printStackTrace();
        }


        // Display the lines
        for (String l : lines) {
            System.out.println(l);
        }
        // Summary Report Output
        System.out.println("Summary Report");
        System.out.println("Number of lines in the file: " + lines.size());
        System.out.println("Number of words in the file: " + numWords);
        System.out.println("Number of characters in the file: " + numChars);
    }
}