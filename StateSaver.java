import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * StateSaver saves the state of a StudyManager object to .UniPlaner.ser.
 */
public class StateSaver {

    public static void saveState(StudyManager studyManager) {
        File file = new File (".UniPlaner.ser");
        if (!file.exists()) {
            try {
                Files.createFile(Paths.get(".UniPlaner.ser"));
            }
            catch (IOException e){
                return;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            os.writeObject(studyManager);
            fos.close();
        }
        catch (FileNotFoundException e){

        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Fehler beim Schreiben");
        }

    }
}
