package edu.ccrm.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private final Student student;
    private final Course course;
    private final Semester semester;
    private final LocalDateTime enrolledAt = LocalDateTime.now();
    private double marks = -1;
    private Grade grade;

    public Enrollment(Student s, Course c, Semester sem){
        this.student = s; this.course = c; this.semester = sem;
    }
    public Student getStudent(){ return student; }
    public Course getCourse(){ return course; }
    public Semester getSemester(){ return semester; }
    public boolean isGraded(){ return grade != null; }
    public Grade getGrade(){ return grade; }
    public double getMarks(){ return marks; }
    public void recordMarks(double m){
        this.marks = m;
        this.grade = Grade.fromMarks(m);
    }
    @Override public String toString(){
        return course.getCode()+" "+course.getTitle()+" "+(isGraded()?grade:"(NG)");
    }
}
