package edu.ccrm.service;

import java.util.*;
import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.exceptions.*;

public class EnrollmentService {
    private final AppConfig cfg = AppConfig.getInstance();

    public Enrollment enroll(Student s, Course c, Semester sem) throws DuplicateEnrollmentException {
        boolean exists = cfg.enrollments().stream()
            .anyMatch(e->e.getStudent()==s && e.getCourse()==c && e.getSemester()==sem);
        if(exists) throw new DuplicateEnrollmentException("Already enrolled");
        int currentCredits = (int) cfg.enrollments().stream()
            .filter(e->e.getStudent()==s && e.getSemester()==sem)
            .mapToInt(e->e.getCourse().getCredits()).sum();
        if(currentCredits + c.getCredits() > cfg.maxCreditsPerSemester())
            throw new MaxCreditLimitExceededException("Credit limit exceeded");
        Enrollment e = new Enrollment(s,c,sem);
        cfg.enrollments().add(e);
        s.addEnrollment(e);
        return e;
    }

    public void unenroll(Student s, Course c, Semester sem){
        cfg.enrollments().removeIf(e->{
            if(e.getStudent()==s && e.getCourse()==c && e.getSemester()==sem){
                s.removeEnrollment(e); return true;
            }
            return false;
        });
    }

    public void recordMarks(Student s, Course c, Semester sem, double marks){
        cfg.enrollments().stream()
            .filter(e->e.getStudent()==s && e.getCourse()==c && e.getSemester()==sem)
            .findFirst().ifPresent(e->e.recordMarks(marks));
    }

    public List<Enrollment> listByStudent(Student s){
        return cfg.enrollments().stream().filter(e->e.getStudent()==s).toList();
    }
}
