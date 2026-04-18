import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {
    public static void main(String[] args)
    {
        String firstName = "";
        String lastName = "";
        String ID = "";
        String email = "";
        int birthYear = 0;
        String csvRec = "";
        boolean done = false;

        Scanner in = new Scanner(System.in);

        ArrayList<String> records = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        // Loop and collect data for the Person records into the array list
        do {
            firstName = SafeInput.getNonZeroLenString(in, "Enter the first name");
            lastName = SafeInput.getNonZeroLenString(in, "Enter the last name");
            ID = SafeInput.getNonZeroLenString(in, "Enter the ID");
            email = SafeInput.getNonZeroLenString(in, "Enter the email");
            birthYear = SafeInput.getRangedInt(in, "Enter the birth year: ", 1000, 9999);

            // use the StringBuilder to build the csv record
            sb.append(firstName).append(", ").append(lastName).append(", ").append(ID).append(", ")
                    .append(email).append(", ").append(birthYear + "");

            // add it to the ArrayList
            records.add(sb.toString());
            sb.setLength(0); // clear the string builder for the next record

            // Prompt user for additional records
            done = SafeInput.getYNConfirm(in, "Are you done");
        }while(!done);

        File workingDirectory = new File(".");
        Path path = Paths.get(workingDirectory.getPath() + "\\src\\UserData.csv");

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile())))
        {
            for(String rec : records)
            {
                writer.write(rec, 0, rec.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line
            }

            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Dump the array list for inspection
        for(String rec : records)
        {
            System.out.println(rec);
        }
    }
}