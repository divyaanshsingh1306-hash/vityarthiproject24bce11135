package edu.ccrm.exceptions;

public class MaxCreditLimitExceededException extends RuntimeException {
    public MaxCreditLimitExceededException(String msg){ super(msg); }
}
