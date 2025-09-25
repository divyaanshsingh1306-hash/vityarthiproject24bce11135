package edu.ccrm.io;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.value.CourseCode;

public class ImportExportService {
    private final AppConfig cfg = AppConfig.getInstance();
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();

    public void importStudents(Path file) throws IOException {
        if(!Files.exists(file)) return;
        try(Stream<String> lines = Files.lines(file)){
            lines.skip(1).forEach(l->{
                String[] p = l.split(",");
                if(p.length>=3) studentService.add(p[0].trim(), p[1].trim(), p[2].trim());
            });
        }
    }

    public void importCourses(Path file) throws IOException {
        if(!Files.exists(file)) return;
        try(Stream<String> lines = Files.lines(file)){
            lines.skip(1).forEach(l->{
                String[] p = l.split(",");
                if(p.length>=6){
                    Course c = new Course.Builder()
                        .code(CourseCode.of(p[0].trim()))
                        .title(p[1].trim())
                        .credits(Integer.parseInt(p[2].trim()))
                        .instructor(p[3].trim())
                        .semester(Semester.valueOf(p[4].trim().toUpperCase()))
                        .department(p[5].trim())
                        .build();
                    courseService.add(c);
                }
            });
        }
    }

    public void exportAll() throws IOException {
        Files.createDirectories(cfg.exportDir());
        Path students = cfg.exportDir().resolve("students_export.csv");
        Path courses = cfg.exportDir().resolve("courses_export.csv");
        Files.writeString(students, "regNo,fullName,email\n");
        for(Student s: cfg.students()){
            Files.writeString(students, s.getRegNo()+","+s.getFullName()+","+s.getEmail()+"\n", StandardOpenOption.APPEND);
        }
        Files.writeString(courses, "code,title,credits,instructor,semester,department\n");
        for(Course c: cfg.courses()){
            Files.writeString(courses, c.getCode()+","+c.getTitle()+","+c.getCredits()+","+c.getInstructor()+","+c.getSemester()+","+c.getDepartment()+"\n", StandardOpenOption.APPEND);
        }
    }
}
