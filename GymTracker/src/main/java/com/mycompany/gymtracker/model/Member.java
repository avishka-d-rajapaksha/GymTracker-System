package com.mycompany.gymtracker.model;

import java.time.LocalDate;

/**
 * Class: Member
 * Requirement: Demonstrates Inheritance, Abstraction, and Encapsulation.
 * Purpose: Represents a gym member with specific membership details and expiry logic.
 */
public class Member extends Person implements Billable {
    // Encapsulation: Private fields
    private String memberId;
    private String membershipType; // e.g., "Monthly", "Quarterly", "Annual"
    private LocalDate registrationDate;
    private LocalDate expiryDate;

    public Member(String memberId, String name, String membershipType, LocalDate registrationDate) {
        super(name); // Inheritance: Calling the constructor of Person
        this.memberId = memberId;
        this.membershipType = membershipType;
        this.registrationDate = registrationDate;
        this.expiryDate = calculateExpiryDate(registrationDate, membershipType);
    }

    /**
     * Logic to calculate expiry based on the membership type.
     */
    private LocalDate calculateExpiryDate(LocalDate start, String type) {
        return switch (type) {
            case "Monthly" -> start.plusMonths(1);
            case "Quarterly" -> start.plusMonths(3);
            case "Annual" -> start.plusYears(1);
            default -> start.plusMonths(1);
        };
    }

    // --- Implementation of Billable Interface (Polymorphism) ---

    @Override
    public double calculateRenewalFee() {
        return switch (membershipType) {
            case "Monthly" -> 3000.00;
            case "Quarterly" -> 8000.00;
            case "Annual" -> 30000.00;
            default -> 0.00;
        };
    }

    @Override
    public String getMembershipTerms() {
        return "Access to all gym equipment for the duration of the " + membershipType + " plan.";
    }

    // --- Encapsulation: Getters and Setters ---

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Checks if the membership has expired relative to the current date.
     * @return boolean - true if expired.
     */
    public boolean isExpired() {
        return LocalDate.now().isAfter(this.expiryDate);
    }
}