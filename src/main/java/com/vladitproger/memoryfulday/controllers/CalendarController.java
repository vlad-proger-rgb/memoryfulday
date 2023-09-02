package com.vladitproger.memoryfulday.controllers;

import com.vladitproger.memoryfulday.models.Day;
import com.vladitproger.memoryfulday.repo.DayRepository;
import com.vladitproger.memoryfulday.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private PostRepository postRepository;


    List<String> listOfMonths = List.of(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December");

    // Метод для преобразования названия месяца в месяц в виде числа (1 - январь, 2 - февраль и т.д.)
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


    public static long convertToDateTimestamp(int year, String month, int day) throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMMM dd");
        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(dateFormat.parse(year + " " + parseMonth(month) + " " + day));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, parseMonth(month));
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println("calendar timeInMillis " + calendar.getTimeInMillis());
        return (calendar.getTimeInMillis() / 1000); // Преобразование в секунды и int
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
                                @PathVariable String month) throws ParseException {


        int dayNum = 1;
        long daysInMonth = YearMonth.of(year, parseMonth(month)).lengthOfMonth();
        ArrayList<Day> daysRes = new ArrayList<>();

        System.out.println("findAll():" + dayRepository.findAll());
        System.out.println("(post) finaAll():" + postRepository.findAll());

        while (dayNum < daysInMonth) {
//            long timestamp = convertToDateTimestamp(year, month, dayNum);

//            Optional<Day> currentDay = dayRepository.findById(1693519200L);

//            currentDay.ifPresent(daysRes::add);

            Optional<Day> currentDay = dayRepository.findById(1693519200L);
            if (currentDay.isPresent()) {
                daysRes.add(currentDay.get());
            } else {
                // Обработка случая, когда запись не найдена в базе данных
                System.out.println("No data");
            }
            dayNum++;
            System.out.println(currentDay);
            System.out.println(daysRes);
        }

        model.addAttribute("days", daysRes);

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("daysInMonth", daysInMonth);
        return "days_of_month";
    }

    @GetMapping("/{year}/{month}/{day}")
    public String day(Model model,
                      @PathVariable int year,
                      @PathVariable String month,
                      @PathVariable int day) throws ParseException {

        long timestamp = convertToDateTimestamp(year, month, day);
        Optional<Day> currentDay = dayRepository.findById(timestamp);
        model.addAttribute("day", currentDay);

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("dayNumber", day);
        return "full_day";
    }


    @PostMapping("/calendar/{year}/{month}/{dayNumber}/edit")
    public String saveDay(Model model, @ModelAttribute("day") Day day) {

        dayRepository.save(day);

        return "redirect:/calendar/{year}/{month}/{dayNumber}";
    }



}
