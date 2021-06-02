package com.gcl.crm.service;

import com.gcl.crm.entity.Documentary;
import com.gcl.crm.entity.Task;

import java.util.List;

public interface DocumentaryService {
    Documentary findByID(int id);
    void promulgateDocumentary(String docID, List<String> deparmentID);
}

