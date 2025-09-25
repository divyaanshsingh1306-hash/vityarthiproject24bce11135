package edu.ccrm.service;

import java.util.*;
import java.util.stream.Collectors;
import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Student;

public class StudentService {
    private final AppConfig cfg = AppConfig.getInstance();

    public Student add(String reg, String name, String email){
        Student s = new Student(UUID.randomUUID().toString(), reg, name, email);
        cfg.students().add(s);
        return s;
    }
    public List<Student> list(){ return cfg.students(); }
    public Optional<Student> find(String reg){ return cfg.findStudentByReg(reg); }
    public void deactivate(String reg){ find(reg).ifPresent(Student::deactivate); }
    public void update(String reg, String name, String email){
        find(reg).ifPresent(s->{ s.setFullName(name); s.setEmail(email); });
    }
    public List<Student> topByGpa(int n){
        return cfg.students().stream()
            .sorted(Comparator.comparingDouble(Student::gpa).reversed())
            .limit(n).collect(Collectors.toList());
    }
}
