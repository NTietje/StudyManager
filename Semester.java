import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Semester allows the user to assign each semester a title and a directory as well as a list of courses and events.
 */
public class Semester extends StudyUnit implements Serializable {

    static final long serialVersionUID = 1L;
	private String title;
	private String path;
	private ArrayList<Course> courses;
	private ArrayList<Event> events;
	
	public Semester(String title) throws FileAlreadyExistsException, Exception {

        this.title = title;
        events = new ArrayList<>();
        courses = new ArrayList<>();

		try {
			path = Files.createDirectory(Paths.get("." + title)).toString();
		}
		catch(FileAlreadyExistsException ex){
			throw ex;
		}
		catch (Exception e){
			throw e;
		}

	}

	/**
	 * changes the title of the semester and renames the corresponding directory
	 * @param title new title
     */
	public void setTitle(String title) {
		Path target = Paths.get("." + title);
        Path source = Paths.get(path);
		try {
			Files.walkFileTree(source, new MoveFileVisitor(Paths.get(path), target));
		}
		catch (Exception ex){
			System.out.println("Der Umbenennungsprozess war nicht erfolgreich.");
			return;
		}
		this.title = title;
	}

	/**
	 * returns the title of the given semester
	 * @return
     */
	public String getTitle(){
		return title;
	}

	/**
	 * returns the path to the corresponding directory
	 * @return
     */
	public Path getPath() {
		return Paths.get(path);
	}

	/**
	 * adds a course to the semester's list of courses
	 * @param course course to add
     */
	public void addCourse(Course course){
			courses.add(course);
			Collections.sort(courses);
	}
	
	/**
	 * returns list of courses
	 * @return
	 */
	public ArrayList<Course> getCourses(){
		return courses;
	}

	/**
	 * removes a course from the list of courses and deletes the corresponding directory
	 * @param course
     */
	public void removeCourse(Course course){
		File file = new File(path, course.getTitle());
		if (file.exists()){
			try {
				Deleter.delete(file);
				courses.remove(course);
				Collections.sort(courses);
			}
			catch (IOException ex){
				System.out.println("Auf den Kurs kann nicht zugegriffen werden.");
			}
		}
		else {
			System.out.println("Der angegebene Kurs existiert nicht.");
		}
	}
	
	/**
	 * identifies semesters by title
	 * @param semester
	 * @return
	 */
	public boolean equals(Semester semester){
		return this.title.equals(semester.getTitle());
	}

    /**
     * returns a list of the semester's events
     * @return
     */
	public ArrayList<Event> getEvents(){
		return events;
	}

    /**
     * adds an event to the semester's list and updates the order of the list
     * @param event
     */
	public void addEvent(Event event){
		//check whether event already exists!
		if (!events.contains(event)) {
			events.add(event);
			Collections.sort(events);
		}
		else {
			System.out.println("Dieser Termin existiert bereits.");
		}
	}

    /**
     * removes the given element from the list and updates its order
     * @param event
     */
	public void removeEvent(Event event){
		events.remove(event);
		Collections.sort(events);
	}

}
