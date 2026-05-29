package com.pluralsight.models;

    /**
     * VeggiePizza.java - A pre-configured vegetarian signature pizza.
     * INHERITANCE: Extends Pizza exactly like MargheritaPizza does.
     * Calls super(8, "regular") to set size=8 and crust="regular",
     * then loads its own default veggie toppings.
     * Java Rule: Each public class gets its own file.
     *   public class VeggiePizza must live in VeggiePizza.java
     */

    public class VeggiePizza extends Pizza {

        /**
         * Constructor pre-loads veggie toppings.
         * super(8, "regular") initializes the Pizza base -- size 8", regular crust.
         */

        public VeggiePizza() {
            super(8, "regular");
            addCheese("Mozzarella", false);
            addRegularTopping("Bell Peppers");
            addRegularTopping("Spinach");
            addRegularTopping("Olives");
            addRegularTopping("Onions");
            addSauce("Marinara");
        }
    }

