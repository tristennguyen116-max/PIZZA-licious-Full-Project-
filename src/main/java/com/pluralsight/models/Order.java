package com.pluralsight.models;

import java.util.ArrayList;
import java.util.List;

/**
     * Order.java — Represents one customer's full order.
     * An Order is a "container" that holds:
     *   - A list of Pizza objects
     *   - A list of Drink objects
     *   - A list of GarlicKnots objects
     * Business Rule: An order must have at least ONE pizza, drink, or garlic knots.
     * If there are 0 pizzas, the customer MUST have at least a drink or garlic knots.
     * This is validated in the checkout screen before saving.
     * OOP Concepts:
     *   - Encapsulation: lists are private; items are added via methods
     *   - Composition: Order "has-a" list of Pizza, Drink, and GarlicKnots
     *   - Single Responsibility: Order manages item collections and totals only
     */

    public class Order {

        // ─── Fields ──────────────────────────────────────────────────────────────

        // Lists store items added during the ordering session.
        // We use ArrayList because the size is dynamic (unknown at start).
        private List<Pizza>       pizzas;
        private List<Drink>       drinks;
        private List<GarlicKnots> garlicKnots;

        // ─── Constructor ─────────────────────────────────────────────────────────

        /**
         * Creates a fresh, empty order.
         * Called each time the customer selects "New Order" from the Home Screen.
         */

        public Order() {
            pizzas      = new ArrayList<>();
            drinks      = new ArrayList<>();
            garlicKnots = new ArrayList<>();
        }

        // ─── Item Adders ─────────────────────────────────────────────────────────

        /** Adds a fully-customized pizza to the order */

        public void addPizza(Pizza pizza) {
            pizzas.add(pizza);
        }

        /** Adds a drink to the order */

        public void addDrink(Drink drink) {
            drinks.add(drink);
        }

        /** Adds garlic knots to the order */

        public void addGarlicKnots(GarlicKnots gk) {
            garlicKnots.add(gk);
        }

        // ─── Totals ──────────────────────────────────────────────────────────────

        /**
         * Calculates the grand total of the entire order.
         * HOW IT WORKS:
         *   1. Loop through every pizza and add its price
         *   2. Loop through every drink and add its price
         *   3. Loop through every garlic knots and add its price
         *   4. Return the sum
         * Each model class handles its own pricing (see Pizza.getPrice(), etc.)
         * — Order just asks each item "what do you cost?" and sums it up.
         */

        public double getTotal() {
            double total = 0.0;

            for (Pizza p : pizzas)       total += p.getPrice();
            for (Drink d : drinks)       total += d.getPrice();
            for (GarlicKnots g : garlicKnots) total += g.getPrice();

            return total;
        }

        // ─── Validation ──────────────────────────────────────────────────────────

        /**
         * Returns true if the order is valid and can be checked out.
         * RULE: If there are 0 pizzas, the customer must have at least
         * one drink OR garlic knots. An order with absolutely nothing is invalid.
         */

        public boolean isValid() {
            if (!pizzas.isEmpty()) return true;                    // has at least one pizza ✓
            return !drinks.isEmpty() || !garlicKnots.isEmpty();   // no pizza but has drink/knots
        }

        // ─── Getters ─────────────────────────────────────────────────────────────

        public List<Pizza> getPizzas()      { return pizzas; }
        public List<Drink>       getDrinks()      { return drinks; }
        public List<GarlicKnots> getGarlicKnots() { return garlicKnots; }

        /** Total number of line items in the order (used to check if order is empty) */

        public int getTotalItemCount() {
            return pizzas.size() + drinks.size() + garlicKnots.size();
        }

        // ─── Display ─────────────────────────────────────────────────────────────

        /**
         * Returns a formatted order summary string.
         * IMPORTANT: Items are displayed newest-first as required.
         * We iterate the lists in reverse order (from last index to 0).
         * This string is used BOTH for the checkout screen display
         * AND as the content of the receipt file.
         */

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("==============================\n");
            sb.append("       PIZZA-licious Order     \n");
            sb.append("==============================\n");

            // ── Pizzas (newest first) ──────────────────────────────────────────
            if (!pizzas.isEmpty()) {
                sb.append("\n--- PIZZAS ---\n");
                for (int i = pizzas.size() - 1; i >= 0; i--) {
                    sb.append(pizzas.get(i));
                }
            }

            // ── Drinks (newest first) ──────────────────────────────────────────
            if (!drinks.isEmpty()) {
                sb.append("\n--- DRINKS ---\n");
                for (int i = drinks.size() - 1; i >= 0; i--) {
                    sb.append(drinks.get(i));
                }
            }

            // ── Garlic Knots (newest first) ────────────────────────────────────
            if (!garlicKnots.isEmpty()) {
                sb.append("\n--- SIDES ---\n");
                for (int i = garlicKnots.size() - 1; i >= 0; i--) {
                    sb.append(garlicKnots.get(i));
                }
            }

            // ── Grand Total ───────────────────────────────────────────────────
            sb.append("\n==============================\n");
            sb.append(String.format("  TOTAL: $%.2f%n", getTotal()));
            sb.append("==============================\n");

            return sb.toString();
        }
    }
