package lib;

import java.io.*;

/**
 * StateLoader loads a StudyManager object by reading data from the file specified in the constructor.
 */
public class StateLoader {
    public static StudyManager loadState(File file) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream is = new ObjectInputStream(fis);

                Object manager =  is.readObject();

                fis.close();
                return (StudyManager) manager;

            }
            catch (FileNotFoundException e){
                return null;
            }
            catch (IOException e){
                return null;
            }
            catch (ClassNotFoundException e){
                return null;
            }
    }
}
