package edu.ccrm.domain;

public enum Grade {
    A(4.0,85), B(3.0,75), C(2.0,65), D(1.0,55), F(0.0,0);
    private final double gpaValue;
    private final int min;
    Grade(double g, int m){ this.gpaValue=g; this.min=m; }
    public double getGpaValue(){ return gpaValue; }
    public static Grade fromMarks(double m){
        for(Grade g: values()){
            if(m >= g.min) return g;
        }
        return F;
    }
}
