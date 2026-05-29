package com.pluralsight.util;

import com.pluralsight.models.Order;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

    /**
     * ReceiptWriter.java - Saves a completed Order to a timestamped .txt file.
     *
     * HOW IT WORKS:
     *   1. Get the current date/time
     *   2. Format it as yyyyMMdd-HHmmss  (e.g. "20240115-143022")
     *   3. Build the filename:  receipts/20240115-143022.txt
     *   4. Create the receipts/ folder if it does not exist yet
     *   5. Write the order's toString() content into the file
     *   6. Close the file (handled automatically by try-with-resources)
     *
     * OOP CONCEPTS:
     *   - Utility class: only static methods -- never instantiated
     *   - Single Responsibility: does ONE thing -- saves receipts
     *
     * JAVA CONCEPTS:
     *   - FileWriter / PrintWriter  for writing text files
     *   - LocalDateTime + DateTimeFormatter  for timestamps
     *   - try-with-resources  ensures the file is always closed
     */
    public class ReceiptWriter {

        // Folder where all receipt files are saved (relative to project root)
        private static final String RECEIPTS_FOLDER = "receipts/";

        // Required filename format: yyyyMMdd-HHmmss  e.g. 20240115-143022
        private static final DateTimeFormatter FORMATTER =
                DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

        // Human-readable date format used inside the receipt file header
        private static final DateTimeFormatter READABLE =
                DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");

        /**
         * Saves the completed order to a timestamped receipt file.
         *
         * @param  order  the completed Order to save
         * @return        the filename that was created, or null on failure
         */
        public static String saveReceipt(Order order) {

            // Step 1: Build the filename from the current timestamp
            LocalDateTime now       = LocalDateTime.now();
            String        timestamp = now.format(FORMATTER);
            String        filename  = RECEIPTS_FOLDER + timestamp + ".txt";

            // Step 2: Create the receipts/ folder (no error if it already exists)
            new java.io.File(RECEIPTS_FOLDER).mkdirs();

            // Step 3: Write the file.
            // try-with-resources automatically closes PrintWriter when the
            // block ends -- even if an exception is thrown inside it.
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

                writer.println("================================================");
                writer.println("         PIZZA-licious - Order Receipt");
                writer.println("================================================");
                writer.println("Date/Time: " + now.format(READABLE));
                writer.println();
                writer.println(order.toString());
                writer.println("================================================");
                writer.println("     Thank you for choosing PIZZA-licious!");
                writer.println("================================================");

                System.out.println();
                System.out.println("  Receipt saved: " + filename);
                return filename;

            } catch (IOException e) {
                // If the file cannot be written (permissions, disk full, etc.)
                // print a friendly message instead of crashing the whole app.
                System.out.println("  Could not save receipt: " + e.getMessage());
                return null;
            }
        }
    }

