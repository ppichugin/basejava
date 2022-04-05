package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println("minValue");
        int[] array1 = new int[]{1, 2, 3, 3, 2, 3};
        System.out.println(" -> OUTPUT: " + minValue(array1));
        int[] array2 = new int[]{9, 8};
        System.out.println(" -> OUTPUT: " + minValue(array2));
        System.out.println("\noddOrEven");
        System.out.println(" -> OUTPUT: " + oddOrEven(Arrays.stream(array1).boxed().collect(Collectors.toList())));
        System.out.println(" -> OUTPUT: " + oddOrEven(Arrays.stream(array2).boxed().collect(Collectors.toList())));
    }

    static int minValue(int[] values) {
        System.out.print("INPUT: " + Arrays.toString(values));
        return Arrays.stream(values).distinct().sorted().reduce(0, (subtotal, x) -> subtotal * 10 + x);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        System.out.print("INPUT: " + integers.toString());
        final Integer sum = integers.stream().reduce(0, Integer::sum);
        System.out.println(" SUM: " + sum);

        /*
          Odd quantity of odd digits will give odd sum.
          Even quantity if odd digits will give even sum.
          Sum of all even digits will give always even sum.

          Therefore, if oddList contains an even quantity of digits
          then sum of all numbers will always be even, and we should return oddList.

          [1, 2, 3, 3, 2, 3] ->
          odd numbers {1, 3, 3, 3}: Quantity = 4, therefore sum of all numbers will be even [14],
          and we should ignore even number and return only odd numbers (oddList).

          [1, 2, 3, 3, 2] ->
          odd numbers {1, 3, 3}: Quantity = 3, therefore sum of all numbers will be odd [11],
          and therefore we ignore all odd numbers ("remove") and return evenList with even numbers.
         */
/*
        return integers.stream().collect(Collectors.teeing(
                Collectors.filtering(i -> i % 2 == 0, Collectors.toList()),
                Collectors.filtering(i -> i % 2 == 1, Collectors.toList()),
                (evenList, oddList) -> oddList.size() % 2 == 1 ? evenList : oddList));
*/

        /**
         * If sum of all numbers is even, we do return all odd numbers.
         * Otherwise, we do return all even numbers.
         */
        return integers.stream().collect(Collectors.filtering(integer -> {
            if (sum % 2 == 0) return integer % 2 == 1;
            return integer % 2 == 0;
        }, Collectors.toList()));
    }
}