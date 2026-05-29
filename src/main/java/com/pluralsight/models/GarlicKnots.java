package com.pluralsight.models;

/**
 * GarlicKnots.java - Represents a garlic knots side order.
 * Flat price of $1.50 -- no size or flavor options.
 * Simple class: just needs to provide getPrice() and toString()
 * so Order can treat it the same way it treats Pizza and Drink.
 * OOP CONCEPTS:
 *   - Encapsulation: even simple classes should be properly structured
 *   - Consistency: same getPrice() / toString() pattern as Drink and Pizza
 */

public class GarlicKnots {

    // static final = a constant -- same value for every GarlicKnots object
    private static final double PRICE = 1.50;

    /** Always returns $1.50 */
    public double getPrice() {
        return PRICE;
    }

    /**
     * Returns a formatted line for the order summary and receipt.
     * Example: "  Garlic Knots  -  $1.50"
     */

    @Override
    public String toString() {
        return String.format("  Garlic Knots  -  $%.2f%n", getPrice());
    }
}
