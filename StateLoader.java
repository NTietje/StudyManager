import java.io.*;

/**
 * StateLoader provides a method to load a StudyManager object by reading data from a file
 * @author katharina
 */
public class StateLoader {
    /**
     * loads the state of a StudyManager object
     * @param file to read from
     * @return StudyManager object
     */
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
