package com.gcl.crm.service;

import com.gcl.crm.entity.ContractFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ContractFileService {
    void saveContractFile(ContractFile contractFile);
    void download(ContractFile file, HttpServletResponse httpServletResponse) throws IOException;
}
