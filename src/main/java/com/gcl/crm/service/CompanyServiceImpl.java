package com.gcl.crm.service;

import com.gcl.crm.entity.Company;
import com.gcl.crm.entity.Department;
import com.gcl.crm.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DepartmentService departmentService;

    @Override
    public List<Company> findAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        for (int i = 0; i < companies.size(); i++) {
            List<Department> departments = departmentService.findDepartmentsByCompany(companies.get(i));
            companies.get(i).setDepartments(departments);
        }
        return companies;
    }

    @Override
    public Company findCompanyById(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()){
            List<Department> departments = departmentService.findDepartmentsByCompany(company.get());
            company.get().setDepartments(departments);
            return company.get();
        }
        return null;
    }
}
