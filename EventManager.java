import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.JButton;

/**
 * EventManager checks which events the user would like to be reminded of on a given day.
 * @author katharina
 *
 */
public class EventManager {
	private StudyManager manager;
	
	public EventManager(StudyManager manager){
		this.manager = manager;
	}
	
	/**
	 * returns a list of events of which the user would like to be reminded at this moment 
	 * @return ArrayList eventList
	 */
	public ArrayList<Date> updateEvents() {
		GregorianCalendar today = new GregorianCalendar();
		ArrayList<Date> eventList = new ArrayList<Date>();
		Date event;
		ArrayList<Semester> semesters = manager.getSemesters();
		for (int i = 0; i < semesters.size(); i++){
			Semester semester = semesters.get(i);
			ArrayList<Date> semesterEvents = semester.getEvents();
			for (int l = 0; l < semesterEvents.size(); l++){
				event = semesterEvents.get(l);
				if (event.remind(today)){
					eventList.add(event);
				}
			}
			ArrayList<Course> courses = semester.getCourses();
			for (int j = 0; j < courses.size(); j++){
				Course course = courses.get(j);
				ArrayList<Date> courseEvents = course.getEvents();
				for (int k = 0; k < courseEvents.size(); k++){
					event = courseEvents.get(k);
					if (event.remind(today)){
						eventList.add(event);
					}
				}
			}
		}
		Collections.sort(eventList);
		return eventList;
		
	}

}
