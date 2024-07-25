package com.burdettracker.budgedtrackerproject.util;

import com.burdettracker.budgedtrackerproject.model.entity.Expense;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Utils {
    public static int getPeriodDateDayValue(String periodDateDay) {
        int result = 0;
        switch (periodDateDay) {
            case "Monday":
                result = 1;
                break;
            case "Tuesday":
                result = 2;
                break;
            case "Wednesday":
                result = 3;
                break;
            case "Thursday":
                result = 4;
                break;
            case "Friday":
                result = 5;
                break;
            case "Saturday":
                result = 6;
                break;
            case "Sunday":
                result = 7;
                break;
        }
        return result;
    }

    public static void setWeeklyDateDue(Expense expenseToEdit, String periodDate) {
        String periodDateDay = periodDate;
        int periodDateDayValue = getPeriodDateDayValue(periodDateDay);

        for (int i = 0; i < 7; i++) {
            LocalDate dateToCheck = LocalDate.now().plusDays(i);

            DayOfWeek neededDay = DayOfWeek.of(periodDateDayValue);
            if (dateToCheck.getDayOfWeek().equals(neededDay)) {
                expenseToEdit.setDateDue(dateToCheck);
            }
        }
    }

    public static void setMonthlyDateDue(Expense expenseToEdit, String periodDate) {
        String periodDateDay = periodDate;
        // 20th  => yyyy MM 20
        int todayDayOfMonth = LocalDate.now().getDayOfMonth();
        int totalMonthDays = LocalDate.now().lengthOfMonth();
        int periodDateDayValue = 0;
        if (periodDateDay.equals("Last day of month")) {
            periodDateDayValue = totalMonthDays;
        } else {
            char[] dateDayCharArr = periodDateDay.toCharArray();
            String result;
            if (periodDateDay.length() == 3) {
                //0-9
                result = String.valueOf(dateDayCharArr[0]);
                periodDateDayValue = Integer.parseInt(result);
            } else {
                //10-30
                result = String.valueOf(String.valueOf(dateDayCharArr[0]) + String.valueOf(dateDayCharArr[1]));
                periodDateDayValue = Integer.parseInt(result);
            }
        }
        //Check where DateDue is based on today's date.
        if (todayDayOfMonth <= periodDateDayValue) {
            //month stays the same
            LocalDate dateToSet = LocalDate.of(
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonth(),
                    periodDateDayValue);
            expenseToEdit.setDateDue(dateToSet);
        } else {
            //month +1
            LocalDate dateToSet = LocalDate.of(
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonth().plus(1),
                    periodDateDayValue);
            expenseToEdit.setDateDue(dateToSet);
        }

    }
}
