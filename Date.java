package lib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class Date implements Comparable<Date>{
	
	//private Semester semester;
	//private Course course;
	private GregorianCalendar date;
	private GregorianCalendar remindDate;
	
	private String title;
	private String description;
	private int remindTime;
	
	/*//Konstruktor für Semestertermin
	public Date(String title, String description, GregorianCalendar date, int remindTime) {
		//this.semester = semester;
		this.title = title;
		this.description = description;
		this.date = date;
	}*/
	
	//Konstruktor für Kurstermin
	public Date(String title, String description, GregorianCalendar date) {
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
	public int compareTo(Date d) {
		return date.compareTo(d.getDate());
	}
	
	public boolean compare(Date d) {
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
		if (!(obj instanceof Date)) {
			return false;
		}
		Date other = (Date) obj;
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

	private void print() {
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH);
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
			int rmonth = remindDate.get(Calendar.MONTH);
			int rday = remindDate.get(Calendar.DAY_OF_MONTH);
			int rhours = remindDate.get(Calendar.HOUR_OF_DAY);
			int rminute = remindDate.get(Calendar.MINUTE);
			System.out.println("Titel: " + getTitle() + " - Beschr: " + getDescription());
			System.out.println(rhours + ":" + rminute
					+ " - " + rday + "." + rmonth + "." + ryear);
		}
	}

	public static void main(String[] args) {
		GregorianCalendar jetzt = new GregorianCalendar();
		Date t1 = new Date("P1", "Erster Termin", jetzt);
		t1.print();
		GregorianCalendar morgen = new GregorianCalendar(2016, 4, 19, 16, 10);
		Date t2 = new Date("P2", "Zweiter Termin", morgen);
		t2.print();
		Date t3 = new Date("P3", "Dritter Termin", morgen);
		t3.print();
		t2.setReminder(15, true, false, false, false);
		System.out.println(t1.compare(t2));
		System.out.println(t2.compare(t3));
		System.out.println(t2.compareTo(t3));
		System.out.println(t1.compareTo(t2));
		System.out.println(t2.remind(jetzt));
		t2.printR();
		t2.print();
		System.out.println(t2.getDescription());
		System.out.println(t2.getRemindTime());
		t2.setTime(jetzt, 0, true, false, false, false);
		t2.print();
		t2.printR();
		System.out.println(t2.getRemindTime());
		GregorianCalendar g = new GregorianCalendar(2016, 4, 19, 10, 15);
		Date t4 = new Date("P4", "new new", g);
		ArrayList<Date> dates = new ArrayList<>();
		dates.add(t1);
		dates.add(t2);
		dates.add(t3);
		dates.add(t4);
		for (Date d : dates) {
			System.out.println(d.getTitle());
		}
		Collections.sort(dates);
		System.out.println("\n");
		for (Date d : dates) {
			System.out.println(d.getTitle());
		}
		
	}

}
