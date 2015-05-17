package ro.utils;

/**
 * Created by Istvan on 29.03.2015.
 */
public class StringUtils {

    /**
     * Checks if a text contains a list of terms with the option of ignoring or not the case. Returns true if the text contains all the terms and false otherwise.
     *
     * @param text
     * @param ignoreCase
     * @param terms
     * @return
     */
    public static boolean checkIfTextContainsTerms(String text, boolean ignoreCase, String... terms) {
        if (ignoreCase) {
            text = text.toLowerCase();
        }
        for (String term : terms) {
            if (ignoreCase) {
                term = term.toLowerCase();
            }
            if (!text.contains(term)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a text contains a list of terms. Returns true if the text contains all the terms and false otherwise.
     *
     * @param text
     * @param terms
     * @return
     */
    public static boolean checkIfTextContainsTerms(String text, String... terms) {
        return checkIfTextContainsTerms(text,  true, terms);
    }
    
    /**
     * Checks if a text contains any terms from a list with the option of ignoring or not the case. Returns true if the text contains any of the terms and false otherwise.
     *
     * @param text
     * @param ignoreCase
     * @param terms
     * @return
     */
    public static boolean checkIfTextContainsAnyTerm(String text, boolean ignoreCase, String... terms) {
        if (ignoreCase) {
            text = text.toLowerCase();
        }
        for (String term : terms) {
            if (ignoreCase) {
                term = term.toLowerCase();
            }
            if (text.contains(term)) {
                return true;
            }
        }
        return false;
    }

    public static String getVarargsAsString(String... terms){
        StringBuilder result = new StringBuilder();
//        for(String term : terms){
        for (int i = 0; i < terms.length; i++){
            String term = terms[i];
            if(!term.isEmpty()){
                result.append(term + (i < terms.length - 1 ? ", " : ""));
            }
        }
        return result.toString();
    }
}
