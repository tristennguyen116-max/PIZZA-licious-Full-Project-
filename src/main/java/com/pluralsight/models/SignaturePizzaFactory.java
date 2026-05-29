package com.pluralsight.models;

    /**
     * SignaturePizzaFactory.java - Creates signature pizza instances by menu number.
     * WHY A FACTORY CLASS?
     *   UserInterface needs to create a signature pizza based on what number
     *   the customer types (1 or 2). Without a factory, the UI would need a
     *   big if/else block that imports and directly references MargheritaPizza
     *   and VeggiePizza. Instead, it just calls:
     *       Pizza pizza = SignaturePizzaFactory.create(choice);
     *   The factory handles which subclass to build. This is the
     *   "Factory Pattern" -- a simple, beginner-friendly design pattern.
     * WHY STATIC METHODS?
     *   There is no reason to instantiate a SignaturePizzaFactory object --
     *   it has no state of its own. Static methods let you call it directly:
     *   SignaturePizzaFactory.create(1) without doing "new SignaturePizzaFactory()".
     * Java Rule: public class must be in its own file named SignaturePizzaFactory.java
     */

    public class SignaturePizzaFactory {

        /**
         * Returns a new pre-configured signature Pizza based on the menu choice.
         * Returns a Pizza (the parent type) even though it actually creates
         * a MargheritaPizza or VeggiePizza -- this is POLYMORPHISM.
         * The caller only needs to know it got a Pizza back.
         * @param choice  1 = Margherita, 2 = Veggie
         * @return        a ready-to-use Pizza object, or null if choice is invalid
         */

        public static Pizza create(int choice) {
            switch (choice) {
                case 1: return new MargheritaPizza();
                case 2: return new VeggiePizza();
                default: return null;
            }
        }
    }

