package edu.ccrm.domain;

import edu.ccrm.value.CourseCode;

public class Course {
    private final CourseCode code;
    private String title;
    private int credits;
    private String instructor; // simplified (could link to Instructor)
    private Semester semester;
    private String department;
    private boolean active = true;

    private Course(Builder b){
        this.code = b.code;
        this.title = b.title;
        this.credits = b.credits;
        this.instructor = b.instructor;
        this.semester = b.semester;
        this.department = b.department;
        assert credits > 0 && credits <= 10;
    }

    public CourseCode getCode(){ return code; }
    public String getTitle(){ return title; }
    public int getCredits(){ return credits; }
    public String getInstructor(){ return instructor; }
    public Semester getSemester(){ return semester; }
    public String getDepartment(){ return department; }
    public boolean isActive(){ return active; }
    public void deactivate(){ active = false; }
    public void update(String title, int credits, String instructor, Semester sem, String dept){
        this.title=title; this.credits=credits; this.instructor=instructor; this.semester=sem; this.department=dept;
    }

    public static class Builder {
        private CourseCode code;
        private String title;
        private int credits;
        private String instructor;
        private Semester semester;
        private String department;
        public Builder code(CourseCode c){ this.code=c; return this; }
        public Builder title(String t){ this.title=t; return this; }
        public Builder credits(int c){ this.credits=c; return this; }
        public Builder instructor(String i){ this.instructor=i; return this; }
        public Builder semester(Semester s){ this.semester=s; return this; }
        public Builder department(String d){ this.department=d; return this; }
        public Course build(){
            if(code==null || title==null || semester==null) throw new IllegalStateException("Missing fields");
            return new Course(this);
        }
    }

    @Override public String toString(){
        return code+":"+title+"("+credits+"cr "+semester+" "+department+")"+(active?"":" [INACTIVE]");
    }
}
