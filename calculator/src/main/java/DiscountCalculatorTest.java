package main.java;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountCalculatorTest {

    @Test
    public void testProcessItemsWithInput1() {
        List<String> items = List.of(
                "book at 14.49",
                "shirt at 19.99",
                "chocolate bar at 1.00",
                "clearance chocolate bar at 2.00"
        );

        DiscountCalculator.ReceiptResult result = DiscountCalculator.processItems(items);

        List<String> expectedReceipt = List.of(
                "book at 13.77",
                "shirt at 15.99",
                "chocolate bar at 0.95",
                "clearance chocolate bar at 1.52"
        );

        assertEquals(expectedReceipt, result.getReceipt());
        assertEquals(32.23, result.getTotal());
        assertEquals(5.25, result.getAmountSaved());
    }

    @Test
    public void testProcessItemsWithInput2() {
        List<String> items = List.of(
                "bottle of perfume at 27.99",
                "bottle of wine at 18.99",
                "dress at 9.75",
                "box of chocolates at 11.25"
        );

        DiscountCalculator.ReceiptResult result = DiscountCalculator.processItems(items);

        List<String> expectedReceipt = List.of(
                "bottle of perfume at 27.15",
                "bottle of wine at 18.04",
                "dress at 7.80",
                "box of chocolates at 10.69"
        );

        assertEquals(expectedReceipt, result.getReceipt());
        assertEquals(63.68, result.getTotal());
        assertEquals(4.30, result.getAmountSaved());
    }
}
