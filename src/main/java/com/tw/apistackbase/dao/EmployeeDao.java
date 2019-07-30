package com.tw.apistackbase.dao;

import com.google.common.collect.ImmutableList;
import com.tw.apistackbase.entity.Employee;
import com.tw.apistackbase.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeDao {
    private List<Employee> employeeRepository = new ArrayList<>();

    public Employee add(Employee employee) {
        employeeRepository.add(employee);

        return employee;
    }

    public List<Employee> getAll() {
        return ImmutableList.copyOf(employeeRepository);
    }

    public Employee get(int id) {
        return employeeRepository.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }
}
