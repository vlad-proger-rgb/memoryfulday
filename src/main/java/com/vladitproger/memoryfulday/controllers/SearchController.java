package com.vladitproger.memoryfulday.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/")
    public String search(Model model) {

        return "search";
    }

    @GetMapping
    public String searchInDB(Model model,
                             @RequestParam("q") String query,
                             @RequestParam("startDate") String startDate,
                             @RequestParam("endDate") String endDate,
                             @RequestParam("tags") String tags) {




        return "search_result";
    }

}
