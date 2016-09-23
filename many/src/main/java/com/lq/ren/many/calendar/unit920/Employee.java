package com.lq.ren.many.calendar.unit920;

/**
 * Author lqren on 16/9/20.
 */
public class Employee {

    private String id;
    private int salary;

    public Employee(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

}

/*public class Dependency {
     
    public final boolean isAlive() { return false; }

    public static boolean isExist() { return false; }

    private boolean isExist2() { return false; }

    public boolean callPrivateMethod() { return isExist2(); }

     
}*/