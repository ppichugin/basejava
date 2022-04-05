package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        //creating arrays with unique numbers in sorted order
        int[] tempArray = Arrays.stream(values).distinct().sorted().toArray();
        // each unique number we do multiple to 10^i
        return IntStream.range(0, tempArray.length).map(new IntUnaryOperator() {
            @Override
            public int applyAsInt(int i) {
                return (int) (tempArray[i] * Math.pow(10, tempArray.length - i - 1));
            }
        }).sum();
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        System.out.print("\nINPUT: " + integers.toString());
        System.out.println(" SUM: " + integers.stream().reduce(0, Integer::sum));
        return integers.stream().collect(Collectors.teeing(
                // composing downstream for even numbers
                Collectors.filtering(i -> i % 2 == 0, Collectors.toList()),
                // composing downstream for odd numbers
                Collectors.filtering(i -> i % 2 == 1, Collectors.toList()),
                new BiFunction<List<Integer>, List<Integer>, List<Integer>>() {
                    @Override
                    public List<Integer> apply(List<Integer> evenList, List<Integer> oddList) {
                        System.out.println("Odd list size: " + oddList.size() + ". Even list size: " + evenList.size());
                        return oddList.size() % 2 == 1 ? evenList : oddList;
                        /**
                         * Odd quantity of odd digits will give odd sum.
                         * Even quantity if odd digits will give even sum.
                         * Sum of all even digits will give always even sum.
                         *
                         * Therefore, if oddList contains an even quantity of digits
                         * then sum of all numbers will always be even, and we should return oddList.
                         *
                         * [1, 2, 3, 3, 2, 3] ->
                         * odd numbers {1, 3, 3, 3}: Quantity = 4, therefore sum of all numbers will be even [14],
                         * and we should ignore even number and return only odd numbers (oddList).
                         *
                         * [1, 2, 3, 3, 2] ->
                         * odd numbers {1, 3, 3}: Quantity = 3, therefore sum of all numbers will be odd [11],
                         * and therefore we ignore all odd numbers ("remove") and return evenList with even numbers.
                         */
                    }
                }));
    }

}