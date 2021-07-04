package com.gcl.crm.controller;

import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Documentary;
import com.gcl.crm.enums.Status;
import com.gcl.crm.repository.DocumentaryRepository;
import com.gcl.crm.service.DepartmentService;
import com.gcl.crm.service.DocumentaryService;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/documentary")
public class DocumentaryController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DocumentaryService documentaryService;

    @Autowired
    private DocumentaryRepository documentaryRepo;




    @GetMapping({"/home"})
    public String showDocumentaryHomePage(Model model){
        List<Documentary> listDocs = documentaryRepo.findAll();

        model.addAttribute("listDocs",listDocs);
        return "documentary/documentary-home";
    }
    @GetMapping({"/"})
    public String showDocumentaryHomePagev2(Model model){
        List<Documentary> listDocs = documentaryRepo.findAll();

        model.addAttribute("listDocs",listDocs);
        return "documentary/upload-documentary-v2";
    }
    @GetMapping({"/uploadPage"})
    public String showUploadPage(Model model){

        return "documentary/upload-documentary-v2";
    }
    @PostMapping({"/upload"})
    public String uploadDocumentary(@RequestParam("documentary") MultipartFile multipartFile, RedirectAttributes ra) throws IOException, ParseException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Documentary documentary = new Documentary();
        documentary.setName(filename);
        documentary.setContent(multipartFile.getBytes());
        documentary.setSize(multipartFile.getSize());
        documentary.setUploadTime(WebUtils.getSystemDate());
        documentary.setActive(Status.ACTIVE);
        documentaryRepo.save(documentary);
        ra.addFlashAttribute("message","Công văn đã được gửi");
        return "redirect:/documentary/home";
    }
    @GetMapping({"/download"})
    public void downloadDocumentary(@Param("id") int id, HttpServletResponse response) throws Exception {
        Optional<Documentary> result = documentaryRepo.findById(id);
        if(!result.isPresent()){
            throw new Exception("Không tìm thấy công văn !!!");
        }
        Documentary documentary = result.get();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue ="attachment; filename=" + documentary.getName();
        response.setHeader(headerKey,headerValue);
        ServletOutputStream outputStream =response.getOutputStream();
        outputStream.write(documentary.getContent());
        outputStream.close();
    }
    @GetMapping({"/promulgatePage/{id}"})
    public String promulgateDocumentary(Model model,@PathVariable(name="id") int id){
        Optional<Documentary> documentary= documentaryRepo.findById(id);
        if(documentary.isPresent()){
            model.addAttribute("documentary",documentary.get());

        }
        List<Department> filterDepartment = departmentService.findAllDepartments();
        List<Department> departmentList = departmentService.findAllDepartments();
        for(int i = 0 ;i < departmentList.size();i++){
            for(int j = 0 ;j<departmentList.get(i).getDocumentaries().size();j++){
                if(departmentList.get(i).getDocumentaries().get(j).getName().equals(documentary.get().getName())){
                    filterDepartment.remove(departmentList.get(i));
                }
            }
        }

        model.addAttribute("departmentList",filterDepartment);


        return"documentary/promulgate-documentary-page-v2";
    }
    @PostMapping({"/promulgate"})
    public String promulgateDocumentary(@RequestParam(required=false,value="documentary_id") String docID,@RequestParam(required=false,value="department_id_check") List<String> departmentID){

        documentaryService.promulgateDocumentary(docID,departmentID);
        return "redirect:/documentary/home";
    }
}
