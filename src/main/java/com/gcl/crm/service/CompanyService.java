package com.gcl.crm.service;

import com.gcl.crm.entity.Company;

import java.util.List;

public interface CompanyService {
    List<Company> findAllCompanies();
    Company findCompanyById(Long id);
}
