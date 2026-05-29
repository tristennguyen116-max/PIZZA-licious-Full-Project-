package com.pluralsight.models;

/**
 * Drink.java - Represents a drink item on the order.
 * PRICING:
 *   Small  -- $2.00
 *   Medium -- $2.50
 *   Large  -- $3.00
 * OOP CONCEPTS:
 *   - Encapsulation: private fields with public getters
 *   - Abstraction: getPrice() returns the correct price without
 *     the caller needing to know the pricing table
 */

public class Drink {

    private String size;    // "Small", "Medium", or "Large"
    private String flavor;  // e.g. "Coke", "Sprite", "Water"

    /**
     * @param size    "Small", "Medium", or "Large"
     * @param flavor  the drink flavor chosen by the customer
     */

    public Drink(String size, String flavor) {
        this.size   = size;
        this.flavor = flavor;
    }

    /**
     * Returns price based on size.
     * Small=$2.00  |  Medium=$2.50  |  Large=$3.00
     */

    public double getPrice() {
        switch (size.toLowerCase()) {
            case "small":  return 2.00;
            case "medium": return 2.50;
            case "large":  return 3.00;
            default:       return 0.0;
        }
    }

    public String getSize()   { return size; }
    public String getFlavor() { return flavor; }

    /**
     * Returns a formatted line for the order summary and receipt.
     * Example: "  Drink  -  Small Coke  -  $2.00"
     */

    @Override
    public String toString() {
        return String.format("  Drink  -  %s %s  -  $%.2f%n", size, flavor, getPrice());
    }
}
