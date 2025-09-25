package edu.ccrm.exceptions;

// Checked custom exception for duplicate enrollment attempts.
public class DuplicateEnrollmentException extends Exception {
    public DuplicateEnrollmentException(String msg){
        super(msg);
    }
}
