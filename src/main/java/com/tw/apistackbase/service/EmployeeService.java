package com.tw.apistackbase.service;

import com.tw.apistackbase.repository.EmployeeRepository;
import com.tw.apistackbase.dto.Employee;
import com.tw.apistackbase.dto.GENDER;
import com.tw.apistackbase.exception.CannotAddEmployeeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.selectAll();
    }

    public Employee addEmployee(Employee employee) {
        return ofNullable(employee)
                .map(targetEmploy -> {
                    try {
                        return employeeRepository.add(targetEmploy);
                    } catch (Exception ignored) {
                        return null;
                    }
                })
                .orElseThrow(CannotAddEmployeeException::new);
    }

    public Employee getCertainEmployee(int id) {
        return employeeRepository.select(id);
    }

    public List<Employee> getPagedEmployees(int page, int pageSize) {
        return employeeRepository.selectAll(page, pageSize);
    }

    public List<Employee> getEmployeesByGender(GENDER gender) {
        return employeeRepository.selectAll(gender);
    }

    public Employee updateEmploy(int id, Employee newEmployee) {
        return employeeRepository.update(id, newEmployee);
    }
}
