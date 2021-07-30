package com.gcl.crm.controller;

import com.gcl.crm.entity.Potential;
import com.gcl.crm.form.RecentPotentialForm;
import com.gcl.crm.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/statistic")
public class StatisticController {
    private static final String STATISTIC_POTENTIAL_PAGE = "/statistic/statistic-potential-page";

    @Autowired
    StatisticService statisticService;

    @RequestMapping(value = "/potential", method = RequestMethod.GET)
    public String goStatisticPotential(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return STATISTIC_POTENTIAL_PAGE;
    }

    /*
    Return list 12 integer number of potentials present volatility
    Return null if year is empty or not match [yyyy]
    URL: http://localhost:8085/statistic/potential/volatility?year=2021
     */
    @GetMapping(value = {"/potential/volatility"})
    @ResponseBody
    public List<Integer> getPotentialVolatility(@Nullable @RequestParam("year") String year){
        if (year == null){
            return null;
        }
        if (!year.matches("[0-9]{4}")){
            return null;
        }
        return statisticService.getPotentialVolatility(year);
    }

    /*
    Return list potentials of current date
    URL: http://localhost:8085/statistic/potential/today
     */
    @GetMapping(value = {"/potential/today"})
    @ResponseBody
    public List<RecentPotentialForm> getTodayPotential(){
        return statisticService.getTodayPotential();
    }

    /*
    Return list 8 integers present number of potential count by level
    Return null if date is empty or not match [M/yyyy] and [MM/yyyy]
    URL: http://localhost:8085/statistic/potential/level?date=05/2021
     */
    @GetMapping(value = {"/potential/level"})
    @ResponseBody
    public List<Integer> getPotentialLevel(@Nullable @RequestParam("date") String date){
        if (date == null)
            return null;
        if (!date.matches("[0-9]{1,2}[/][0-9]{4}")){
            return null;
        }
        return statisticService.countPotentialByDateGroupByLevel(date);
    }
}
