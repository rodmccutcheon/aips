package com.rodmccutcheon;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class Main {

    public static final String DELIMITER = " ";

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Hello world!");

        try (Stream<String> stream = Files.lines(Path.of(ClassLoader.getSystemResource("sample.txt").toURI()))) {
            stream.map(line -> {
                String[] columns = line.split(DELIMITER);
                return new TrafficReading(LocalDateTime.parse(columns[0]), Long.parseLong(columns[1]));
            }).forEach(System.out::println);
        }
    }
}

record TrafficReading(LocalDateTime timestamp, long carCount) { }