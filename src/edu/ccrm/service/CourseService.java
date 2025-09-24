package edu.ccrm.service;

import java.util.*;
import java.util.stream.Collectors;
import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.value.CourseCode;

public class CourseService {
    private final AppConfig cfg = AppConfig.getInstance();

    public Course add(Course c){ cfg.courses().add(c); return c; }
    public List<Course> list(){ return cfg.courses(); }
    public Optional<Course> find(CourseCode code){ return cfg.findCourseByCode(code); }
    public void deactivate(CourseCode code){ find(code).ifPresent(Course::deactivate); }
    public void update(CourseCode code, String title, int credits, String instructor, Semester sem, String dept){
        find(code).ifPresent(c->c.update(title,credits,instructor,sem,dept));
    }
    public List<Course> filterByInstructor(String inst){
        return cfg.courses().stream().filter(c->c.getInstructor().equalsIgnoreCase(inst)).collect(Collectors.toList());
    }
    public List<Course> filterByDept(String dept){
        return cfg.courses().stream().filter(c->c.getDepartment().equalsIgnoreCase(dept)).collect(Collectors.toList());
    }
    public List<Course> filterBySemester(Semester sem){
        return cfg.courses().stream().filter(c->c.getSemester()==sem).collect(Collectors.toList());
    }
}
