import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * StudyManager manages the different semesters of a given course of studies.
 */
public class StudyManager implements Serializable {

    static final long serialVersionUID = 1L;
    private ArrayList<Semester> semesters;
    private String managerPathString;

    public StudyManager() {
        semesters = new ArrayList<>();
        
    }

    public void test() {
        System.out.println("test");
    }

    /**
     * adds a new semester to the list
     * @param semester to be added
     */
    public void addSemester(Semester semester) {
    		semesters.add(semester);
    }

    /**
     * removes a semester from the list
     * @param semester to be removed
     */
    public void removeSemester (Semester semester) {
        for (int i = 0; i < semesters.size(); i++){
        	Semester sem = semesters.get(i);
            if (semester.getTitle().equals(sem.getTitle())){
            	File file = new File(sem.getPath().toString());
        		if (file.exists()){
        			try {
        				Deleter.delete(file);
        				semesters.remove(i);
        				return;
        				//Collections.sort(semesters);
        			}
        			catch (IOException ex){
        				System.out.println("Auf den Kurs kann nicht zugegriffen werden.");
        				return;
        			}
        		}
            }
        }
    }
    
    /**
     * returns list of all semesters
     * @return
     */
    public ArrayList<Semester> getSemesters() {
    	return semesters;
    }

    /**
     * returns the semester identified by the given title and null, if there is no such semester
     * @param title by which to identify the semester
     * @return
     */
    public Semester getSemester(String title){
        for (int i = 0; i < semesters.size(); i++){
            if (title.equals(semesters.get(i).getTitle())){
                System.out.println("Semester gefunden!");
                return semesters.get(i);
            }
        }
        return null;
    }
}
