package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.*;

import edu.ccrm.service.TranscriptService;

public class Student extends Person {
	public enum Status { ACTIVE, INACTIVE }

	private final String regNo;
	private Status status = Status.ACTIVE;
	private final LocalDate createdDate = LocalDate.now();
	private final List<Enrollment> enrolled = new ArrayList<>();

	public Student(String id, String regNo, String fullName, String email){
		super(id, fullName, email);
		this.regNo = regNo;
	}

	public String getRegNo(){ return regNo; }
	public Status getStatus(){ return status; }
	public void deactivate(){ status = Status.INACTIVE; }
	public void activate(){ status = Status.ACTIVE; }
	public LocalDate getCreatedDate(){ return createdDate; }

	public List<Enrollment> getEnrollments(){ return Collections.unmodifiableList(enrolled); }
	public void addEnrollment(Enrollment e){ enrolled.add(e); }
	public void removeEnrollment(Enrollment e){ enrolled.remove(e); }

	public double gpa(){
		int totalCredits = enrolled.stream().mapToInt(e->e.getCourse().getCredits()).sum();
		if(totalCredits == 0) return 0.0;
		double pts = enrolled.stream()
				.filter(Enrollment::isGraded)
				.mapToDouble(e-> e.getGrade().getGpaValue() * e.getCourse().getCredits())
				.sum();
		return pts / totalCredits;
	}

	public String transcript(){
		return new TranscriptService().buildTranscript(this).toString();
	}

	@Override public String profile(){
		return "Student["+regNo+" "+fullName+" "+status+" GPA="+String.format("%.2f", gpa())+"]";
	}
}

