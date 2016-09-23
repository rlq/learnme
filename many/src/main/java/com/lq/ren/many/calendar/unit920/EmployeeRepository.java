package com.lq.ren.many.calendar.unit920;

import java.util.List;

/**
 * Author lqren on 16/9/20.
 */
public class EmployeeRepository {

    private EmployeeTableUtil tableUtil;

    public int count() {
        return new EmployeeTableUtil().count();
    }

    public List<Employee> findAll() {
        return EmployeeTableUtil.findAll();
    }

    public boolean insert(Employee employee) {
        try {
            tableUtil.insert(employee);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(Employee employee) {
        try {
            EmployeeTableUtil.update(employee);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(Employee employee) {
        return tableUtil.delete(employee);
    }

    private double bonus(Employee employee) {
        return employee.getSalary() * 0.1d;
    }

    public void setTableUtil(EmployeeTableUtil tableUtil) {
        this.tableUtil = tableUtil;
    }
}
