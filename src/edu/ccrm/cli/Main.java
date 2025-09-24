package edu.ccrm.cli;

import java.nio.file.Paths;
import java.util.*;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.exceptions.*;
import edu.ccrm.io.*;
import edu.ccrm.service.*;
import edu.ccrm.util.InterfacesDemo;
import edu.ccrm.value.CourseCode;

public class Main {
	private static final Scanner SC = new Scanner(System.in);
	private final StudentService studentService = new StudentService();
	private final CourseService courseService = new CourseService();
	private final EnrollmentService enrollmentService = new EnrollmentService();
	private final ImportExportService ioService = new ImportExportService();
	private final BackupService backupService = new BackupService();
	public static void main(String[] args){
		// Anonymous inner class demo
		Runnable banner = new Runnable(){ public void run(){ System.out.println("=== Campus Course & Records Manager (CCRM) ==="); }};
		banner.run();
		// Diamond interface demo usage
		System.out.println("Interface diamond: "+ new InterfacesDemo.Impl().idInfo());
		new Main().run();
	}

	private void run(){
		AppConfig.getInstance(); // ensure config initialized
		mainLoop: while(true){ // labeled loop for requirement
			showMenu();
			String choice = SC.nextLine().trim();
			switch(choice){
				case "1" -> manageStudents();
				case "2" -> manageCourses();
				case "3" -> enrollmentGrades();
				case "4" -> importExport();
				case "5" -> backup();
				case "6" -> reports();
				case "0" -> { System.out.println("Goodbye"); break mainLoop; }
				default -> System.out.println("Invalid option");
			}
		}
	}
	private void showMenu(){
		System.out.println();
		System.out.println("1) Manage Students");
		System.out.println("2) Manage Courses");
		System.out.println("3) Enrollment & Grades");
		System.out.println("4) Import/Export");
		System.out.println("5) Backup & Backup Size");
		System.out.println("6) Reports");
		System.out.println("0) Exit");
		System.out.print("Select: ");
	}

	private void manageStudents(){
		System.out.println("Students: a)Add b)List c)Update d)Deactivate e)Profile");
		String c = SC.nextLine();
		switch(c){
			case "a" -> {
				System.out.print("RegNo: "); String r=SC.nextLine();
				System.out.print("Name: "); String n=SC.nextLine();
				System.out.print("Email: "); String e=SC.nextLine();
				studentService.add(r,n,e);
			}
			case "b" -> studentService.list().forEach(System.out::println);
			case "c" -> {
				System.out.print("RegNo: "); String r=SC.nextLine();
				System.out.print("Name: "); String n=SC.nextLine();
				System.out.print("Email: "); String e=SC.nextLine();
				studentService.update(r,n,e);
			}
			case "d" -> { System.out.print("RegNo: "); studentService.deactivate(SC.nextLine()); }
			case "e" -> {
				System.out.print("RegNo: "); String r=SC.nextLine();
				studentService.find(r).ifPresentOrElse(s->{
					System.out.println(s.profile());
					System.out.println(new TranscriptService().buildTranscript(s));
				}, ()->System.out.println("Not found"));
			}
		}
	}

	private void manageCourses(){
		System.out.println("Courses: a)Add b)List c)Update d)Deactivate f)Filter");
		String c = SC.nextLine();
		try {
			switch(c){
				case "a" -> {
					System.out.print("Code (ABC123): "); CourseCode code=CourseCode.of(SC.nextLine());
					System.out.print("Title: "); String t=SC.nextLine();
					System.out.print("Credits: "); int cr=Integer.parseInt(SC.nextLine());
					System.out.print("Instructor: "); String inst=SC.nextLine();
					System.out.print("Semester (SPRING/SUMMER/FALL/WINTER): "); Semester sem=Semester.valueOf(SC.nextLine().toUpperCase());
					System.out.print("Dept: "); String dept=SC.nextLine();
					courseService.add(new Course.Builder().code(code).title(t).credits(cr).instructor(inst).semester(sem).department(dept).build());
				}
				case "b" -> courseService.list().forEach(System.out::println);
				case "c" -> {
					System.out.print("Code: "); CourseCode code=CourseCode.of(SC.nextLine());
					System.out.print("Title: "); String t=SC.nextLine();
					System.out.print("Credits: "); int cr=Integer.parseInt(SC.nextLine());
					System.out.print("Instructor: "); String inst=SC.nextLine();
					System.out.print("Semester: "); Semester sem=Semester.valueOf(SC.nextLine().toUpperCase());
					System.out.print("Dept: "); String dept=SC.nextLine();
					courseService.update(code,t,cr,inst,sem,dept);
				}
				case "d" -> { System.out.print("Code: "); courseService.deactivate(CourseCode.of(SC.nextLine())); }
				case "f" -> {
					System.out.print("Filter by (inst/dept/sem): ");
					String f=SC.nextLine();
					switch(f){
						case "inst" -> { System.out.print("Instructor: "); courseService.filterByInstructor(SC.nextLine()).forEach(System.out::println); }
						case "dept" -> { System.out.print("Dept: "); courseService.filterByDept(SC.nextLine()).forEach(System.out::println); }
						case "sem" -> { System.out.print("Semester: "); courseService.filterBySemester(Semester.valueOf(SC.nextLine().toUpperCase())).forEach(System.out::println); }
					}
				}
			}
		} catch(Exception ex){ System.out.println("Error: "+ex.getMessage()); }
	}

