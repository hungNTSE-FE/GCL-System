package com.gcl.crm.service;

import com.gcl.crm.entity.Company;
import com.gcl.crm.entity.Department;
import com.gcl.crm.enums.Status;
import com.gcl.crm.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAllDepartments() {
        return departmentRepository.findAllByStatus(Status.ACTIVE);
    }

    @Override
    public Department findDepartmentById(String id) {
        Long departmentId;
        try {
            departmentId = Long.parseLong(id);
        } catch (NumberFormatException ex){
            return null;
        }
        Department department = departmentRepository.findByIdAndStatus(departmentId, Status.ACTIVE);
        return department;
    }

    @Override
    public boolean createDepartment(Department department) {
        Department depart = departmentRepository.save(department);
        return depart != null;
    }

    @Override
    public boolean updateDepartment(Department department) {
        if (department.getId() == null){
            return false;
        }
        Department depart = departmentRepository.save(department);
        return depart != null;
    }

    @Override
    public boolean deleteDepartment(String id) {
        Department department = this.findDepartmentById(id);
        if (department == null){
            return false;
        }
        department.setStatus(Status.INACTIVE);
        Department depart = departmentRepository.save(department);
        return depart != null;
    }

    @Override
    public List<Department> findDepartmentsByCompany(Company company) {
        return departmentRepository.findAllByStatusAndCompany(Status.ACTIVE, company);
    }

    @Override
    public List<Department> findDepartmentsByIdList(List<Long> idList) {
        if (idList == null){
            return null;
        }
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            Department department = this.findDepartmentById(idList.get(i).toString());
            if (department != null){
                departments.add(department);
            }
        }
        return departments;
    }
}
