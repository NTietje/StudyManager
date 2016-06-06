import java.io.File;
import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Course implements Comparable<Course>, Serializable {

	static final long serialVersionUID = 1L;
	private String title;
	//private ArrayList<Date> events;
	private Semester semester;
	private ArrayList<File> documents;
	private String coursePathString;
	private boolean enabled;
	
	public Course (Semester semester, String title){

		Path path = semester.getPath().resolve(title) ;
		try {
			coursePathString = Files.createDirectory(path).toString();
		}
		catch (FileAlreadyExistsException ex){
			System.out.println("Es existiert bereits ein Kurs mit diesem Namen.");
			return;
		}
		catch (Exception e){
			System.out.println("Kurs konnte nicht erstellt werden");
			return;
		}

		this.semester = semester;
		this.title = title;


		//events = new ArrayList<>();
		documents = new ArrayList<>();
		enabled = true;

	}
	
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
	
	public String getTitle(){
		return title;
	}
	
	public Semester getSemester() {
		return semester;
	}
	
	/*public ArrayList<Date> getEvents(){
		return events;
	}
	
	public void addEvent(Date event){
  	if (!events.contains(event)) {
			events.add(event);
			Collections.sort(events);
		}
		else {
			System.out.println("Dieser Termin existiert bereits.");
		}
	}

	public void removeEvent(int index){
		events.remove(index);
		//remove via index or compare date and title of events?
	}*/
	
	public ArrayList<File> getDocuments() {
		return documents;
	}
	
	public void setCourseEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public boolean getCourseEnabled(){
		return enabled;
	}
	
	
	//compare courses according to title
	public int compareTo (Course course){
		return title.compareTo(course.getTitle());
	}
	

}
