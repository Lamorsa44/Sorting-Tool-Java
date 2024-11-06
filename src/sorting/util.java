package sorting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class util {
    static Scanner scanner = new Scanner(System.in);

    static void sortIntegers() {
        ArrayList<Integer> list = new ArrayList<>();

        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        Collections.sort(list);
        String sorted = list.stream()
                .map(String::valueOf)
                .reduce("", (s1, s2) -> s1.concat(" " + s2));

        System.out.printf("Total numbers: %d.\n", list.size());
        System.out.printf("Sorted data: %s", sorted);
    }

    // IO

    static String getNext(ArrayList<String> inputs, String action) {
        try {
            return inputs.get(inputs.indexOf(action) + 1);
        } catch (Exception e) {
            return "";
        }
    }

    static boolean hasInput(List<String> input) {
        return input.contains("-inputFile");
    }

    static boolean hasOutput(List<String> input) {
        return input.contains("-outputFile");
    }

    static String returnInput(List<String> input) {
        return input.get(input.indexOf("-inputFile") + 1);
    }

    static String returnOutput(List<String> input) {
        return input.get(input.indexOf("-outputFile") + 1);
    }

    static void correctBadFlags(ArrayList<String> inputs, ArrayList<String> badFlags) {
        for (String input : inputs) {
            if (input.matches("-inputFile|-outputFile")) {
                badFlags.remove(badFlags.indexOf(input) + 1);
            }
        }
    }

    //      NaturalIO

    static void linesNaturalIO(ArrayList<String> inputs) throws FileNotFoundException {
        boolean hasInput = hasInput(inputs);
        boolean hasOutput = hasOutput(inputs);

        if (hasInput) {
            scanner = new Scanner(new File(returnInput(inputs)));
        }

        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        lines.sort(Comparator.comparingInt(String::length).reversed());

        if (hasOutput) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(returnOutput(inputs)))) {
                bufferedWriter.write(String.format("Total lines: %d\n", lines.size()));
                bufferedWriter.write("Sorted data:\n");
                for (String line : lines) {
                    bufferedWriter.write(String.format("%s\n", line));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Total lines: " + lines.size());
            System.out.println("Sorted data:");
            lines.forEach(System.out::println);
        }
    }

    static void wordsNaturalIO(ArrayList<String> inputs) throws FileNotFoundException {
        boolean hasInput = hasInput(inputs);
        boolean hasOutput = hasOutput(inputs);

        if (hasInput) {
            scanner = new Scanner(new File(returnInput(inputs)));
        }

        TreeMap<String, Integer> words = new TreeMap<>();
        getWords(words);
        int total = words.values().stream().mapToInt(Integer::intValue).sum();

        if (hasOutput) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(returnOutput(inputs)))) {
                bufferedWriter.write(String.format("Total words: %d.\n", words.size()));
                bufferedWriter.write("Sorted data: ");
                for (var entry : words.entrySet()) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        bufferedWriter.write(String.format("%s ", entry.getKey()));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.printf("Total words: %d.\n", total);
            System.out.print("Sorted data: ");
            for (var entry : words.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    System.out.print(entry.getKey() + " ");
                }
            }
        }
    }

    static void longsNaturalIO(ArrayList<String> inputs) throws FileNotFoundException {
        boolean hasInput = hasInput(inputs);
        boolean hasOutput = hasOutput(inputs);

        if (hasInput) {
            scanner = new Scanner(new File(returnInput(inputs)));
        }

        TreeMap<Integer, Integer> numbers = new TreeMap<>();
        getLongs(numbers);
        int total = numbers.values().stream().mapToInt(Integer::intValue).sum();

        if (hasOutput) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(returnOutput(inputs)))) {
                bufferedWriter.write(String.format("Total numbers: %d.\n", numbers.size()));
                bufferedWriter.write("Sorted data: ");
                for (var entry : numbers.entrySet()) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        bufferedWriter.write(String.format("%s ", entry.getKey()));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.printf("Total numbers: %d.\n", total);
            System.out.print("Sorted data: ");
            for (var entry : numbers.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    System.out.print(entry.getKey() + " ");
                }
            }
        }
    }

        // ByCountIO

    static void lineByCountIO(ArrayList<String> inputs) throws FileNotFoundException {
        boolean hasInput = hasInput(inputs);
        boolean hasOutput = hasOutput(inputs);

        if (hasInput) {
            scanner = new Scanner(new File(returnInput(inputs)));
        }

        HashMap<String, Integer> lines = new HashMap<>();
        while (scanner.hasNextLine()) {
            lines.compute(scanner.nextLine(), (k, v) -> v == null ? 1 : v + 1);
        }

        int total = lines.values().stream().mapToInt(Integer::intValue).sum();
        var sorted = lines.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .toList();

        if (hasOutput) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(returnOutput(inputs)))) {
                bufferedWriter.write(String.format("Total lines: %d.\n", total));
                for (var entry : lines.entrySet()) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        bufferedWriter.write(String.format("%s: %d time(s), %.0f%%\n", entry.getKey(), entry.getValue()
                                , (float) entry.getValue()/total * 100));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.printf("Total lines: %d.\n", total);
            sorted.forEach(e ->
                    System.out.printf("%s: %d time(s), %.0f%%\n", e.getKey(), e.getValue()
                            , (float) e.getValue()/total * 100));
        }
    }

    static void wordsByCountIO(ArrayList<String> inputs) throws FileNotFoundException {
        boolean hasInput = hasInput(inputs);
        boolean hasOutput = hasOutput(inputs);

        if (hasInput) {
            scanner = new Scanner(new File(returnInput(inputs)));
        }

        HashMap<String, Integer> words = new HashMap<>();
        getWords(words);

        int total = words.values().stream().mapToInt(Integer::intValue).sum();
        var sorted = words.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .toList();

        if (hasOutput) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(returnOutput(inputs)))) {
                bufferedWriter.write(String.format("Total words: %d.\n", total));
                for (var entry : words.entrySet()) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        bufferedWriter.write(String.format("%s: %d time(s), %.0f%%\n", entry.getKey(), entry.getValue()
                                , (float) entry.getValue()/total * 100));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.printf("Total words: %d.\n", total);
            sorted.forEach(e ->
                    System.out.printf("%s: %d time(s), %.0f%%\n", e.getKey(), e.getValue()
                            , (float) e.getValue()/total * 100));
        }
    }

    static void longsByCountIO(ArrayList<String> inputs) throws FileNotFoundException {
        boolean hasInput = hasInput(inputs);
        boolean hasOutput = hasOutput(inputs);

        if (hasInput) {
            scanner = new Scanner(new File(returnInput(inputs)));
        }

        HashMap<Integer, Integer> numbers = new HashMap<>();
        getLongs(numbers);
        int total = numbers.values().stream().mapToInt(Integer::intValue).sum();
        var sorted = numbers.entrySet().stream().sorted(Map.Entry.comparingByValue()).toList();

        if (hasOutput) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(returnOutput(inputs)))) {
                bufferedWriter.write(String.format("Total numbers: %d.\n", total));
                for (var entry : numbers.entrySet()) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        bufferedWriter.write(String.format("%s: %d time(s), %.0f%%\n", entry.getKey(), entry.getValue()
                                , (float) entry.getValue()/total * 100));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.printf("Total numbers: %d.\n", total);
            sorted.forEach(e ->
                    System.out.printf("%d: %d time(s), %.0f%%\n", e.getKey(), e.getValue()
                            , (float) e.getValue()/total * 100));
        }
    }

    // More Util

    static void getWords(Map<String, Integer> words) {
        Pattern pattern = Pattern.compile("\\S+");
        String lines  = scanner.nextLine();
        while (scanner.hasNextLine()) {
            lines = lines.concat(" " + scanner.nextLine());
        }
        Matcher matcher = pattern.matcher(lines);

        while (matcher.find()) {
            String word = matcher.group();
            words.compute(word, (k, v) -> v == null ? 1 : v + 1);
        }
    }

    static void getLongs(Map<Integer, Integer> numbers) {
        while (scanner.hasNext()) {
            String input = scanner.next();
            if (input.matches("-?[0-9]+")) {
                numbers.compute(Integer.valueOf(input), (k, v) -> v == null ? 1 : v + 1);
            } else {
                System.out.printf("\"%s\" is not a long. It will be skipped.\n", input);
            }
        }
    }

    // Natural

    static void linesNatural() {
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        lines.sort(Comparator.comparingInt(String::length).reversed());
        System.out.println("Total lines: " + lines.size());
        System.out.println("Sorted data:");
        lines.forEach(System.out::println);
    }

    static void wordsNatural() {
        TreeMap<String, Integer> words = new TreeMap<>();
        getWords(words);
        int total = words.values().stream().mapToInt(Integer::intValue).sum();

        System.out.printf("Total words: %d.\n", total);
        System.out.print("Sorted data: ");
        for (var entry : words.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                System.out.print(entry.getKey() + " ");
            }
        }
    }

    static void longsNatural() {
        TreeMap<Integer, Integer> numbers = new TreeMap<>();
        getLongs(numbers);
        int total = numbers.values().stream().mapToInt(Integer::intValue).sum();

        System.out.printf("Total numbers: %d.\n", total);
        System.out.print("Sorted data: ");
        for (var entry : numbers.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                System.out.print(entry.getKey() + " ");
            }
        }
    }

    // ByCount

    static void lineByCount() {
        HashMap<String, Integer> lines = new HashMap<>();
        while (scanner.hasNextLine()) {
            lines.compute(scanner.nextLine(), (k, v) -> v == null ? 1 : v + 1);
        }

        int total = lines.values().stream().mapToInt(Integer::intValue).sum();
        var sorted = lines.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .toList();

        System.out.printf("Total lines: %d.\n", total);
        sorted.forEach(e ->
                System.out.printf("%s: %d time(s), %.0f%%\n", e.getKey(), e.getValue()
                        , (float) e.getValue()/total * 100));
    }

    static void wordsByCount() {
        HashMap<String, Integer> words = new HashMap<>();
        getWords(words);

        int total = words.values().stream().mapToInt(Integer::intValue).sum();
        var sorted = words.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .toList();

        System.out.printf("Total words: %d.\n", total);
        sorted.forEach(e ->
                System.out.printf("%s: %d time(s), %.0f%%\n", e.getKey(), e.getValue()
                        , (float) e.getValue()/total * 100));
    }

    static void longsByCount() {
        HashMap<Integer, Integer> numbers = new HashMap<>();
        getLongs(numbers);
        int total = numbers.values().stream().mapToInt(Integer::intValue).sum();
        var sorted = numbers.entrySet().stream().sorted(Map.Entry.comparingByValue()).toList();

        System.out.printf("Total numbers: %d.\n", total);
        sorted.forEach(e ->
                System.out.printf("%d: %d time(s), %.0f%%\n", e.getKey(), e.getValue()
                        , (float) e.getValue()/total * 100));
    }

    // Ignore

    static void getWordThing() {
        int wordCount = 0;
        ArrayList<String> longestWords = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();
        int timesLongestWord = 0;
        int lengthLongestWord = 0;
        float longestOccurrence = 0f;

        while (scanner.hasNext()) {
            String word = scanner.next();
            words.add(word);
            if (word.length() > lengthLongestWord) {
                lengthLongestWord = word.length();
            }
        }

        wordCount = words.size();

        final int tmp = lengthLongestWord;
        longestWords.addAll(words.stream()
                .filter(s -> s.length() == tmp).toList());

        timesLongestWord = longestWords.size();

        longestOccurrence = (float) timesLongestWord / wordCount * 100;
        String longestWord = String.join(" ", longestWords);

        System.out.printf("Total words: %d.\n", wordCount);
        System.out.printf("The longest word: %s (1 time(s), %.0f%%)."
                , longestWord, longestOccurrence);
    }

    static void getLineThing() {
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        String longestLine = lines.stream().reduce("", (s1, s2) -> {
            return s1.length() > s2.length() ? s1 : s2;
        });

        var times =  lines.stream()
                .filter(s -> s.equals(longestLine)).count();

        float percentage = (float) times / lines.size() * 100;

        System.out.printf("Total lines: %d.\n", lines.size());
        System.out.printf("The longest line:\n%s\n(%d time(s), %.0f%%)."
                , longestLine, times, percentage);
    }

    static void getLongThing() {
        int total = 0;
        long greatest = Long.MIN_VALUE;
        int timesGreatest = 0;

        while (scanner.hasNext()) {
            String input = scanner.next();
            if (!input.matches("-?[0-9]+")) {
                System.out.printf("\"%s\" is not a long. It will be skipped.\n", input);
                continue;
            }

            long number = Long.parseLong(input);
            if (number > greatest) {
                greatest = number;
                timesGreatest = 1;
            }
            else if (number == greatest) {
                timesGreatest++;
            }
            total++;
        }
        float percentage = (float) timesGreatest / total * 100;

        System.out.printf("Total numbers: %d.\n", total);
        System.out.printf("The greatest number: %d (%d time(s), %.0f%%)."
                , greatest, timesGreatest, percentage);
    }
}
