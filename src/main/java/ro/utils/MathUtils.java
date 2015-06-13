package ro.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Istvan on 06.06.2015.
 */
public class MathUtils {

    private static final Logger LOG = LoggerFactory.getLogger(MathUtils.class);

    public static List<String> getAllDigitSequencesFromText(String text) {
        List<String> digitSequences = new ArrayList<>();
        StringBuilder numberBuilder = new StringBuilder();
        boolean foundDigit = false;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            boolean saveNumber = false;
            if (Character.isDigit(currentChar)) {
                foundDigit = true;
                numberBuilder.append(currentChar);
            } else if (foundDigit) {
                foundDigit = false;
                saveNumber = true;
            }
            if (saveNumber || i == text.length() - 1 && Character.isDigit(currentChar)) {
                String number = numberBuilder.toString();
                LOG.debug("number: {}", number);
                digitSequences.add(number);
                numberBuilder = new StringBuilder();
            }
        }
        return digitSequences;
    }

    public static List<Integer> getAllIntegersFromText(String text) {
        List<String> digitSequences = getAllDigitSequencesFromText(text);
//        List<Integer> integers = new ArrayList<>();
//        for (String digitSequence : digitSequences) {
//            integers.add(Integer.parseInt(digitSequence));
//        }
        List<Integer> integers = digitSequences.stream().map(Integer::parseInt).collect(Collectors.toList());
        return integers;
    }

    public static List<String> getAllNumbersFromText(String text) {
        List<String> numbers = new ArrayList<>();
        StringBuilder numberBuilder = new StringBuilder();
        boolean foundDigit = false;
        Character previousChar = null;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            boolean saveNumber = false;
            if (Character.isDigit(currentChar)) {
                foundDigit = true;
                numberBuilder.append(currentChar);
            } else if (foundDigit) {
                if (previousChar == '.' || previousChar == ',') {
                    numberBuilder.deleteCharAt(numberBuilder.length() - 1);
                    foundDigit = false;
                } else if (currentChar == '.' || currentChar == ',') {
                    numberBuilder.append(currentChar);
                } else {
                    foundDigit = false;
                }
                if (!foundDigit) {
                    saveNumber = true;
                }
            }
            if (saveNumber || i == text.length() - 1 && Character.isDigit(currentChar)) {
                String number = numberBuilder.toString();
                LOG.debug("number: {}", number);
                numbers.add(number);
                numberBuilder = new StringBuilder();
            }
            previousChar = currentChar;
        }
        return numbers;
    }

    public static void main(String[] args) {
    }
}
