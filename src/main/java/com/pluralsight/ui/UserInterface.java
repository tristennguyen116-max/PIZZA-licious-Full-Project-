package com.pluralsight.ui;

import com.pluralsight.models.Drink;
import com.pluralsight.models.GarlicKnots;
import com.pluralsight.models.Order;
import com.pluralsight.models.Pizza;
import com.pluralsight.models.SignaturePizzaFactory;
import com.pluralsight.util.ReceiptWriter;
import java.util.Scanner;

    /**
     * UserInterface.java -- All console screens and user input handling.
     * This class is the "controller" of the application:
     *   - Displays menus to the user
     *   - Reads input with Scanner
     *   - Calls model methods to build the order
     *   - Calls ReceiptWriter when the order is confirmed
     * APPLICATION FLOW:
     *   start()
     *     -> displayHomeScreen()           (loops until "Exit")
     *           -> displayOrderScreen()    (loops for one order)
     *                 -> displayAddPizzaScreen()
     *                 -> displayAddDrinkScreen()
     *                 -> displayAddGarlicKnotsScreen()
     *                 -> displayCheckoutScreen()
     * OOP CONCEPTS:
     *   - Encapsulation: scanner and currentOrder are private fields
     *   - Separation of Concerns: UI handles I/O only; models handle data/pricing
     */

    public class UserInterface {

        // --- Fields --------------------------------------------------------------

        // ONE Scanner for the whole app -- never open multiple scanners on System.in
        private Scanner scanner;

        // The active order. null when no order is in progress.
        private Order currentOrder;

        // --- Constructor ---------------------------------------------------------

        public UserInterface() {
            scanner = new Scanner(System.in);
        }

        // --- Entry Point ---------------------------------------------------------

        /**
         * Starts the application. Called from Main.java.
         * Hands control to the Home Screen loop, then cleans up on exit.
         */

        public void start() {
            displayHomeScreen();
            scanner.close();
        }

        // =========================================================================
        //  HOME SCREEN
        // =========================================================================

        /**
         * Loops until the user chooses 0 to exit.
         * Selecting "New Order" creates a fresh Order and opens the Order Screen.
         */

        private void displayHomeScreen() {
            while (true) {
                printBanner();
                System.out.println("  1) New Order");
                System.out.println("  0) Exit");
                System.out.println();
                System.out.print("  Enter your choice: ");

                int choice = getIntInput();

                switch (choice) {
                    case 1:
                        currentOrder = new Order();
                        displayOrderScreen();
                        break;
                    case 0:
                        System.out.println();
                        System.out.println("  Thanks for visiting PIZZA-licious! Goodbye!");
                        System.out.println();
                        return;
                    default:
                        System.out.println("  Invalid choice. Please enter 1 or 0.");
                        System.out.println();
                }
            }
        }

        // =========================================================================
        //  ORDER SCREEN
        // =========================================================================

        /**
         * Main order loop. Shows the current order contents (newest first)
         * and lets the customer add items, checkout, or cancel.
         */

        private void displayOrderScreen() {
            while (true) {
                printDivider();
                System.out.println("  YOUR ORDER");
                printDivider();

                // Show order contents, or a placeholder if nothing added yet
                if (currentOrder.getTotalItemCount() == 0) {
                    System.out.println("  (No items yet)");
                    System.out.println();
                } else {
                    System.out.println(currentOrder);
                }

                printDivider();
                System.out.println("  1) Add Pizza");
                System.out.println("  2) Add Drink");
                System.out.println("  3) Add Garlic Knots  ($1.50)");
                System.out.println("  4) Checkout");
                System.out.println("  0) Cancel Order");
                System.out.println();
                System.out.print("  Enter your choice: ");

                int choice = getIntInput();

                switch (choice) {
                    case 1: displayAddPizzaScreen();       break;
                    case 2: displayAddDrinkScreen();       break;
                    case 3: displayAddGarlicKnotsScreen(); break;
                    case 4:
                        displayCheckoutScreen();
                        return;  // done -- back to home screen
                    case 0:
                        System.out.println("  Order cancelled.");
                        System.out.println();
                        currentOrder = null;
                        return;
                    default:
                        System.out.println("  Invalid choice, please try again.");
                        System.out.println();
                }
            }
        }

        // =========================================================================
        //  ADD PIZZA SCREEN
        // =========================================================================

        /**
         * Walks the customer step-by-step through building a pizza.
         * Steps:
         *   0. Custom or Signature pizza?
         *   1. Crust type  (if custom)
         *   2. Size        (if custom)
         *   3. Meats       (optional premium -- adds to price)
         *   4. Cheeses     (optional premium -- adds to price)
         *   5. Regular toppings (optional -- free)
         *   6. Sauces           (optional -- free)
         *   7. Sides            (optional -- free)
         *   8. Stuffed crust?   (optional -- adds cheese price)
         */

        private void displayAddPizzaScreen() {
            printDivider();
            System.out.println("  BUILD YOUR PIZZA");
            printDivider();

            // Step 0: Custom or Signature?
            System.out.println("  Start from:");
            System.out.println("  1) Build from scratch");
            System.out.println("  2) Margherita - 12\" Regular, Mozzarella, Tomatoes, Basil");
            System.out.println("  3) Veggie     - 8\"  Regular, Mozzarella, Bell Peppers, Spinach, Olives, Onions");
            System.out.print("  Choice: ");
            int startChoice = getIntInput();

            Pizza pizza;

            if (startChoice == 2 || startChoice == 3) {
                // SignaturePizzaFactory.create() maps 1=Margherita, 2=Veggie
                // We subtract 1 because the menu shows 2 and 3 but factory uses 1 and 2
                pizza = SignaturePizzaFactory.create(startChoice - 1);
                if (pizza == null) {
                    System.out.println("  Invalid choice, building from scratch instead.");
                    System.out.println();
                    pizza = buildCustomPizza();
                } else {
                    System.out.println("  Signature pizza loaded! You can still add more toppings.");
                    System.out.println();
                }
            } else {
                pizza = buildCustomPizza();
            }

            // Step 3: Meats
            System.out.println("  MEATS  (premium - adds to price)");
            System.out.println("  Options: pepperoni, sausage, ham, bacon, chicken, meatball");
            addPremiumToppings(pizza, "meat");

            // Step 4: Cheeses
            System.out.println("  CHEESE  (premium - adds to price)");
            System.out.println("  Options: mozzarella, parmesan, ricotta, goat cheese, buffalo");
            addPremiumToppings(pizza, "cheese");

            // Step 5: Regular Toppings
            System.out.println("  REGULAR TOPPINGS  (free)");
            System.out.println("  Options: onions, mushrooms, bell peppers, olives, tomatoes,");
            System.out.println("           spinach, basil, pineapple, anchovies");
            addFreeToppings(pizza, "regular topping");

            // Step 6: Sauces
            System.out.println("  SAUCES  (free)");
            System.out.println("  Options: marinara, alfredo, pesto, bbq, buffalo, olive oil");
            addFreeToppings(pizza, "sauce");

            // Step 7: Sides / Condiments
            System.out.println("  SIDES / CONDIMENTS  (free)");
            System.out.println("  Options: red pepper, parmesan");
            addFreeToppings(pizza, "side");

            // Step 8: Stuffed Crust
            System.out.println("  Would you like stuffed crust?");
            System.out.println("  (Adds the cheese price for your size)");
            System.out.println("  1) Yes   2) No");
            System.out.print("  Choice: ");
            if (getIntInput() == 1) {
                pizza.setStuffedCrust(true);
            }

            // Add the finished pizza to the order
            currentOrder.addPizza(pizza);
            System.out.println();
            System.out.printf("  Pizza added!  Subtotal: $%.2f%n", pizza.getPrice());
            System.out.println();
        }

        /**
         * Helper: prompts the user to choose crust and size, returns a new Pizza.
         * Extracted into its own method so both the scratch path and the
         * fallback path in displayAddPizzaScreen() can reuse it.
         */

        private Pizza buildCustomPizza() {
            // Crust type
            System.out.println("  Select crust type:");
            System.out.println("  1) Thin");
            System.out.println("  2) Regular");
            System.out.println("  3) Thick");
            System.out.println("  4) Cauliflower");
            System.out.print("  Choice: ");
            String crust = getCrustName(getIntInput());

            // Size
            System.out.println();
            System.out.println("  Select size:");
            System.out.println("  1)  8\"  Personal  - $8.50");
            System.out.println("  2) 12\"  Medium    - $12.00");
            System.out.println("  3) 16\"  Large     - $16.50");
            System.out.print("  Choice: ");
            int size = getSizeInches(getIntInput());
            System.out.println();

            return new Pizza(size, crust);
        }

        /**
         * Helper: repeatedly prompts for premium toppings (meats or cheeses)
         * until the user presses Enter with no text.
         * For each topping, also asks whether they want extra (double portion).
         * @param pizza       the pizza being built
         * @param toppingType "meat" or "cheese" -- controls which Pizza method is called
         */

        private void addPremiumToppings(Pizza pizza, String toppingType) {
            while (true) {
                System.out.print("  Add a " + toppingType + "? (type name or press Enter to skip): ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println();
                    break;  // Enter with no text -- done
                }

                System.out.print("  Extra " + toppingType + "? 1=Yes  2=No: ");
                boolean extra = (getIntInput() == 1);

                if (toppingType.equals("meat")) {
                    pizza.addMeat(input, extra);
                } else {
                    pizza.addCheese(input, extra);
                }
                System.out.println("  Added: " + input + (extra ? " (EXTRA)" : ""));
            }
        }

        /**
         * Helper: repeatedly prompts for free toppings (regular, sauce, or side)
         * until the user presses Enter with no text.
         * No "extra" option because free toppings have no extra surcharge.
         *
         * @param pizza       the pizza being built
         * @param toppingType "regular topping", "sauce", or "side"
         */

        private void addFreeToppings(Pizza pizza, String toppingType) {
            while (true) {
                System.out.print("  Add a " + toppingType + "? (type name or press Enter to skip): ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println();
                    break;
                }

                switch (toppingType) {
                    case "regular topping": pizza.addRegularTopping(input); break;
                    case "sauce":           pizza.addSauce(input);          break;
                    case "side":            pizza.addSide(input);           break;
                }
                System.out.println("  Added: " + input);
            }
        }

        // =========================================================================
        //  ADD DRINK SCREEN
        // =========================================================================

        private void displayAddDrinkScreen() {
            printDivider();
            System.out.println("  ADD A DRINK");
            printDivider();

            System.out.println("  Select size:");
            System.out.println("  1) Small  - $2.00");
            System.out.println("  2) Medium - $2.50");
            System.out.println("  3) Large  - $3.00");
            System.out.print("  Choice: ");
            String size = getDrinkSize(getIntInput());

            System.out.println();
            System.out.println("  Select flavor:");
            System.out.println("  1) Coke");
            System.out.println("  2) Sprite");
            System.out.println("  3) Lemonade");
            System.out.println("  4) Iced Tea");
            System.out.println("  5) Water");
            System.out.print("  Choice: ");
            String flavor = getDrinkFlavor(getIntInput());

            Drink drink = new Drink(size, flavor);
            currentOrder.addDrink(drink);
            System.out.printf("  Added: %s %s - $%.2f%n%n", size, flavor, drink.getPrice());
        }

        // =========================================================================
        //  ADD GARLIC KNOTS SCREEN
        // =========================================================================

        private void displayAddGarlicKnotsScreen() {
            printDivider();
            System.out.println("  GARLIC KNOTS - $1.50");
            printDivider();
            System.out.println("  Add garlic knots to your order?");
            System.out.println("  1) Yes   2) No");
            System.out.print("  Choice: ");

            if (getIntInput() == 1) {
                currentOrder.addGarlicKnots(new GarlicKnots());
                System.out.println("  Garlic Knots added!");
                System.out.println();
            }
        }

        // =========================================================================
        //  CHECKOUT SCREEN
        // =========================================================================

        /**
         * Shows the full order summary and asks for confirmation.
         * Validates first -- if the order is empty the customer is sent back.
         * Confirm -> saves receipt file -> returns to Home Screen.
         * Cancel  -> discards order    -> returns to Home Screen.
         */

        private void displayCheckoutScreen() {
            printDivider();
            System.out.println("  CHECKOUT");
            printDivider();

            // Validate: must have at least one item
            if (!currentOrder.isValid()) {
                System.out.println("  Your order is empty.");
                System.out.println("  Please add at least one pizza, drink, or garlic knots.");
                System.out.println();
                return;
            }

            // Show full order summary
            System.out.println(currentOrder);

            System.out.println("  1) Confirm Order");
            System.out.println("  0) Cancel Order");
            System.out.print("  Choice: ");

            if (getIntInput() == 1) {
                ReceiptWriter.saveReceipt(currentOrder);
                System.out.println("  Order confirmed! See you next time!");
                System.out.println();
            } else {
                System.out.println("  Order cancelled.");
                System.out.println();
            }

            currentOrder = null;  // clear order in both cases
        }

        // =========================================================================
        //  HELPER METHODS
        // =========================================================================

        /**
         * Reads one line from the keyboard and parses it as an integer.
         * WHY scanner.nextLine() INSTEAD OF scanner.nextInt()?
         *   nextInt() leaves the newline character in the buffer. The very
         *   next nextLine() call then reads that leftover newline as an empty
         *   string instead of waiting for real input -- causing silent skips.
         *   Using nextLine() + Integer.parseInt() avoids this entirely.
         *
         * @return the integer typed, or -1 if the input was not a valid number
         */

        private int getIntInput() {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                return -1;  // -1 hits the default case in every switch -> "Invalid choice"
            }
        }

        /** Maps menu choice 1-4 to a crust name string */
        private String getCrustName(int choice) {
            switch (choice) {
                case 1: return "thin";
                case 2: return "regular";
                case 3: return "thick";
                case 4: return "cauliflower";
                default: return "regular";
            }
        }

        /** Maps menu choice 1-3 to pizza size in inches */
        private int getSizeInches(int choice) {
            switch (choice) {
                case 1: return 8;
                case 2: return 12;
                case 3: return 16;
                default: return 12;
            }
        }

        /** Maps menu choice 1-3 to drink size string */
        private String getDrinkSize(int choice) {
            switch (choice) {
                case 1: return "Small";
                case 2: return "Medium";
                case 3: return "Large";
                default: return "Medium";
            }
        }

        /** Maps menu choice 1-5 to drink flavor string */
        private String getDrinkFlavor(int choice) {
            switch (choice) {
                case 1: return "Coke";
                case 2: return "Sprite";
                case 3: return "Lemonade";
                case 4: return "Iced Tea";
                case 5: return "Water";
                default: return "Water";
            }
        }

        // --- Display Helpers -----------------------------------------------------

        /** Prints the PIZZA-licious header banner using plain ASCII characters */
        private void printBanner() {
            System.out.println();
            System.out.println("  ==============================");
            System.out.println("       PIZZA-licious");
            System.out.println("  ==============================");
            System.out.println();
        }

        /** Prints a plain ASCII divider line between sections */
        private void printDivider() {
            System.out.println("  ------------------------------");
        }
    }

