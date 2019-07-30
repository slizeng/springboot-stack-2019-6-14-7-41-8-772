package com.tw.apistackbase.controller;

import com.google.common.collect.ImmutableList;
import com.tw.apistackbase.dto.Company;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/companies", produces = "application/json")
public class CompanyController {

    private List<Company> companies = new ArrayList<>();

    @GetMapping
    public List<Company> getAll() {
        return companies;
    }

    public Company addCompany(Company company) {
        return company;
    }
}
