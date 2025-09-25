package edu.ccrm.config;

import java.util.*;
import java.nio.file.*;

import edu.ccrm.domain.*;
import edu.ccrm.value.CourseCode;

public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();
    private final List<Student> students = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Enrollment> enrollments = new ArrayList<>();
    private final Path dataDir = Paths.get("data");
    private final Path exportDir = Paths.get("exports");
    private final Path backupDir = Paths.get("backups");
    private int maxCreditsPerSemester = 21;

    private AppConfig() { }

    public static AppConfig getInstance() { return INSTANCE; }

    public List<Student> students() { return students; }
    public List<Course> courses() { return courses; }
    public List<Enrollment> enrollments() { return enrollments; }
    public Path dataDir() { return dataDir; }
    public Path exportDir() { return exportDir; }
    public Path backupDir() { return backupDir; }
    public int maxCreditsPerSemester() { return maxCreditsPerSemester; }
    public void setMaxCreditsPerSemester(int v){ this.maxCreditsPerSemester = v; }

    // Convenience finders
    public Optional<Student> findStudentByReg(String reg){
        return students.stream().filter(s->s.getRegNo().equalsIgnoreCase(reg)).findFirst();
    }
    public Optional<Course> findCourseByCode(CourseCode code){
        return courses.stream().filter(c->c.getCode().equals(code)).findFirst();
    }
}
