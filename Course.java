import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Course allows the user to assign each course a title, a list of documents and a directory as well as the semester
 * it belongs to.
 */
public class Course implements Comparable<Course>, Serializable {

	static final long serialVersionUID = 1L;
	private String title;
	//private ArrayList<Date> events;
	private Semester semester;
	private ArrayList<File> documents;
	private String coursePathString;
	private boolean enabled;
	
	public Course (Semester semester, String title) throws FileAlreadyExistsException, Exception{

		Path path = semester.getPath().resolve(title) ;
		try {
			coursePathString = Files.createDirectory(path).toString();
		}
		catch (FileAlreadyExistsException ex){
			System.out.println("Es existiert bereits ein Kurs mit diesem Namen.");
			throw ex;
		}
		catch (Exception e){
			System.out.println("Kurs konnte nicht erstellt werden");
			throw e;
		}

		this.semester = semester;
		this.title = title;


		//events = new ArrayList<>();
		documents = new ArrayList<>();
		enabled = true;

	}

	/**
	 * sets the title of the course and changes the name of the directory accordingly
	 * @param title new title
     */
	public void setTitle(String title) {
		Path target = semester.getPath().resolve(title);
		Path source = Paths.get(coursePathString);
		try {
			Files.walkFileTree(source, new MoveFileVisitor(Paths.get(coursePathString), target));
		}
		catch (Exception ex){
			System.out.println("Der Umbenennungsprozess war nicht erfolgreich.");
			return;
		}
		this.title = title;
	}

	/**
	 * returns the title of the course
	 * @return
     */
	public String getTitle(){
		return title;
	}

	/**
	 * returns the semester to which the course belongs
	 * @return
     */
	public Semester getSemester() {
		return semester;
	}

	/**
	 * returns the list of events associated with the course
	 * @return
     */
	/*public ArrayList<Date> getEvents(){
		return events;
	}*/


	/**
	 * adds an event to the list of events and updates its order
     */
	/*public void addEvent(Date event){
  	if (!events.contains(event)) {
			events.add(event);
			Collections.sort(events);
		}
		else {
			System.out.println("Dieser Termin existiert bereits.");
		}
	}*/

	/**
	 * removes an event from the list
     */
	/*public void removeEvent(int index){
		events.remove(index);
		//remove via index or compare date and title of events?
	}*/

	/**
	 * returns a list of the documents that belong to the course
	 * @return
     */
	public ArrayList<File> getDocuments() {
		return documents;
	}
	
	/**
	 * copies the given file to the course directory and adds it to the list of files
	 * @param File document to be added
	 */
	public void addDocument(File document){
		if (!documents.contains(document)){
			//copy file to course directory
			try{
			Files.copy(Paths.get(document.getAbsolutePath()), Paths.get(coursePathString).resolve(document.getName()));
			}
			catch (Exception e){
				e.printStackTrace();
				System.out.println("Datei konnte nicht importiert werden.");
				return;
			}
			//add file to list of documents
			documents.add(document);
		}
	}
	
	/**
	 * deletes given file from course directory and removes it from the list of documents
	 * @param document
	 */
	public void removeDocument(File document){
		try {
			Files.deleteIfExists(Paths.get(document.getAbsolutePath()));
		}
		catch (IOException e){
			System.out.println("Datei konnte nicht gelöscht werden.");
			return;
		}
		documents.remove(document);
	}

	/**
	 * enables the course, so that its upcoming events are displayed
	 * @param enabled
     */
	public void setCourseEnabled(boolean enabled){
		this.enabled = enabled;
	}

	/**
	 * returns whether the course is enabled or not
	 * @return
     */
	public boolean getCourseEnabled(){
		return enabled;
	}


	/**
	 * compares courses according to title
	 * @param course
	 * @return
     */
	public int compareTo (Course course){
		return title.compareTo(course.getTitle());
	}
	

}
