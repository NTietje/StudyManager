import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Semester {
	private String title;
	private Path path;
	private ArrayList<Course> courses;
	//private ArrayList<Date> events;
	
	public Semester(String title){
		try {
		path = Files.createDirectory(Paths.get("." + title));
		}
		catch(FileAlreadyExistsException ex){
			System.out.println("Ein Semester mit diesem Namen existiert bereits.");
			return;
		}
		catch (Exception e){
			System.out.println("Das Semester konnte nicht erstellt werden.");
			return;
		}
		
		this.title = title;
		//events = new ArrayList<>();
		courses = new ArrayList<>();
	}
	
	public void setTitle(String title) {
		Path target = Paths.get("." + title);
		try {
			Files.walkFileTree(this.path, new CopyFileVisitor(path, target));
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
	
	public Path getPath() {
		return path;
	}
	
	public void addCourse(Course course){
		if (!courses.contains(course)){
			courses.add(course);
			Collections.sort(courses);
		}
	}
	
	public void removeCourse(Course course){
		courses.remove(course);
		Collections.sort(courses);
	}
	
	/*public ArrayList<Date> getEvents(){
		return events;
	}
	
	 public void addEvent(Date event){
		//check whether event already exists!
		events.add(event);
		Collections.sort(events);
	}
	
	public void removeEvent(Date event){
		events.remove(event);
		Collections.sort(event);
	}*/

}
