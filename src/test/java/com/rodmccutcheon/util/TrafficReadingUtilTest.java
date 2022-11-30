package com.rodmccutcheon.util;

import com.rodmccutcheon.model.TrafficReading;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrafficReadingUtilTest {

    private static final List<TrafficReading> trafficReadings = List.of(
            new TrafficReading(LocalDateTime.of(2022, 11, 30, 21, 30, 0), 100L),
            new TrafficReading(LocalDateTime.of(2022, 11, 30, 22, 0, 0), 50L),
            new TrafficReading(LocalDateTime.of(2022, 12, 1, 21, 30, 0), 7L),
            new TrafficReading(LocalDateTime.of(2022, 12, 1, 22, 0, 0), 11L),
            new TrafficReading(LocalDateTime.of(2022, 12, 2, 22, 0, 0), 0L)
    );

    @Test
    void testCountTotalCarsNoReadings() {
        assertEquals(0L, TrafficReadingUtil.countTotalCars(Stream.empty()));
    }

    @Test
    void testCountTotalCars() {
        assertEquals(168L, TrafficReadingUtil.countTotalCars(trafficReadings.stream()));
    }

    @Test
    void testCountDailyTotalOfCarsNoReadings() {
        Map<LocalDate, Long> expectedDailyTotals = new HashMap<>();

        assertEquals(expectedDailyTotals, TrafficReadingUtil.countDailyTotalOfCars(Stream.empty()));
    }

    @Test
    void testCountDailyTotalOfCars() {
        Map<LocalDate, Long> expectedDailyTotals = new HashMap<>();
        expectedDailyTotals.put(LocalDate.of(2022, 11, 30), 150L);
        expectedDailyTotals.put(LocalDate.of(2022, 12, 1), 18L);
        expectedDailyTotals.put(LocalDate.of(2022, 12, 2), 0L);

        assertEquals(expectedDailyTotals, TrafficReadingUtil.countDailyTotalOfCars(trafficReadings.stream()));
    }

    @Test
    void testSelectPeriodsWithMostCarsNoReadings() {
        assertEquals(Collections.emptyList(), TrafficReadingUtil.selectPeriodsWithMostCars(Stream.empty(), 3));
    }

    @Test
    void testSelectPeriodsWithMostCars() {
        List<TrafficReading> expectedReadings = List.of(
                new TrafficReading(LocalDateTime.of(2022, 11, 30, 21, 30, 0), 100L),
                new TrafficReading(LocalDateTime.of(2022, 11, 30, 22, 0, 0), 50L),
                new TrafficReading(LocalDateTime.of(2022, 12, 1, 22, 0, 0), 11L)
        );

        assertEquals(expectedReadings, TrafficReadingUtil.selectPeriodsWithMostCars(trafficReadings.stream(), 3));
    }

    @Test
    void testSelectWindowWithLeastCarsNoReadings() {
        assertEquals(Collections.emptyList(), TrafficReadingUtil.selectWindowWithLeastCars(Collections.emptyList(), 3));
    }

    @Test
    void testSelectWindowWithLeastCars() {
        List<TrafficReading> expectedReadings = List.of(
                new TrafficReading(LocalDateTime.of(2022, 12, 1, 21, 30, 0), 7L),
                new TrafficReading(LocalDateTime.of(2022, 12, 1, 22, 0, 0), 11L),
                new TrafficReading(LocalDateTime.of(2022, 12, 2, 22, 0, 0), 0L)
        );

        assertEquals(expectedReadings, TrafficReadingUtil.selectWindowWithLeastCars(trafficReadings, 3));
    }
}
