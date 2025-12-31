package com.mycompany.gymtracker.model;

/**
 * Interface: Billable
 * Requirement: Demonstrates Abstraction and provides business logic for financial operations.
 * Purpose: Defines the contract for all billable entities in the Gym Tracker system.
 */
public interface Billable {

    /**
     * Calculates the renewal fee based on specific membership criteria.
     * Implementation logic resides in classes like Member.
     * @return double - The total renewal fee.
     */
    double calculateRenewalFee();

    /**
     * Returns the description of the membership plan and its terms.
     * @return String - Membership terms.
     */
    String getMembershipTerms();

    /**
     * Logic: Shared validation to check if a provided payment covers the fee.
     * This is a default method to ensure full logical functionality within the interface.
     * @param amountPaid - The amount the member is paying.
     * @return boolean - true if the payment is equal to or greater than the fee.
     */
    default boolean verifyPayment(double amountPaid) {
        double requiredFee = calculateRenewalFee();
        if (amountPaid < requiredFee) {
            System.out.println("Payment Refused: Amount is less than the required fee of " + requiredFee);
            return false;
        }
        return true;
    }
}