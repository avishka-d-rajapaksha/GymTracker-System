package com.mycompany.gymtracker.service;

import com.mycompany.gymtracker.model.AttendanceRecord;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceService {
    private static final String BINARY_FILE = "data/attendance.bin";

    public void logAttendance(String memberId) {
        List<AttendanceRecord> history = getAllRecords();
        history.add(new AttendanceRecord(memberId));
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BINARY_FILE))) {
            oos.writeObject(history);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<AttendanceRecord> getAllRecords() {
        File file = new File(BINARY_FILE);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<AttendanceRecord>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<AttendanceRecord> getTodaysLogs() {
        List<AttendanceRecord> all = getAllRecords();
        List<AttendanceRecord> today = new ArrayList<>();
        java.time.LocalDate now = java.time.LocalDate.now();

        for (AttendanceRecord record : all) {
            if (record.isLoggedOn(now)) {
                today.add(record);
            }
        }
        return today;
    }
}