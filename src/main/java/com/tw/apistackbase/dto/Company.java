package com.tw.apistackbase.dto;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private int id;
    private String name;
    private List<Employee> employees;
    private int employeeNumber;

    public Company() {
    }

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
        this.employees = new ArrayList<>();
        this.employeeNumber = 0;
    }

    public Company(int id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
