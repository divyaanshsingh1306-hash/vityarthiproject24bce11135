package edu.ccrm.util;

public class InterfacesDemo {
    interface Identifiable { default String idInfo(){ return "ID"; } }
    interface Registrable extends Identifiable { @Override default String idInfo(){ return "REG"; } }
    interface Loggable extends Identifiable { @Override default String idInfo(){ return "LOG"; } }
    public static class Impl implements Registrable, Loggable {
        @Override public String idInfo(){ return Registrable.super.idInfo()+"/"+Loggable.super.idInfo(); }
    }
}
