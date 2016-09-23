package com.lq.ren.many.calendar.unit920;


import java.util.ArrayList;
import java.util.List;

/**
 * Author lqren on 16/9/20.
 */
public class EmployeeTableUtil {

    public int count() {
        return 0;
    }

    public static final List<Employee> findAll() {
        return new ArrayList<Employee>();
    }

    public void insert(Employee employee) {
        if (existed(employee.getId())) {
            throw new NullPointerException();
        }

        //insert employee
    }

    public static final void update(Employee employee) {
        if (employee == null) {
            throw new NullPointerException();
        }
    }

    public boolean delete(Employee employee) {
        if (existed(employee.getId())) {
            //delete employee
            return true;
        }
        return false;
    }

    private boolean existed(String id) {
        return false;
    }
}

