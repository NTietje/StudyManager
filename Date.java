package lib;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date implements Comparable<Calendar>{
	
	Semester semester;
	Course course;
	Calendar today;
	Calendar date;
	Calendar remindDate;
	
	String title;
	String description;
	int minutes;
	int hours;
	int day;
	int month;
	int year;
	
	//Konstruktor für Semestertermin
	public Date(Semester semester, String title, String description, int year, int month, int day, int hours, int minutes, int remindTime) {
		this.semester = semester;
		this.title = title;
		this.description = description;
		
		this.year = year;
		this.month = month;
		this.day = day;
		this.hours = hours;
		this. minutes = minutes;
		
		date = new GregorianCalendar(year, month-1, day, hours, minutes);
		if(remindTime != 0) {
			setReminder(remindTime);
		}
		
		today = new GregorianCalendar();
	}
	
	//Konstruktor für Kurstermin
	public Date(Course course, String title, String description, int year, int month, int day, int hours, int minutes, int remindTime) {
		this.course = course;
		this.title = title;
		this.description = description;
		
		this.year = year;
		this.month = month;
		this.day = day;
		this.hours = hours;
		this. minutes = minutes;
	
		date = new GregorianCalendar(year, month-1, day, hours, minutes);
		if(remindTime != 0) {
			setReminder(remindTime);
		}
		
		today = new GregorianCalendar();
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTime(int year, int month, int day, int hours, int minutes, int remindTime) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hours = hours;
		this. minutes = minutes;
		date = new GregorianCalendar(year, month-1, day, hours, minutes);
		if(remindTime != 0) {
			setReminder(remindTime);
		}
	}
	
	private void setReminder(int remindTime) {
		if (remindTime<60) {
			minutes -= remindTime;
		}
		else if (remindTime<60*24) {
			hours -= remindTime;
		}
		else {
			month -= remindTime;
		}
		remindDate = new GregorianCalendar(year, month-1, day, hours, minutes);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public boolean checkReminder() {
		/*calendar.get(Calendar.YEAR);
		calendar.get(Calendar.MONTH);
		calendar.get(Calendar.DAY_OF_MONTH);
		calendar.get(Calendar.HOUR_OF_DAY);
		calendar.get(Calendar.MINUTE);
		calendar.get(Calendar.SECOND);*/
		
		
		if () {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int compareTo(Calendar o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void main(String[] args) {

	}

}
