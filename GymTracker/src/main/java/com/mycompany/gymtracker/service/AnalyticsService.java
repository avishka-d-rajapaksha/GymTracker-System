package com.mycompany.gymtracker.service;

import com.mycompany.gymtracker.model.AttendanceRecord;
import com.mycompany.gymtracker.model.Member;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AnalyticsService {
    
    private AttendanceService attendanceService = new AttendanceService();
    private FileHandler fileHandler = new FileHandler();

    public int getAttendanceCountForPeriod(int daysBack) {
        List<AttendanceRecord> allLogs = attendanceService.getAllRecords();
        LocalDate threshold = LocalDate.now().minusDays(daysBack);
        return (int) allLogs.stream()
                .filter(record -> !record.getTimestamp().toLocalDate().isBefore(threshold))
                .count();
    }

    public List<Member> getExpiredMembers() {
        return fileHandler.getAllMembers().stream()
                .filter(Member::isExpired)
                .collect(Collectors.toList());
    }

    public List<Member> getMembersDueForRenewal() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        return fileHandler.getAllMembers().stream()
                .filter(m -> !m.isExpired() && m.getExpiryDate().isBefore(nextWeek))
                .collect(Collectors.toList());
    }
}