import java.io.Serializable;
import java.util.ArrayList;

/**
 * StudyManager manages the different semesters of a given course of studies.
 */
public class StudyManager implements Serializable {

    static final long serialVersionUID = 1L;
    private ArrayList<Semester> semesters;

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
            if (semester.getTitle().equals(semesters.get(i).getTitle())){
                semesters.remove(i);
                return;
            }
        }
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
