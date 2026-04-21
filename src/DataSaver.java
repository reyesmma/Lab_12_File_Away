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
        String idRegEx = "^\\d{6}$";
        String emailRegEx = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Scanner in = new Scanner(System.in);

        ArrayList<String> records = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String outputFilename = "";

        // Loop and collect data for the records into the array list
        do {
            firstName = SafeInput.getNonZeroLenString(in, "Enter the first name");
            lastName = SafeInput.getNonZeroLenString(in, "Enter the last name");
            ID = SafeInput.getRegExString(in, "Enter ID (6 digits, e.g., 000001)", idRegEx);;
            email = SafeInput.getRegExString(in, "Enter the email", emailRegEx);
            birthYear = SafeInput.getRangedInt(in, "Enter the birth year", 1000, 9999);

            // use the StringBuilder to build the csv record
            sb.append(firstName).append(", ").append(lastName).append(", ").append(ID).append(", ")
                    .append(email).append(", ").append(birthYear + "");

            // add it to the ArrayList
            records.add(sb.toString());
            sb.setLength(0); // clear the string builder for the next record

            // Prompt user for additional records
            done = SafeInput.getYNConfirm(in, "Are you done");
            if(done==true)
                outputFilename = SafeInput.getNonZeroLenString(in, "Enter the name of the output file") + ".csv";
        }while(!done);

        File workingDirectory = new File(".");
        Path path = Paths.get(workingDirectory.getPath() + "\\src\\" + outputFilename);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile())))
        {
            for(String rec : records)
            {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }

            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Print the array list for inspection
        for(String rec : records)
        {
            System.out.println(rec);
        }
    }
}