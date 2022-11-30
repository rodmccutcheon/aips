package com.rodmccutcheon;

import com.rodmccutcheon.model.TrafficReading;
import com.rodmccutcheon.util.TrafficReadingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final String DELIMITER = " ";
    public static final String SAMPLE_FILE = "sample.txt";

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<TrafficReading> trafficReadings = readTrafficReadings(SAMPLE_FILE);

        logger.info("======= AIPS =======");
        logger.info("Total number of cars seen: {}", TrafficReadingUtil.countTotalCars(trafficReadings.stream()));
        logger.info("Daily total of cars seen: ");
        TrafficReadingUtil.countDailyTotalOfCars(trafficReadings.stream())
                .forEach((date, carCount) -> logger.info("{} {}", date.format(dateTimeFormatter), carCount));
        logger.info("Top 3 half hours with most cars: ");
        TrafficReadingUtil.selectPeriodsWithMostCars(trafficReadings.stream(), 3)
                .forEach(tr -> logger.info(tr.toString()));
        logger.info("90 minute contiguous period with least cars: ");
        TrafficReadingUtil.selectWindowWithLeastCars(trafficReadings, 3)
                .forEach(tr -> logger.info(tr.toString()));
        logger.info("====================");
    }

    private static List<TrafficReading> readTrafficReadings(final String filename) throws IOException, URISyntaxException {
        List<TrafficReading> trafficReadings;
        try (Stream<String> stream = Files.lines(Path.of(ClassLoader.getSystemResource(filename).toURI()))) {
            trafficReadings = stream.map(line -> {
                String[] columns = line.split(DELIMITER);
                return new TrafficReading(LocalDateTime.parse(columns[0]), Long.parseLong(columns[1]));
            }).toList();
        }
        return trafficReadings;
    }

}

