package com.mycompany.gymtracker.model;

import java.io.Serializable;

/**
 * Abstract Class: Person
 * Requirement: Demonstrates Abstraction, Inheritance (Base Class), and Encapsulation.
 * Purpose: Provides a foundation for all human-related entities in the Gym Tracker.
 */
public abstract class Person implements Serializable {
    // Encapsulation: Private field to protect data
    private String name;

    /**
     * Constructor for Person
     * @param name - The full name of the individual
     */
    public Person(String name) {
        this.name = name;
    }

    // --- Encapsulation: Getter and Setter ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}