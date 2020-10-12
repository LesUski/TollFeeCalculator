package com.kyh;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static org.junit.jupiter.api.Assertions.*;

class TollFeeCalculatorTest {

    @Test
    void throwsException() {
        File file = new File("src/com/kyh/Lab.txt");

        assertThrows(FileNotFoundException.class, () -> TollFeeCalculator.tollFeeCalculator(file));
    }

    @Test
    void fileNotFoundException() {
        File file = new File("src/com/kyh/secondText");

        assertThrows(FileNotFoundException.class, () -> TollFeeCalculator.tollFeeCalculator(file));
    }

    @Test
    void getMaximalCost() {
        LocalDateTime[] date = new LocalDateTime[6];
        date[0] = LocalDateTime.parse("2020-06-30 06:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 8
        date[1] = LocalDateTime.parse("2020-06-30 07:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 18
        date[2] = LocalDateTime.parse("2020-06-30 08:20", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        date[3] = LocalDateTime.parse("2020-06-30 09:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 8
        date[4] = LocalDateTime.parse("2020-06-30 15:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        date[5] = LocalDateTime.parse("2020-06-30 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 18 totalFee = 78

        assertEquals(60, TollFeeCalculator.getTotalFeeCost(date));
    }

    @Test
    @DisplayName("Testing if the full array is reading (!.length-1)")
    void readFullArray() {
        LocalDateTime[] date = new LocalDateTime[6];
        date[0] = LocalDateTime.parse("2020-06-30 00:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[1] = LocalDateTime.parse("2020-06-30 00:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[2] = LocalDateTime.parse("2020-06-30 01:20", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[3] = LocalDateTime.parse("2020-06-30 02:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[4] = LocalDateTime.parse("2020-06-30 02:36", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[5] = LocalDateTime.parse("2020-06-30 06:35", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13

        assertEquals(13, TollFeeCalculator.getTotalFeeCost(date));
    }

    @Test
    void testCostOfSpecificPassingTimes() {
        LocalDateTime date = LocalDateTime.parse("2020-06-30 09:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        assertEquals(8, TollFeeCalculator.getTollFeePerPassing(date));

        LocalDateTime date1 = LocalDateTime.parse("2020-06-30 15:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        assertEquals(18, TollFeeCalculator.getTollFeePerPassing(date1));
    }

    @Test
    void testFirstPassing() {
        LocalDateTime[] date = new LocalDateTime[2];

        date[0] = LocalDateTime.parse("2020-06-30 07:13", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 18
        date[1] = LocalDateTime.parse("2020-06-30 08:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        assertEquals(31, TollFeeCalculator.getTotalFeeCost(date));
    }

    @Test
    void checkTotalCostUnderSixty() {
        LocalDateTime[] date = new LocalDateTime[8];

        date[0] = LocalDateTime.parse("2020-06-30 10:13", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //8
        date[1] = LocalDateTime.parse("2020-06-30 10:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // (8)
        date[2] = LocalDateTime.parse("2020-06-30 14:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 8 = 16
        date[3] = LocalDateTime.parse("2020-06-30 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13 = 21
        date[4] = LocalDateTime.parse("2020-06-30 15:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // (13)
        date[5] = LocalDateTime.parse("2020-06-30 15:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // (13)
        date[6] = LocalDateTime.parse("2020-06-30 16:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // + 18 - 13 = 26
        date[7] = LocalDateTime.parse("2020-06-30 17:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13 = 39
        assertEquals(39, TollFeeCalculator.getTotalFeeCost(date));
    }

    @Test
    void checkTotalCostAboveSixty() {
        LocalDateTime[] date = new LocalDateTime[8];

        date[0] = LocalDateTime.parse("2020-06-30 08:13", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[1] = LocalDateTime.parse("2020-06-30 09:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[2] = LocalDateTime.parse("2020-06-30 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[3] = LocalDateTime.parse("2020-06-30 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[4] = LocalDateTime.parse("2020-06-30 13:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[5] = LocalDateTime.parse("2020-06-30 14:22", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[6] = LocalDateTime.parse("2020-06-30 15:55", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[7] = LocalDateTime.parse("2020-06-30 17:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(60, TollFeeCalculator.getTotalFeeCost(date));
    }

    @Test
    void isTollFeeWeekend() {
        LocalDateTime date = LocalDateTime.parse("2020-06-13 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //lördag
        assertTrue(TollFeeCalculator.isTollFreeDate(date));

        LocalDateTime date2 = LocalDateTime.parse("2020-06-12 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //fredag
        assertFalse(TollFeeCalculator.isTollFreeDate(date2));
    }

    @Test
    void isTollFeeMonth() { //test july
        LocalDateTime date3 = LocalDateTime.parse("2020-07-14 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(TollFeeCalculator.isTollFreeDate(date3));

        LocalDateTime date2 = LocalDateTime.parse("2020-06-11 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertFalse(TollFeeCalculator.isTollFreeDate(date2));
    }
}
