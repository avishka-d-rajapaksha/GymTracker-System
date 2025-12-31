package com.mycompany.gymtracker.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class: AttendanceRecord
 * Requirement: Implements Serializable for binary file storage (attendance.bin).
 * Purpose: Captures full logic for a single attendance entry.
 */
public class AttendanceRecord implements Serializable {
    // Ensures compatibility during serialization
    private static final long serialVersionUID = 1L;

    private String memberId;
    private LocalDateTime timestamp;

    /**
     * Constructor creates a record for the current date and time.
     * @param memberId - The ID of the member marking attendance.
     */
    public AttendanceRecord(String memberId) {
        this.memberId = memberId;
        this.timestamp = LocalDateTime.now();
    }

    // --- Encapsulation: Getters ---

    public String getMemberId() {
        return memberId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Logic: Formats the timestamp into a human-readable string.
     * Useful for displaying in reports or UI labels.
     * @return String - Formatted date and time.
     */
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    /**
     * Logic: Determines if this record was created on a specific date.
     * Used by AnalyticsService to filter daily/weekly logs.
     * @param date - The date to check against.
     * @return boolean - true if the dates match.
     */
    public boolean isLoggedOn(java.time.LocalDate date) {
        return this.timestamp.toLocalDate().equals(date);
    }

    @Override
    public String toString() {
        return "Attendance[MemberID=" + memberId + ", Time=" + getFormattedTimestamp() + "]";
    }
}