package com.kyh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Scanner;

public class TollFeeCalculator {

    public TollFeeCalculator() throws FileNotFoundException{
        File file = new File("src/com/kyh/Lab4.txt");  //1 build a new Constuctor to be able to test a method @Test throwsException() 1
        tollFeeCalculator(file);
    }

    public static void tollFeeCalculator(File inputFile) throws FileNotFoundException { // changed Contructor due to String convertion testas i throws Exception()

        try {
            Scanner sc = new Scanner(inputFile);
            String[] dateStrings = sc.nextLine().split(", ");
            LocalDateTime[] dates = new LocalDateTime[dateStrings.length]; //1 test i readFullArray()

            try {
                for (int i = 0; i < dates.length; i++) {
                    dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                }

            } catch (DateTimeParseException e) {
                throw e;
            }
            System.out.println("The total fee for the inputfile is " + getTotalFeeCost(dates));
            sc.close();                                                                         // Scanner close

        } catch (FileNotFoundException e){
            throw new FileNotFoundException("Couldn't now find file");
        }
    }

    public static int getTotalFeeCost(LocalDateTime[] dates) {
        int totalFee = 0;

        LocalDateTime intervalStart = dates[0];
        totalFee += getTollFeePerPassing(dates[0]);                         //testFirstPassing()

        for (LocalDateTime date : dates) {                                  //3 tagit bort System.out

            long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);

            if (diffInMinutes > 60) {
                totalFee += getTollFeePerPassing(date);
                intervalStart = date;

            } else {
                if (getTollFeePerPassing(date) > getTollFeePerPassing(intervalStart)){
                    totalFee += Math.max(getTollFeePerPassing(date), getTollFeePerPassing(intervalStart));
                    totalFee -= getTollFeePerPassing(intervalStart);
                }

                if (!date.equals(intervalStart)) {
                    intervalStart = date;}
            }
        }
        return Math.min(totalFee, 60);              //Math.max to Math.min test i getMaximalCost
    }

    public static int getTollFeePerPassing(LocalDateTime date) {

        if (isTollFreeDate(date)) return 0;

        int hour = date.getHour();
        int minute = date.getMinute();

        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6) return 13;
        else if (hour == 7) return 18;
        else if (hour == 8 && minute <= 29) return 13;
        else if (hour >= 8 && hour < 15) return 8;                  //7 fixat tid testCostSpecificPassingTimes()
        else if (hour == 15 && minute <= 29) return 13;
        else if (hour >= 15 && hour <= 16) return 18;               //8 fixat tid testCostSpecificPassingTimes()
        else if (hour == 17 ) return 13;
        else if (hour == 18 && minute <= 29) return 8;
        else return 0;
    }

    public static boolean isTollFreeDate(LocalDateTime date) {      //isTollFeeWeekend() & void isTollFeeMonth()
        return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || date.getMonth().getValue() == 7;
    }

    public static void main(String[] args) throws FileNotFoundException {

        new TollFeeCalculator();

    }
}