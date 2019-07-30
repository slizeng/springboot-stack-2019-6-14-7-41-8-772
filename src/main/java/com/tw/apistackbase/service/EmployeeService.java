package com.tw.apistackbase.service;

import com.tw.apistackbase.dao.EmployeeDao;
import com.tw.apistackbase.entity.Employee;
import com.tw.apistackbase.exception.CannotAddEmployeeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class EmployeeService {
    @Autowired
    EmployeeDao employeeDao;

    public List<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    public Employee addEmployee(Employee employee) {
        return ofNullable(employee)
                .map(targetEmploy -> {
                    try {
                        return employeeDao.add(targetEmploy);
                    } catch (Exception ignored) {
                        return null;
                    }
                })
                .orElseThrow(CannotAddEmployeeException::new);
    }

    public Employee getCertainEmployee(int id) {
        return employeeDao.get(id);
    }
}
