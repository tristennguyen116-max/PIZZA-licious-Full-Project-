package com.pluralsight.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Pizza.java - The core product of PIZZA-licious.
 * Stores everything about one pizza:
 *   - size (8, 12, or 16 inches)
 *   - crustType ("thin", "regular", "thick", "cauliflower")
 *   - stuffedCrust (boolean -- adds extra cost)
 *   - meats           -- List<Topping>  (premium -- costs money, can be extra)
 *   - cheeses         -- List<Topping>  (premium -- costs money, can be extra)
 *   - regularToppings -- List<String>   (free vegetables)
 *   - sauces          -- List<String>   (free)
 *   - sides           -- List<String>   (free condiments: red pepper, parmesan)
 * OOP CONCEPTS:
 *   - Encapsulation: all fields private, exposed via getters/setters
 *   - Abstraction: getPrice() hides all the pricing math from callers
 *   - Inheritance: MargheritaPizza and VeggiePizza extend this class
 */

public class Pizza {

    // --- Fields --------------------------------------------------------------

    private int     size;         // 8, 12, or 16
    private String  crustType;    // thin | regular | thick | cauliflower
    private boolean stuffedCrust; // false by default

    private List<Topping> meats;
    private List<Topping> cheeses;
    private List<String>  regularToppings;
    private List<String>  sauces;
    private List<String>  sides;

    // --- Constructor ---------------------------------------------------------

    /**
     * Creates a new empty pizza with the given size and crust.
     * All topping lists start empty -- items are added via the add methods.
     *
     * @param size      8, 12, or 16
     * @param crustType "thin", "regular", "thick", or "cauliflower"
     */

    public Pizza(int size, String crustType) {
        this.size         = size;
        this.crustType    = crustType;
        this.stuffedCrust = false;

        this.meats            = new ArrayList<>();
        this.cheeses          = new ArrayList<>();
        this.regularToppings  = new ArrayList<>();
        this.sauces           = new ArrayList<>();
        this.sides            = new ArrayList<>();
    }

    // --- Pricing -------------------------------------------------------------

    /**
     * Calculates the total price of this pizza.
     * PRICING TABLE:
     *   Base (any crust):     8"=$8.50   12"=$12.00  16"=$16.50
     *   Each meat:            8"=$1.00   12"=$2.00   16"=$3.00
     *   Extra meat surcharge: 8"=$0.50   12"=$1.00   16"=$1.50
     *   Each cheese:          8"=$0.75   12"=$1.50   16"=$2.25
     *   Extra cheese:         8"=$0.30   12"=$0.60   16"=$0.90
     *   Stuffed crust:        same price as one cheese for that size
     *   Regular toppings, sauces, sides: FREE
     *
     * @return total price as a double
     */

    public double getPrice() {
        double total = getBasePrice();

        // Each meat: base cost + extra surcharge if doubled
        for (Topping meat : meats) {
            total += getMeatPrice();
            if (meat.isExtra()) {
                total += getExtraMeatPrice();
            }
        }

        // Each cheese: base cost + extra surcharge if doubled
        for (Topping cheese : cheeses) {
            total += getCheesePrice();
            if (cheese.isExtra()) {
                total += getExtraCheesePrice();
            }
        }

        // Stuffed crust costs the same as one cheese for this size
        if (stuffedCrust) {
            total += getCheesePrice();
        }

        return total;
    }

    /** Base pizza price by size -- all crust types cost the same */
    private double getBasePrice() {
        switch (size) {
            case 8:  return 8.50;
            case 12: return 12.00;
            case 16: return 16.50;
            default: return 0.0;
        }
    }

    /** Cost per meat topping by size */
    private double getMeatPrice() {
        switch (size) {
            case 8:  return 1.00;
            case 12: return 2.00;
            case 16: return 3.00;
            default: return 0.0;
        }
    }

    /** Extra meat surcharge (double portion) by size */
    private double getExtraMeatPrice() {
        switch (size) {
            case 8:  return 0.50;
            case 12: return 1.00;
            case 16: return 1.50;
            default: return 0.0;
        }
    }

    /** Cost per cheese topping by size */
    private double getCheesePrice() {
        switch (size) {
            case 8:  return 0.75;
            case 12: return 1.50;
            case 16: return 2.25;
            default: return 0.0;
        }
    }

    /** Extra cheese surcharge (double portion) by size */
    private double getExtraCheesePrice() {
        switch (size) {
            case 8:  return 0.30;
            case 12: return 0.60;
            case 16: return 0.90;
            default: return 0.0;
        }
    }

    // --- Topping Adders ------------------------------------------------------

    /**
     * Adds a premium meat topping.
     * @param name   topping name (e.g. "pepperoni")
     * @param extra  true = double portion (adds extra surcharge)
     */

    public void addMeat(String name, boolean extra) {
        meats.add(new Topping(name, extra));
    }

    /**
     * Adds a premium cheese topping.
     * @param name   cheese name (e.g. "mozzarella")
     * @param extra  true = double portion (adds extra surcharge)
     */

    public void addCheese(String name, boolean extra) {
        cheeses.add(new Topping(name, extra));
    }

    /** Adds a free regular vegetable topping (e.g. "onions") */
    public void addRegularTopping(String name) {
        regularToppings.add(name);
    }

    /** Adds a free sauce (e.g. "marinara") */
    public void addSauce(String name) {
        sauces.add(name);
    }

    /** Adds a free condiment/side (e.g. "red pepper", "parmesan") */
    public void addSide(String name) {
        sides.add(name);
    }

    // --- Getters / Setters ---------------------------------------------------

    public int     getSize()              { return size; }
    public String  getCrustType()         { return crustType; }
    public boolean isStuffedCrust()       { return stuffedCrust; }
    public List<Topping> getMeats()       { return meats; }
    public List<Topping> getCheeses()     { return cheeses; }
    public List<String>  getRegularToppings() { return regularToppings; }
    public List<String>  getSauces()      { return sauces; }
    public List<String>  getSides()       { return sides; }

    public void setStuffedCrust(boolean stuffedCrust) {
        this.stuffedCrust = stuffedCrust;
    }

    // --- Display -------------------------------------------------------------

    /**
     * Returns a formatted summary used in the checkout screen and receipt file.
     * Example output:
     *   Pizza (12" regular crust) [STUFFED CRUST]  -  $16.50
     *     Meats:    pepperoni (EXTRA)  sausage
     *     Cheese:   mozzarella
     *     Toppings: onions, mushrooms
     *     Sauces:   marinara
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // First line: size, crust, stuffed crust flag, and price
        sb.append(String.format("  Pizza (%d\" %s crust)%s  -  $%.2f%n",
                size,
                crustType,
                stuffedCrust ? " [STUFFED CRUST]" : "",
                getPrice()));

        // Only print a section if the list is not empty
        if (!meats.isEmpty()) {
            sb.append("    Meats:    ");
            for (Topping t : meats) sb.append(t).append("  ");
            sb.append("\n");
        }
        if (!cheeses.isEmpty()) {
            sb.append("    Cheese:   ");
            for (Topping t : cheeses) sb.append(t).append("  ");
            sb.append("\n");
        }
        if (!regularToppings.isEmpty()) {
            sb.append("    Toppings: ").append(String.join(", ", regularToppings)).append("\n");
        }
        if (!sauces.isEmpty()) {
            sb.append("    Sauces:   ").append(String.join(", ", sauces)).append("\n");
        }
        if (!sides.isEmpty()) {
            sb.append("    Sides:    ").append(String.join(", ", sides)).append("\n");
        }
        return sb.toString();
    }
}

