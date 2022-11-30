package com.rodmccutcheon.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TrafficReading(LocalDateTime timestamp, long carCount) {
    public LocalDate getLocalDate() {
        return timestamp.toLocalDate();
    }

    @Override
    public String toString() {
        return timestamp + " " + carCount;
    }
}
