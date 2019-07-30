package com.tw.apistackbase.dao;

import com.tw.apistackbase.entity.Employee;
import com.tw.apistackbase.entity.GENDER;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.collect.ImmutableList.of;
import static com.tw.apistackbase.entity.GENDER.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeDaoTest {
    private EmployeeDao employeeDao;

    @BeforeEach
    void setUp() {
        employeeDao = new EmployeeDao();
    }

    @Test
    void should_return_paged_employee_succeed() {
        Employee firstEmploy = new Employee(1, "first", 18, male, 1000);
        Employee secondEmploy = new Employee(2, "first", 18, male, 1000);
        Employee thirdEmploy = new Employee(3, "first", 18, male, 1000);
        employeeDao.add(firstEmploy);
        employeeDao.add(secondEmploy);
        employeeDao.add(thirdEmploy);
        List<Employee> firstPageEmployees = of(firstEmploy, secondEmploy);
        List<Employee> secondPageEmployees = of(thirdEmploy);

        List<Employee> firstQueryResult = employeeDao.getAll(1, 2);
        List<Employee> secondQueryResult = employeeDao.getAll(2, 2);

        assertEquals(firstPageEmployees, firstQueryResult);
        assertEquals(secondPageEmployees, secondQueryResult);
    }

    @Test
    void should_return_NoSuchElementException_when_page_exceed() {
        Employee firstEmploy = new Employee(1, "first", 18, male, 1000);
        employeeDao.add(firstEmploy);

        assertThrows(NoSuchElementException.class, () -> employeeDao.getAll(2, 2));
    }
}
