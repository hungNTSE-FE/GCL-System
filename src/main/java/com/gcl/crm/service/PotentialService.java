package com.gcl.crm.service;

import com.gcl.crm.entity.*;
import com.gcl.crm.enums.Status;
import com.gcl.crm.form.EmployeeSearchForm;
import com.gcl.crm.form.PotentialSearchForm;
import com.gcl.crm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PotentialService {

    @Autowired
    DiaryService diaryService;

    @Autowired
    PotentialRepository potentialRepository;

    @Autowired
    PotentialRepository2 potentialRepository2;

    @Autowired
    LevelService levelService;

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    CareRepository careRepository;

    @Autowired
    SourceService sourceService;

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    UserService userService;

    @Transactional
    public boolean importPotential(List<Potential> potentials, User user){
        if (potentials.size() == 0){
            return false;
        }
        for (Potential potential : potentials){

            if (potentialRepository.findAllByPhoneNumberOrEmail(potential.getPhoneNumber(), potential.getEmail()).size() > 0){
                continue;
            }
            String sourceName = potential.getSourceName();
            Source source = sourceService.getSourceByName(sourceName);
            potential.setSource(source);
            potential.setStatus(Status.ACTIVE);
            potential.setMaker(user.getEmployee().getId());
            potentialRepository.save(potential);
        }
        potentialRepository2.ratingPotential();
        return true;
    }

    public boolean editPotential(Potential newPotential, User currentUser){
        Potential potential = potentialRepository.findPotentialByIdAndStatus(newPotential.getId(), Status.ACTIVE);
        if (potential == null){
            return false;
        }
        potential.setName(newPotential.getName());
        potential.setAddress(newPotential.getAddress());
        potential.setEmail(newPotential.getEmail());
        potential.setPhoneNumber(newPotential.getPhoneNumber());
        potential.setSource(sourceService.getSourceById(newPotential.getSource().getSourceId()));
        potential.setLastModified(getCurrentDate());
        potential.setLastModifier(currentUser.getEmployee().getId());
        potential = potentialRepository.save(potential);
        diaryService.createDiary("Thông tin đầu mối được cập nhật", currentUser, potential);
        return true;
    }

    public boolean editLevelPotential(Long id, int levelId, User currentUser) {
        Potential potential = potentialRepository.findPotentialByIdAndStatus(id, Status.ACTIVE);
        if (potential == null) {
            return false;
        }
        Level level = levelRepository.findByLevelId(levelId);
        potential.setLevel(level);
        potential.setLastModifier(currentUser.getEmployee().getId());
        potential.setLastModified(this.getCurrentDate());
        potential = potentialRepository.save(potential);
        diaryService.createDiary("Cập nhật cấp độ", currentUser, potential);
        return true;
    }

    public Potential getPotentialById(Long id){
        return potentialRepository.findPotentialByIdAndStatus(id, Status.ACTIVE);
    }

    public void removePotentials(List<Long> idList, User currentUser){
        for (Long id : idList){
            Potential potential = potentialRepository.findPotentialByIdAndStatus(id, Status.ACTIVE);
            potential.setStatus(Status.INACTIVE);
            potential.setLastModified(this.getCurrentDate());
            potential.setLastModifier(currentUser.getEmployee().getId());
            potential = potentialRepository.save(potential);
            diaryService.createDiary("Bị xóa và được chuyển tới thùng rác", currentUser, potential);
        }
    }

    public boolean createPotential(Potential potential, User user) {
        String sourceName = potential.getSourceName();
        Source source = sourceService.getSourceByName(sourceName);
        potential.setSource(source);
        potential.setStatus(Status.ACTIVE);
        potential.setMaker(user.getUserId());
        potential.setDate(getStringCurrentDate());
        Potential poten = potentialRepository.save(potential);
        diaryService.createDiary("Đầu mối được tạo", user, potential);
        return poten != null;
    }

    public boolean resetPotential(Long id,User currentUser) {
        Potential potential = potentialRepository.findPotentialByIdAndStatus(id, Status.INACTIVE);
        if (potential == null) {
            return false;
        }
        potential.setStatus(Status.ACTIVE);
        Potential potential1 = potentialRepository.save(potential);
        diaryService.createDiary("Đầu mối được phục hồi từ thùng rác", currentUser, potential);
        return potential1 != null;
    }

    public List<Potential> getPotentialsByIdList(List<Long> aidList) {
        if (aidList == null) {
            return null;
        }
        List<Potential> potentials = new ArrayList<>();
        for (int i=0; i < aidList.size(); i++) {
            Optional<Potential> potential = potentialRepository.findById(aidList.get(i));
            if (potential.isPresent()) {
                potentials.add(potential.get());
            }
        }
        return potentials;
    }

    public boolean resetAllPotential(List<Long> checkedPotential, User currentUser) {
        for (int i = 0; i < checkedPotential.size(); i++) {
            Potential potential = potentialRepository.findPotentialByIdAndStatus(checkedPotential.get(i), Status.INACTIVE);
            potential.setStatus(Status.ACTIVE);
            potential.setLastModified(this.getCurrentDate());
            potential.setLastModifier(currentUser.getEmployee().getId());
            potential = potentialRepository.save(potential);
            diaryService.createDiary("Đầu mối được phục hồi từ thùng rác", currentUser, potential);
        }
        return true;
    }

    public List<Potential> getAllPotentials(){
        List<Potential> potentials = potentialRepository.findAllByStatus(Status.ACTIVE);
        Collections.sort(potentials);
        return potentials;
    }

    public List<Potential> getAllDeletedPotentials(){
        List<Potential> potentials = potentialRepository.findAllByStatus(Status.INACTIVE);
        Collections.sort(potentials);
        return potentials;
    }

    public List<Potential> search(PotentialSearchForm searchForm){
        Level level = null;
        if (searchForm.getLevel() != null){
            level = levelService.getLevelById(searchForm.getLevel());
        }
        Source source = sourceService.getSourceByName(searchForm.getSource());
        List<Potential> potentials = potentialRepository
                .findAllByNameContainingAndPhoneNumberContainingAndEmailContainingAndStatus
                        (searchForm.getName(), searchForm.getPhone(), searchForm.getEmail(), Status.ACTIVE);
        if (searchForm.getTime() ==  null || searchForm.getTime().isEmpty()) {
            return potentials;
        }
        String[] dateRange = searchForm.getTime().split("-");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = simpleDateFormat.parse(dateRange[0].trim());
            date2 = simpleDateFormat.parse(dateRange[1].trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Potential> result = new ArrayList<>();
        for (int i = 0; i < potentials.size(); i++) {
            Potential potential = potentials.get(i);
            boolean flag = true;
            Date date = null;
            try {
                date = simpleDateFormat.parse(potential.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if ((date.before(date1) || date.after(date2))){
                flag = false;
            }
            if (level != null){
                if (!level.equals(potential.getLevel())){
                    flag = false;
                }
            }
            if (source != null){
                if (!source.equals(potential.getSource())){
                    flag = false;
                }
            }
            if (flag){
                result.add(potential);
            }
        }
        Collections.sort(result);
        return result;
    }

    public boolean isPhoneExisted(String phone, Long id){
        Potential potential = (id != null) ? potentialRepository.findPotentialByPhoneNumberAndIdNot(phone, id)
                : potentialRepository.findPotentialByPhoneNumber(phone);
        return potential != null;
    }

    public boolean isEmailExisted(String email, Long id){
        Potential potential = (id != null) ? potentialRepository.findPotentialByEmailAndIdNot(email, id)
                : potentialRepository.findPotentialByEmail(email);
        return potential != null;
    }

    public List<PotentialSearchForm> getListPotentialToShare(List<Long> listSelectedId) {
        return potentialRepository2.getListPotentialToShare(listSelectedId);
    }

    public boolean addTakeCarePotentialDetail(Potential potential, User user, String description){
        Care care = new Care();
        care.setDescription(description);
        care.setPotential(potential);
        care.setLastModified(this.getCurrentDate());
        care.setLastModifier(user.getUserId());
        care.setModifierName(user.getEmployee().getName());
        care.setAccepted(false);
        Care confirm = careRepository.save(care);
        diaryService.createDiary("Cập nhật chăm sóc lần " + potential.getCares().size(), user, potential);
        return confirm.equals(care);
    }

    public boolean acceptTakeCareInfo(Potential potential, User user, Integer index){
        List<Care> cares = potential.getCares();
        if (cares.size() <= index){
            return false;
        }
        Care care = cares.get(index);
        if (care.isAccepted()){
            return false;
        }
        care.setAccepted(true);
        care.setAcceptDate(this.getCurrentDate());
        care.setAcceptor(user.getEmployee().getId());
        Care confirm = careRepository.save(care);
        return care.equals(confirm);
    }

    public List<EmployeeSearchForm> getEmployeeByDepartmentId(Long id) {
        return employeesRepository.getListEmployeeByDepartmentId(id);
    }

    public String getDepartmentByUserName(User user) {
        String depName = user.getEmployee().getDepartment().getName();
        if (depName.toUpperCase().contains("SALE")) {
            return "SALE";
        }
        if (depName.toUpperCase().contains("MARKETING")) {
            return "MARKETING";
        }
        return null;
    }

    public List<Potential> getAllPotentialsOfSale(User user){
        return potentialRepository2.getListPotentialOfSale(user.getEmployee().getMarketingGroup().getId());
    }

    private Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
    private String getStringCurrentDate() {
        String newYorkDateTimePattern = "dd/MM/yyyy";
        DateTimeFormatter newYorkDateFormatter = DateTimeFormatter.ofPattern(newYorkDateTimePattern);
        String formattedDate = newYorkDateFormatter.format(LocalDate.now());
        return formattedDate;
    }
}
