package com.gcl.crm.service;

import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Documentary;
import com.gcl.crm.entity.Task;
import com.gcl.crm.repository.DocumentRepository;
import com.gcl.crm.repository.DocumentaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentaryServiceImpl implements DocumentaryService{
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DocumentaryService documentaryService;


    @Autowired
    private DocumentRepository documentRepository;
    @Override
    public Documentary findByID(int id) {
        Optional<Documentary> option = documentRepository.findById(id);
        Documentary documentary = null ;
        if(option.isPresent()){
            documentary = option.get();
        }else {
            throw new RuntimeException("Documentary not found for id  :"+id);
        }
        return documentary;

    }

    @Override
    public void promulgateDocumentary(String docID, List<String> departmentID) {
        int docIDParse = Integer.parseInt(docID);
        List<Department> departmentList = new ArrayList<>();
        Documentary documentary = documentaryService.findByID(docIDParse);
        for(int i =0;i<departmentID.size();i++){
            departmentList.add(departmentService.findDepartmentById(departmentID.get(0).toString()));

        }

        documentary.setDepartments(departmentList);
        documentRepository.save(documentary);
    }
}

