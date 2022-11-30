package com.rodmccutcheon.util;

import com.rodmccutcheon.model.TrafficReading;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

public class TrafficReadingUtil {

    private TrafficReadingUtil() {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static long countTotalCars(Stream<TrafficReading> trafficReadings) {
        return trafficReadings.map(TrafficReading::carCount).reduce(0L, Long::sum);
    }

    public static Map<LocalDate, Long> countDailyTotalOfCars(Stream<TrafficReading> trafficReadings) {
        return trafficReadings.collect(groupingBy(TrafficReading::getLocalDate, summingLong(TrafficReading::carCount)));
    }

    public static List<TrafficReading> selectPeriodsWithMostCars(Stream<TrafficReading> trafficReadings, long count) {
        return trafficReadings.sorted(comparingLong(TrafficReading::carCount).reversed()).limit(count).toList();
    }

    public static List<TrafficReading> selectWindowWithLeastCars(List<TrafficReading> trafficReadings, int windowSize) {
        return slidingWindow(trafficReadings, windowSize)
                .min(Comparator.comparingLong(TrafficReadingUtil::sumWindow))
                .orElse(Collections.emptyList());
    }

    private static long sumWindow(List<TrafficReading> trafficReadings) {
        return trafficReadings.stream().map(TrafficReading::carCount).reduce(0L, Long::sum);
    }

    private static <T> Stream<List<T>> slidingWindow(List<T> list, int size) {
        if (size > list.size()) {
            return Stream.empty();
        } else {
            return IntStream.range(0, list.size() - size + 1)
                    .mapToObj(start -> list.subList(start, start + size));
        }
    }
}
