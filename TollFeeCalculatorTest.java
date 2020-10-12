package Labb4;

import org.junit.jupiter.api.Test;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TollFeeCalculatorTest {


    @Test
    void tollFeeCalculator() {
        File file = new File("FileCrawler/src/Labb4/Lab4.txt");

        assertThrows(TollFeeCalculator.class, () -> TollFeeCalculator.tollFeeCalculator(file));
    }
    @Test
    void exceptionTesting() {
        MyException thrown = assertThrows(
                MyException.class,
                () -> myObject.doThing(),
                "Expected doThing() to throw, but it didn't"
        );

    @Test
    void testExpectedException() {

        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("One");
        });

    }
    @Test
    void getTollFeePerPassing() {
//        String[] dateStrings = {2020-06-30 00:05, 2020-06-30 06:34, 2020-06-30 08:52, 2020-06-30 10:13, 2020-06-30 10:25, 2020-06-30 11:04, 2020-06-30 16:50, 2020-06-30 18:00, 2020-06-30 21:30, 2020-07-01 00:00}
//        LocalDateTime[] dates = new LocalDateTime[];
//        dates[] = {2020-06-30 00:05, 2020-06-30 06:34, 2020-06-30 08:52, 2020-06-30 10:13, 2020-06-30 10:25, 2020-06-30 11:04, 2020-06-30 16:50, 2020-06-30 18:00, 2020-06-30 21:30, 2020-07-01 00:00}

        LocalDateTime date = LocalDateTime.parse("2020-06-30 06:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(13, TollFeeCalculator.getTollFeePerPassing(date));

        LocalDateTime date2 = LocalDateTime.parse("2020-06-30 15:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        assertEquals(18, TollFeeCalculator.getTollFeePerPassing(date2));

    }

    @Test
    void getTotalFeeCost() {
        LocalDateTime[] date = new LocalDateTime[8];

        date[0] = LocalDateTime.parse("2020-06-30 10:13", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //8
        date[1] = LocalDateTime.parse("2020-06-30 10:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // (8)
        date[2] = LocalDateTime.parse("2020-06-30 14:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 8 = 16
        date[3] = LocalDateTime.parse("2020-06-30 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13 = 21
        date[4] = LocalDateTime.parse("2020-06-30 15:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // (13)
        date[5] = LocalDateTime.parse("2020-06-30 15:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // (13)
        date[6] = LocalDateTime.parse("2020-06-30 16:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // + 18 - 13 = 26
        date[7] = LocalDateTime.parse("2020-06-30 17:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13 = 39
        assertEquals(39, TollFeeCalculator.getTotalFeeCost(date)); //
    }

    @Test
    void isTollFeeDate() {
        // Testar weekends och en dag i juli
        LocalDateTime date = LocalDateTime.parse("2020-06-13 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //lördag
        assertTrue(TollFeeCalculator.isTollFreeDate(date));

        LocalDateTime date2 = LocalDateTime.parse("2020-06-14 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //söndag
        assertTrue(TollFeeCalculator.isTollFreeDate(date2));

        LocalDateTime date3 = LocalDateTime.parse("2020-07-14 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //juli
        assertTrue(TollFeeCalculator.isTollFreeDate(date3));
    }


}