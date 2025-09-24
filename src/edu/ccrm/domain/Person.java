package edu.ccrm.domain;

import java.time.LocalDateTime;

public abstract class Person {
    protected final String id;
    protected String fullName;
    protected String email;
    protected final LocalDateTime createdAt = LocalDateTime.now();

    protected Person(String id, String fullName, String email){
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }
    public String getId(){ return id; }
    public String getFullName(){ return fullName; }
    public String getEmail(){ return email; }
    public void setFullName(String n){ this.fullName = n; }
    public void setEmail(String e){ this.email = e; }
    public abstract String profile();
    @Override public String toString(){ return profile(); }
}
