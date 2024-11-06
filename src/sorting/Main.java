package sorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static sorting.util.*;

public class Main {
    static final Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) throws FileNotFoundException {
        boolean hasFlag = args.length > 0;
        ArrayList<String> inputs = hasFlag ?
                new ArrayList<>(List.of(args)): new ArrayList<>();

        flagProcess(inputs);
    }

    static void flagProcess(ArrayList<String> inputs) throws FileNotFoundException {
        boolean hasDataType = false;
        boolean hasSorting = false;
        boolean hasInput = false;
        boolean hasOutput = false;
        ArrayList<String> unknownFlags = new ArrayList<>();

        for (String input : inputs) {
            switch (input) {
                case "-sortingType" -> hasSorting = true;
                case "-dataType" -> hasDataType = true;
                case "-inputFile" -> hasInput = true;
                case "-outputFile" -> hasOutput = true;
                default -> {
                    if (!input.matches("(long)|(word)|(line)|(natural)|(byCount)")) {
                        unknownFlags.add(input);
                    }
                }
            }
        }

        if (hasInput || hasOutput) {
            correctBadFlags(inputs, unknownFlags);
            IOProcess(inputs, unknownFlags);
        } else if (hasSorting) {
            sortedTypeProcess(inputs, unknownFlags);
        } else if (hasDataType) {
            dataTypeProcess(inputs, unknownFlags);
        }
    }

    // DataTypes

    static void byCountDataType(ArrayList<String> inputs) {
        String action = getNext(inputs, "-dataType");

        switch (action) {
            case "long" -> {
                longsByCount();
            }
            case "line" -> {
                lineByCount();
            }
            case "word" -> {
                wordsByCount();
            }
            default -> {
                System.out.println("No data type defined!");
            }
        }
    }

    static void naturalDataType(ArrayList<String> inputs) {
        String action = getNext(inputs, "-dataType");

        switch (action) {
            case "long" -> {
                longsNatural();
            }
            case "line" -> {
                linesNatural();
            }
            case "word" -> {
                wordsNatural();
            }
            default -> {
                System.out.println("No data type defined!");
            }
        }
    }

    static void naturalDataTypeIO(ArrayList<String> inputs) throws FileNotFoundException {
        String action = getNext(inputs, "-dataType");

        switch (action) {
            case "long" -> {
                longsNaturalIO(inputs);
            }
            case "line" -> {
                linesNaturalIO(inputs);
            }
            case "word" -> {
                wordsNaturalIO(inputs);
            }
            default -> {
                System.out.println("No data type defined!");
            }
        }
    }

    static void byCountDataTypeIO(ArrayList<String> inputs) throws FileNotFoundException {
        String action = getNext(inputs, "-dataType");

        switch (action) {
            case "long" -> {
                longsByCountIO(inputs);
            }
            case "line" -> {
                lineByCountIO(inputs);
            }
            case "word" -> {
                wordsByCountIO(inputs);
            }
            default -> {
                System.out.println("No data type defined!");
            }
        }
    }

    // flags process

    static void IOProcess(ArrayList<String> inputs, ArrayList<String> unknownFlags) throws FileNotFoundException {
        String sortingType = getNext(inputs, "-sortingType");

        if (!unknownFlags.isEmpty()) {
            unknownFlags.forEach(s -> System.out.printf
                    ("\"%s\" is not a valid parameter. It will be skipped.\n", s));
        }

        switch (sortingType) {
            case "natural" -> {
                naturalDataTypeIO(inputs);
            }
            case "byCount" -> {
                byCountDataTypeIO(inputs);
            }
            default -> {
                System.out.println("No sorting type defined!");
            }
        }
    }

    static void sortedTypeProcess(ArrayList<String> inputs, ArrayList<String> unknownFlags) {
        String sortingType = getNext(inputs, "-sortingType");

        if (!unknownFlags.isEmpty()) {
            unknownFlags.forEach(s -> System.out.printf
                    ("\"%s\" is not a valid parameter. It will be skipped.\n", s));
        }

        switch (sortingType) {
            case "natural" -> {
                naturalDataType(inputs);
            }
            case "byCount" -> {
                byCountDataType(inputs);
            }
            default -> {
                System.out.println("No sorting type defined!");
            }
        }
    }

    static void dataTypeProcess(ArrayList<String> inputs, ArrayList<String> unknownFlags) {
        String action = getNext(inputs, "-dataType");

        if (!unknownFlags.isEmpty()) {
            unknownFlags.forEach(s -> System.out.printf
                    ("\"%s\" is not a valid parameter. It will be skipped.\n", s));
        }

        switch (action) {
            case "long" -> {
                getLongThing();
            }
            case "line" -> {
                getLineThing();
            }
            case "word" -> {
                getWordThing();
            }
            default -> {
                System.out.println("No data type defined!");
            }
        }
    }
}