	private void enrollmentGrades(){
		System.out.println("Enroll: a)Enroll b)Unenroll c)Mark");
		String c = SC.nextLine();
		try {
			switch(c){
				case "a" -> {
					System.out.print("RegNo: "); var s = studentService.find(SC.nextLine()).orElseThrow();
					System.out.print("Course Code: "); var course = courseService.find(CourseCode.of(SC.nextLine())).orElseThrow();
					System.out.print("Semester: "); Semester sem=Semester.valueOf(SC.nextLine().toUpperCase());
					enrollmentService.enroll(s,course,sem);
				}
				case "b" -> {
					System.out.print("RegNo: "); var s=studentService.find(SC.nextLine()).orElseThrow();
					System.out.print("Course Code: "); var course = courseService.find(CourseCode.of(SC.nextLine())).orElseThrow();
					System.out.print("Semester: "); Semester sem=Semester.valueOf(SC.nextLine().toUpperCase());
					enrollmentService.unenroll(s,course,sem);
				}
				case "c" -> {
					System.out.print("RegNo: "); var s=studentService.find(SC.nextLine()).orElseThrow();
					System.out.print("Course Code: "); var course = courseService.find(CourseCode.of(SC.nextLine())).orElseThrow();
					System.out.print("Semester: "); Semester sem=Semester.valueOf(SC.nextLine().toUpperCase());
					System.out.print("Marks: "); double m=Double.parseDouble(SC.nextLine());
					enrollmentService.recordMarks(s,course,sem,m);
				}
			}
		} catch(DuplicateEnrollmentException de){ System.out.println("Duplicate enrollment"); }
		catch(MaxCreditLimitExceededException mcle){ System.out.println("Credit limit exceeded"); }
		catch(Exception ex){ System.out.println("Error: "+ex.getMessage()); }
	}

	private void importExport(){
		System.out.println("a)Import Students b)Import Courses c)Export All");
		String c = SC.nextLine();
		try {
			switch(c){
				case "a" -> ioService.importStudents(Paths.get("test-data","students.csv"));
				case "b" -> ioService.importCourses(Paths.get("test-data","courses.csv"));
				case "c" -> ioService.exportAll();
			}
		} catch(Exception e){ System.out.println("IO Error "+e.getMessage()); }
	}

	private void backup(){
		try {
			var loc = backupService.backupExports();
			if(loc!=null){
				long size = backupService.backupSize(loc);
				System.out.println("Backup at "+loc+" size="+size+" bytes");
			} else System.out.println("Nothing to backup");
		} catch(Exception e){ System.out.println("Backup error "+e.getMessage()); }
	}

	private void reports(){
		System.out.println("Reports: a)Top Students b)GPA Distribution");
		String c = SC.nextLine();
		switch(c){
			case "a" -> studentService.topByGpa(5).forEach(System.out::println);
			case "b" -> {
				var map = AppConfig.getInstance().students().stream()
					.collect(java.util.stream.Collectors.groupingBy(
						s->{ double g=s.gpa(); if(g>=3.5) return "High"; if(g>=2.5) return "Mid"; return "Low";},
						java.util.stream.Collectors.counting()));
				map.forEach((k,v)->System.out.println(k+":"+v));
			}
		}
	}
}
