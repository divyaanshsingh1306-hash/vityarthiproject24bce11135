package edu.ccrm.domain;

public enum Semester {
    SPRING(1), SUMMER(2), FALL(3), WINTER(4);
    private final int order;
    Semester(int o){ this.order=o; }
    public int getOrder(){ return order; }
}
