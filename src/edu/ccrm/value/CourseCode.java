package edu.ccrm.value;

import java.util.Objects;

public final class CourseCode {
    private final String value;
    private CourseCode(String v){ this.value = v.toUpperCase(); }
    public static CourseCode of(String v){
        if(v==null || !v.matches("[A-Za-z]{3}\\d{3}")) throw new IllegalArgumentException("Invalid code");
        return new CourseCode(v);
    }
    public String get(){ return value; }
    @Override public String toString(){ return value; }
    @Override public boolean equals(Object o){ return o instanceof CourseCode c && c.value.equals(value); }
    @Override public int hashCode(){ return Objects.hash(value); }
}
