package main.java;

import java.util.*;
import java.util.stream.IntStream;

public class DiscountCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Get the number of items
        int numItems = Integer.parseInt(scanner.nextLine());

        //Reading all items and their prices
        List<String> items = IntStream.range(0, numItems)
                .mapToObj(i -> scanner.nextLine())
                .toList();

        //Process items and print receipt
        ReceiptResult result = processItems(items);
        printReceipt(result);

        scanner.close();
    }

    public static ReceiptResult processItems(List<String> items) {
        List<String> receipt = new ArrayList<>();
        double total = items.stream().mapToDouble(line -> {
            String[] parts = line.split(" at ");
            String itemName = parts[0];
            double itemPrice = Double.parseDouble(parts[1]);

            double discountRate = getDiscountRate(itemName);

            double discountedPrice = itemPrice * (1 - discountRate);

            //Applying additional discount for clearance items
            if (itemName.contains("clearance")) {
                discountedPrice *= 0.80;
            }

            //Round to the nearest cent
            discountedPrice = Math.round(discountedPrice * 100.0) / 100.0;

            //Adding to receipt
            receipt.add(String.format("%s at %.2f", itemName, discountedPrice));

            return discountedPrice;
        }).sum();

        //Calculate the amount saved
        double originalTotal = items.stream()
                .mapToDouble(line -> Double.parseDouble(line.split(" at ")[1]))
                .sum();
        double amountSaved = Math.round((originalTotal - total) * 100.0) / 100.0;

        //Round total to the nearest cent
        total = Math.round(total * 100.0) / 100.0;

        return new ReceiptResult(receipt, total, amountSaved);
    }

    public static void printReceipt(ReceiptResult result) {
        result.getReceipt().forEach(System.out::println);
        System.out.printf("Total: %.2f%n", result.getTotal());
        System.out.printf("You saved: %.2f%n", result.getAmountSaved());
    }

    private static double getDiscountRate(String itemName) {
        if (itemName.contains("book") || itemName.contains("chocolate") || itemName.contains("food") || itemName.contains("drink") || itemName.contains("wine")) {
            return 0.05;
        } else if (itemName.contains("shirt") || itemName.contains("dress") || itemName.contains("cloth")) {
            return 0.20;
        } else {
            return 0.03;
        }
    }

    //For Test class
    public static class ReceiptResult {
        private final List<String> receipt;
        private final double total;
        private final double amountSaved;

        public ReceiptResult(List<String> receipt, double total, double amountSaved) {
            this.receipt = receipt;
            this.total = total;
            this.amountSaved = amountSaved;
        }

        public List<String> getReceipt() {
            return receipt;
        }

        public double getTotal() {
            return total;
        }

        public double getAmountSaved() {
            return amountSaved;
        }
    }
}