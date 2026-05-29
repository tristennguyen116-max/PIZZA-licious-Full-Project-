package com.pluralsight.models;

/**
 * Topping.java - Represents a single PREMIUM topping (meat or cheese).
 * WHY THIS CLASS EXISTS:
 *   Premium toppings need TWO pieces of information:
 *     1. What they are  (name  -- e.g. "pepperoni")
 *     2. Is it doubled? (extra -- true/false)
 *   A plain String can only hold one piece of information.
 *   So we use a Topping object wherever the "extra" flag matters.
 *   Regular toppings (veggies, sauces, sides) are always free with no
 *   extra option -- so Pizza stores those as plain List<String>.
 * OOP CONCEPTS:
 *   - Encapsulation: fields are private, accessed only via getters
 *   - Single Responsibility: this class does one thing -- hold topping data
 */

public class Topping {

    // --- Fields --------------------------------------------------------------

    private String name;    // e.g. "pepperoni", "mozzarella"
    private boolean extra;  // true = customer wants a double portion

    // --- Constructor ---------------------------------------------------------

    /**
     * @param name   the topping's display name
     * @param extra  true if the customer ordered extra (double portion)
     */

    public Topping(String name, boolean extra) {
        this.name  = name;
        this.extra = extra;
    }

    // --- Getters -------------------------------------------------------------

    /** Returns the topping name (e.g. "pepperoni") */
    public String getName()  { return name; }

    /**
     * Returns true if the customer ordered extra of this topping.
     * Pizza.getPrice() checks this to add the extra portion surcharge.
     */

    public boolean isExtra() { return extra; }

    // --- Display -------------------------------------------------------------

    /**
     * Used when printing the order summary and receipt.
     * Example outputs:
     *   regular -- "pepperoni"
     *   extra   -- "pepperoni (EXTRA)"
     */

    @Override
    public String toString() {
        return extra ? name + " (EXTRA)" : name;
    }
}
