import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Deleter provides a way to recursively delete directories.
 * @author katharina
 */
public final class Deleter implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * deletes file and deletes directory recursively
     * @param file
     * @throws IOException
     */
    public static void delete (File file) throws IOException {

        if (file.isDirectory()) {

            //if file is empty, delete it
            if (file.list().length == 0) {
                file.delete();
            }
            //delete its contents first
            else {
                for (String fileName : file.list()) {

                    File fileToDelete = new File(file, fileName);

                    //recursively delete contents
                    delete(fileToDelete);

                }
                //check again and delete if empty
                if (file.list().length == 0) {
                    file.delete();
                }
            }

        } else {
            file.delete();
        }

    }
}
