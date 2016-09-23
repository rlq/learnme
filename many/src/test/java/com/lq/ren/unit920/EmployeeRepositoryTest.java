package com.lq.ren.unit920;


import com.lq.ren.many.calendar.unit920.Employee;
import com.lq.ren.many.calendar.unit920.EmployeeRepository;
import com.lq.ren.many.calendar.unit920.EmployeeTableUtil;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author lqren on 16/9/20.
 * 针对EmployeeRepository编写测试,它协作的服务类为EmployeTableUtil，主要承担了访问数据库的职责。
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(EmployeeTableUtil.class)
public class EmployeeRepositoryTest {

    private EmployeeRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new EmployeeRepository();
    }

    /**
     * 使用PowerMock 测试findAll
     */
    @Test
    public void test_static_method() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("1"));
        employeeList.add(new Employee("2"));

        PowerMockito.mockStatic(EmployeeTableUtil.class);//static method EasyMock的风格
        PowerMockito.when(EmployeeTableUtil.findAll()).thenReturn(employeeList);

        List<Employee> employees = repository.findAll();
        Assert.assertTrue(employees.size() == 2);
        Assert.assertEquals(employees.size(), 2);
        Assert.assertEquals(employees.get(0).getId(), 1);
        Assert.assertEquals(employees.get(1).getId(), 2);
    }

    @Test
    public void test_command_method() {// command method 是没有返回值的方法  Mockito的风格
        Employee employee = new Employee("1");
        PowerMockito.mockStatic(EmployeeTableUtil.class);
        PowerMockito.doThrow(new NullPointerException()).when(EmployeeTableUtil.class);
        EmployeeTableUtil.update(employee);

        Assert.assertTrue(repository.update(employee));
    }

    @Test
    public void test_private_method() throws Exception {
        Employee employee = new Employee("1");
        EmployeeTableUtil util = PowerMockito.spy(new EmployeeTableUtil());
        PowerMockito.when(util, "existed").thenReturn(true);
        repository.setTableUtil(util);
        Assert.assertTrue(repository.insert(employee));
        Assert.assertTrue(repository.delete(employee));
    }

    /*public boolean isExists(File file) {
         return file.exists();
    }
      
    @Test
    public void testIsExists() { 
        File file = PowerMockito.mock(File.class); 
        PowerMockito.when(file.exists()).thenReturn(true);
         Assert.assertTrue(this.isExists(file));
     }

    //Mock方法内部new出来的对象
    public boolean isExists(String path) {
         File file = new File(path); return file.exists(); 
    }

      
    @Test
    public void testIsExists(int i) throws Exception {
         File file = PowerMockito.mock(File.class); 
        PowerMockito.whenNew(File.class).withArguments("bbb").thenReturn(file); 
        PowerMockito.when(file.exists()).thenReturn(true); 
        Assert.assertTrue(isExists("bbb")); 
    }

    //Mock普通对象的  final method

    public void testCallFinalMethod() { Dependency refer = PowerMockito.mock(Dependency.class); 
        PowerMockito.when(refer.isAlive()).thenReturn(true); 
        Assert.assertTrue(callFinalMethod(refer)); }

      
    @Test  
    @PrepareForTest(Dependency.class)  
    public boolean callFinalMethod(Dependency refer) { 
        return refer.isAlive(); 
    }

    //Mock普通对象的  final method
    public static boolean isExist() { 
        return false;
     }
    public boolean callStaticMethod() { 
        return isExist(); 
    }


    @Test
    @PrepareForTest(Dependency.class)  
    public void testCallStaticMethod() { 
        Dependency refer = PowerMockito.mockStatic(Dependency.class); 
        PowerMockito.when(isExist()).thenReturn(true); 
        Assert.assertTrue(callStaticMethod()); }

       //Mock  callPrivateMethod      
    @Test  
    @PrepareForTest(Dependency.class)  
    public void testCallPrivateMethod() throws Exception { 
        Dependency refer = PowerMockito.mock(Dependency.class); 
        PowerMockito.when(refer.callPrivateMethod()).thenCallRealMethod(); 
        PowerMockito.when(refer, "isExist2").thenReturn(true); 
        Assert.assertTrue(refer.callPrivateMethod());
         }
 */
}

