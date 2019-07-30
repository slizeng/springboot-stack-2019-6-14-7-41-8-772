package com.tw.apistackbase.repository;

import com.tw.apistackbase.dto.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.collect.ImmutableList.of;
import static com.tw.apistackbase.dto.GENDER.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeRepositoryTest {
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository = new EmployeeRepository();
    }

    @Test
    void should_return_paged_employee_succeed() {
        Employee firstEmploy = new Employee(1, "first", 18, male, 1000);
        Employee secondEmploy = new Employee(2, "first", 18, male, 1000);
        Employee thirdEmploy = new Employee(3, "first", 18, male, 1000);
        employeeRepository.add(firstEmploy);
        employeeRepository.add(secondEmploy);
        employeeRepository.add(thirdEmploy);
        List<Employee> firstPageEmployees = of(firstEmploy, secondEmploy);
        List<Employee> secondPageEmployees = of(thirdEmploy);

        List<Employee> firstQueryResult = employeeRepository.selectAll(1, 2);
        List<Employee> secondQueryResult = employeeRepository.selectAll(2, 2);

        assertEquals(firstPageEmployees, firstQueryResult);
        assertEquals(secondPageEmployees, secondQueryResult);
    }

    @Test
    void should_return_NoSuchElementException_when_page_exceed() {
        Employee firstEmploy = new Employee(1, "first", 18, male, 1000);
        employeeRepository.add(firstEmploy);

        assertThrows(NoSuchElementException.class, () -> employeeRepository.selectAll(2, 2));
    }
}
