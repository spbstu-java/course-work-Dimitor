package lab04;

import java.util.*;
import java.util.stream.*;

public class StreamUtils {

    public static double getAverage(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public static List<String> transformToUpperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(str -> "_new_" + str.toUpperCase())
                .collect(Collectors.toList());
    }

    public static List<Integer> getSquaresOfUniqueElements(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(entry -> entry.getKey() * entry.getKey())
                .collect(Collectors.toList());
    }

    public static <T> T getLastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Collection is empty"));
    }

    public static int sumEvenNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    public static Map<Character, String> stringsToMap(List<String> strings) {
        return strings.stream()
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toMap(
                    str -> str.charAt(0),
                    str -> str.length() > 1 ? str.substring(1) : "",
                    (existing, replacement) -> existing
                ));
    }
}
