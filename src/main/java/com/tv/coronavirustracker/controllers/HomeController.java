package com.tv.coronavirustracker.controllers;

import com.tv.coronavirustracker.models.LocationStats;
import com.tv.coronavirustracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getStats();
        int totalReportedCases = allStats.stream().mapToInt(locationStats -> locationStats.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(locationStats -> locationStats.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";
    }
}
