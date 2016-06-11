import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * StudyUnit serves as a template for Semester and Course, it stores and manages associated events.
 * @author katharina
 */
public class StudyUnit  implements Serializable {

    static final long serialVersionUID = 1L;
    private ArrayList<Event> events;

    public StudyUnit() {
        events = new ArrayList<>();
    }

    /**
     * returns a list of the unit's events
     * @return
     */
    public ArrayList<Event> getEvents(){
        return events;
    }

    /**
     * adds an event to the unit's list and updates the order of the list
     * @param event to add
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
     * @param event to remove
     */
    public void removeEvent(Event event){
        events.remove(event);
        Collections.sort(events);
    }

}
