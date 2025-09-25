package edu.ccrm.service;

import java.util.*;
import java.util.stream.Collectors;
import edu.ccrm.domain.*;

public class TranscriptService {
    public Transcript buildTranscript(Student s){
        List<TranscriptEntry> list = s.getEnrollments().stream().map(e->new TranscriptEntry(e)).toList();
        return new Transcript(s, list);
    }
    public static class Transcript { // static nested
        private final Student student;
        private final List<TranscriptEntry> entries;
        private final double gpa;
        Transcript(Student s, List<TranscriptEntry> e){ this.student=s; this.entries=e; this.gpa=s.gpa(); }
        @Override public String toString(){
            StringBuilder sb = new StringBuilder("Transcript for "+student.getRegNo()+" "+student.getFullName()+" GPA="+String.format("%.2f", gpa)+"\n");
            entries.forEach(te->sb.append(te).append("\n"));
            return sb.toString().trim();
        }
    }

    class TranscriptEntry { // inner class
        final String code; final String title; final int credits; final String grade;
        TranscriptEntry(Enrollment e){
            this.code = e.getCourse().getCode().toString();
            this.title = e.getCourse().getTitle();
            this.credits = e.getCourse().getCredits();
            this.grade = e.isGraded()? e.getGrade().name(): "NG";
        }
        @Override public String toString(){ return code+" "+title+" "+credits+"cr "+grade; }
    }

}
