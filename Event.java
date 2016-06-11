
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Event implements Comparable<Event>, Serializable{
	
	//private Semester semester;
	//private Course course;
	private GregorianCalendar date;
	private GregorianCalendar remindDate;
	
	private String title;
	private String description;
	private int remindTime;
	
	/*//Konstruktor für Semestertermin
	public Event(String title, String description, GregorianCalendar date, int remindTime) {
		//this.semester = semester;
		this.title = title;
		this.description = description;
		this.date = date;
	}*/
	
	//Konstruktor für Kurstermin
	public Event(String title, String description, GregorianCalendar date) {
		//this.course = course;
		this.title = title;
		this.description = description;
		this.date = date;
	}
		
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTime(GregorianCalendar date, int remindTime, boolean minute, boolean hours, boolean day, boolean weak) {
		this.date = date;
		System.out.println(remindTime);
		System.out.println("this "+this.remindTime);
		if (remindTime > 0) {
			this.remindTime = remindTime;
		}
		System.out.println("this "+this.remindTime);
		if (this.remindTime > 0) {
			System.out.println("reminder gesettet");
			setReminder(this.remindTime, minute, hours, day, weak);
		}
	}
	
	private int getRemindTime() {
		return this.remindTime;
	}
	
	public GregorianCalendar getDate() {
		return date;
	}
	
	public void setReminder(int remindTime, boolean minute, boolean hours, boolean day, boolean weak) {
		this.remindTime = remindTime;
		remindDate = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
		if (minute) {
			remindDate.add(Calendar.MINUTE, -remindTime);
		}
		else if (hours) {
			remindDate.add(Calendar.HOUR_OF_DAY, -remindTime);
		}
		else if (day) {
			remindDate.add(Calendar.DAY_OF_MONTH, -remindTime);
		}
		else if (weak) {
			remindDate.add(Calendar.DAY_OF_MONTH, -remindTime*7);
		}
		
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public boolean remind(GregorianCalendar today) {
		if (today.after(remindDate)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int compareTo(Event d) {
		return date.compareTo(d.getDate());
	}
	
	public boolean compare(Event d) {
		if (compareTo(d) == 0 && title.equals(d.getTitle())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Event)) {
			return false;
		}
		Event other = (Event) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (remindDate == null) {
			if (other.remindDate != null) {
				return false;
			}
		} else if (!remindDate.equals(other.remindDate)) {
			return false;
		}
		if (remindTime != other.remindTime) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	public void print() {
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH) + 1;
		int day = date.get(Calendar.DAY_OF_MONTH);
		int hours = date.get(Calendar.HOUR_OF_DAY);
		int minute = date.get(Calendar.MINUTE);
		System.out.println("Titel: " + getTitle() + " - Beschr: " + getDescription());
		System.out.println(hours + ":" + minute
				+ " - " + day + "." + month + "." + year);
	}
	
	private void printR() {
		if (remindDate != null ) {
			int ryear = remindDate.get(Calendar.YEAR);
			int rmonth = remindDate.get(Calendar.MONTH) + 1;
			int rday = remindDate.get(Calendar.DAY_OF_MONTH);
			int rhours = remindDate.get(Calendar.HOUR_OF_DAY);
			int rminute = remindDate.get(Calendar.MINUTE);
			System.out.println("Titel: " + getTitle() + " - Beschr: " + getDescription());
			System.out.println(rhours + ":" + rminute
					+ " - " + rday + "." + rmonth + "." + ryear);
		}
	}

}
