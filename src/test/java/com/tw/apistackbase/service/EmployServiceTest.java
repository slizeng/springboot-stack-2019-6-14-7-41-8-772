package com.tw.apistackbase.service;

import com.tw.apistackbase.dao.EmployeeDao;
import com.tw.apistackbase.entity.Employee;
import com.tw.apistackbase.exception.CannotAddEmployeeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployServiceTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeDao employeeDao;

    private Employee employee = new Employee(1, "name", 18, "male");

    @Test
    public void should_return_employees_as_list_when_get_all_employees_succeed() {
        List<Employee> employeeEntities = of(employee);
        when(employeeDao.getAll()).thenReturn(employeeEntities);

        List<Employee> allEmployees = employeeService.getAllEmployees();

        assertEquals(employeeEntities, allEmployees);
    }

    @Test
    public void should_return_employee_when_add_an_employee_succeed() {
        when(employeeDao.add(employee)).thenReturn(employee);

        Employee returnedEmployee = employeeService.addEmployee(employee);

        assertEquals(employee, returnedEmployee);
    }

    @Test
    public void should_throw_CannotAddEmployeeException_when_add_an_employee_failed() {
        when(employeeDao.add(employee)).thenThrow(new RuntimeException());

        assertThrows(CannotAddEmployeeException.class, () -> employeeService.addEmployee(employee));
    }

    @Test
    public void should_throw_CannotAddEmployeeException_when_dao_return_null() {
        when(employeeDao.add(employee)).thenReturn(null);

        assertThrows(CannotAddEmployeeException.class, () -> employeeService.addEmployee(employee));
    }
}
