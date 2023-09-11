package com.vladitproger.memoryfulday.controllers;

import com.vladitproger.memoryfulday.models.Day;
import com.vladitproger.memoryfulday.repo.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private DayRepository dayRepository;

    List<String> listOfMonths = List.of(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December");

    private static int parseMonth(String monthName) {
        return switch (monthName.toLowerCase()) {
            case "january" -> 1;
            case "february" -> 2;
            case "march" -> 3;
            case "april" -> 4;
            case "may" -> 5;
            case "june" -> 6;
            case "july" -> 7;
            case "august" -> 8;
            case "september" -> 9;
            case "october" -> 10;
            case "november" -> 11;
            case "december" -> 12;
            default -> throw new IllegalArgumentException("Invalid month name");
        };
    }


    public static long convertToDateTimestamp(int year, String month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, parseMonth(month)-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (calendar.getTimeInMillis() / 1000);
    }


    @GetMapping("/{year}")
    public String yearCalendar(Model model,
                               @PathVariable int year) {
        model.addAttribute("monthsOfYear", listOfMonths);
        model.addAttribute("year", year);
        return "months_of_year";
    }

    @GetMapping("/{year}/{month}")
    public String monthCalendar(Model model,
                                @PathVariable int year,
                                @PathVariable String month) {


        int dayNumber = 1;
        long daysInMonth = YearMonth.of(year, parseMonth(month)).lengthOfMonth();
        System.out.println("daysInMonth:" + daysInMonth);

        ArrayList<Day> dayArray = new ArrayList<>();

        System.out.println("findAll():" + dayRepository.findAll());

        while (dayNumber < daysInMonth) {
            long timestamp = convertToDateTimestamp(year, month, dayNumber);

            if (!dayRepository.existsById(timestamp)) {
                Day newDay = new Day(timestamp);
                dayArray.add(newDay);
                System.out.println("\n(if(!timestamp)) Current day: " + dayNumber + " timestamp: " + timestamp);
                dayNumber++;
                continue;
            }

            Optional<Day> currentDay = dayRepository.findById(timestamp);
            currentDay.ifPresent(dayArray::add);

            System.out.println("\n(Day presents) Current day: " + dayNumber + " timestamp:" + timestamp);

            dayNumber++;
        }

        model.addAttribute("dayArray", dayArray);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("dayNumber", dayNumber);
        model.addAttribute("daysInMonth", daysInMonth);

        System.out.println("\n\nEnd of method");


        return "days_of_month";
    }

    @GetMapping("/{year}/{month}/{dayNumber}")
    public String day(Model model,
                      @PathVariable int year,
                      @PathVariable String month,
                      @PathVariable int dayNumber) {

        long timestamp = convertToDateTimestamp(year, month, dayNumber);
        System.out.println(timestamp);
        Optional<Day> gotDay = dayRepository.findById(timestamp);
        ArrayList<Day> currentDay = new ArrayList<>();

//        gotDay.ifPresent(currentDay::add);

        if (gotDay.isPresent()) {
            currentDay.add(gotDay.get());
        } else {
            currentDay.add(new Day(timestamp));
        }

        System.out.println("gotDay:" + gotDay);
        System.out.println("currentDay:" + currentDay.get(0).toString());

        model.addAttribute("gotDay", gotDay);
        model.addAttribute("currentDay", currentDay);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("dayNumber", dayNumber);
        return "full_day";
    }


    @PostMapping("/{year}/{month}/{dayNumber}")
    public String saveDay(Model model,
                          @ModelAttribute("gotDay") Day currentDay,
                          @PathVariable int year,
                          @PathVariable String month,
                          @PathVariable int dayNumber
//                          @RequestParam("mainImage") String mainImage
    ) {


        /*
        if (!mainImage.isEmpty()) {
            String fileName = mainImage.getOriginalFilename();
            System.out.println("static/images/   " + fileName);
            System.out.println("MainImage:" + mainImage);
            try {
                byte[] bytes = mainImage.getBytes();
                Path path = Paths.get("static/images/" + fileName);
                Files.write(path, bytes);

                currentDay.setMainImage(mainImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         */

        currentDay.setTimestamp(convertToDateTimestamp(year, month, dayNumber));

        System.out.println("(postMapping) currentDay:" + currentDay);

        dayRepository.save(currentDay);

        return "redirect:/calendar/" + year + "/" + month + "/" + dayNumber;
    }
}