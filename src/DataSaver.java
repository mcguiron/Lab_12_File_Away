import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;
/**
 *
 * @author wulft
 *
 * Demonstrates how to use Java NIO, a thread safe File IO library
 * to write a text file
 */
public class DataSaver
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // Test data the lines of the file we will write
        ArrayList <String>recs = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        String firstName = "";
        String lastName = "";
        String idNum = "";
        String email = "";
        String csvRec = "";
        int yob = 0;
        boolean done = false;

        do {

            firstName = SafeInput.getNonZeroLensString(in, "Enter the first name");
            lastName = SafeInput.getNonZeroLensString(in, "Enter the last name");
            idNum = SafeInput.getNonZeroLensString(in, "Enter the id number name");
            email = SafeInput.getNonZeroLensString(in, "Enter the email");
            yob = SafeInput.getRangedInt(in, "Enter the year of brith", 1000, 9999);

            csvRec = firstName + ", " + lastName + ", " + idNum + ", " + email + ", " + yob;

            recs.add(csvRec);

            done = SafeInput.getYNConfirm(in, "are you done?");

        }while(!done);



        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\data.csv");

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!

            for(String rec : recs)
            {
                writer.write(rec, 0, rec.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line

            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
