package com.pluralsight.models;

    /**
     * MargheritaPizza.java - A pre-configured signature pizza.
     * INHERITANCE: This class extends Pizza, meaning it IS-A Pizza.
     * It inherits ALL fields and methods from Pizza (size, crust, meats,
     * cheeses, getPrice(), addMeat(), etc.) and just pre-loads classic toppings
     * in its constructor by calling the parent's add methods.
     * The customer can still customize after creation -- adding extra toppings,
     * choosing stuffed crust, etc. -- because all of Pizza's methods are inherited.
     * Java Rule: A public class MUST be in its own file named exactly the same.
     *   public class MargheritaPizza must live in MargheritaPizza.java
     */

    public class MargheritaPizza extends Pizza {

        /**
         * Constructor pre-loads the classic Margherita toppings.
         * super(12, "regular") calls Pizza's constructor first,
         * which initializes all the lists and sets size=12, crustType="regular".
         * Then we add the default toppings on top of that.
         */

        public MargheritaPizza() {
            super(12, "regular");
            addCheese("Mozzarella", false);
            addRegularTopping("Tomatoes");
            addRegularTopping("Basil");
            addSauce("Marinara");
            addSauce("Olive Oil");
        }
    }

