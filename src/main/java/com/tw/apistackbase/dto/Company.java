package com.tw.apistackbase.dto;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private int id;
    private String name;
    private List<Object> employees;
    private int employeeNumber;

    public Company() {
    }

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
        this.employees = new ArrayList<>();
        this.employeeNumber = 0;
    }

    public Company(int id, String name, List<Object> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }
}
