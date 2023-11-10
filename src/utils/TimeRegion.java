package utils;

import java.time.LocalDate;

public class TimeRegion {
    private LocalDate start;
    private LocalDate end;
    public TimeRegion(LocalDate start, LocalDate end) {
        this.start = start; this.end = end;
    }
    public void adjustStart(LocalDate start) {
        this.start = start;
    }
    public void adjustEnd(LocalDate end) {
        this.end = end;
    }
    public boolean conflictsWith(TimeRegion region) {
        return start.isBefore(region.end) && end.isAfter(region.start);
    }
}
