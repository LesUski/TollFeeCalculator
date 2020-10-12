package Labb4;

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

    public TollFeeCalculator() {
        File file = new File("FileCrawler/src/Labb4/Lab4.txt");
        tollFeeCalculator(file);
    }

    public static void tollFeeCalculator(File inputFile) { //1
        try {
            Scanner sc = new Scanner(inputFile);
            String[] dateStrings = sc.nextLine().split(", ");
            LocalDateTime[] dates = new LocalDateTime[dateStrings.length]; //1 tagit bort lenght-1

            for (int i = 0; i < dates.length; i++) {
                dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }

            }
            System.out.println("The total fee for the inputfile is " + getTotalFeeCost(dates));
            sc.close();                                                                         //2 added scanner.close
        } catch(IOException e) {

            System.err.println("Could not read file " + inputFile + " " + e.getMessage());
        }
    }

    public static int getTotalFeeCost(LocalDateTime[] dates) {
        int totalFee = 0;

        LocalDateTime intervalStart = dates[0];
        for (LocalDateTime date : dates) {                                  //3 tagit bort System.out
            long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);
            if (diffInMinutes > 60 || diffInMinutes == 0) {
                totalFee += getTollFeePerPassing(date);
                intervalStart = date;
            } else {
                if (getTollFeePerPassing(date) > getTollFeePerPassing(intervalStart)){
                    totalFee += Math.max(getTollFeePerPassing(date), getTollFeePerPassing(intervalStart));
                    totalFee -= getTollFeePerPassing(intervalStart);
                }

                if (!date.equals(intervalStart)) {
                    intervalStart = date;}
            }                                                                                                   //5 return bara totalFee
        }
        return Math.min(totalFee, 60);
    }

    public static int getTollFeePerPassing(LocalDateTime date) {
        if (isTollFreeDate(date)) return 0;

        int hour = date.getHour();
        int minute = date.getMinute();
        if (hour == 6 && minute <= 29) return 8;                                //6
        else if (hour == 6) return 13;
        else if (hour == 7) return 18;
        else if (hour == 8 && minute <= 29) return 13;
        else if (hour >= 8 && hour < 15) return 8;                  //7 fixat tid
        else if (hour == 15 && minute <= 29) return 13;
        else if (hour >= 15 && hour <= 16) return 18;               //8 fixat tid
        else if (hour == 17 ) return 13;
        else if (hour == 18 && minute <= 29) return 8;
        else return 0;
    }

    public static boolean isTollFreeDate(LocalDateTime date) {
        return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || date.getMonth().getValue() == 7;
    }

    public static void main(String[] args) {

        new TollFeeCalculator();

    }
}