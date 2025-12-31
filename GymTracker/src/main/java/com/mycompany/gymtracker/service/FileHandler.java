package com.mycompany.gymtracker.service;

import com.mycompany.gymtracker.model.Member;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String MEMBER_FILE = "data/members.txt";

    public void saveMember(Member member) {
        try (PrintWriter out = new PrintWriter(new FileWriter(MEMBER_FILE, true))) {
            out.println(member.getMemberId() + "," + member.getName() + "," + member.getMembershipType() + "," + member.getRegistrationDate() + "," + member.getExpiryDate());
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        File file = new File(MEMBER_FILE);
        if (!file.exists()) return members;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 5) {
                    members.add(new Member(p[0], p[1], p[2], LocalDate.parse(p[3])));
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return members;
    }
}