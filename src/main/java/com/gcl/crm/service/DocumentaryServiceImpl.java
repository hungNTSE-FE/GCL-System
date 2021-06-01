package com.gcl.crm.service;

import com.gcl.crm.entity.Documentary;
import com.gcl.crm.entity.Task;
import com.gcl.crm.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentaryServiceImpl implements DocumentaryService{

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
    }

