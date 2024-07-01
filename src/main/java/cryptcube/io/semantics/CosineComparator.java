package cryptcube.io.semantics;

import java.util.*;

public class CosineComparator {
    private String   inputText1;
    private String   inputText2;
    private Boolean  ignoreCase;
    private String[] prepositionsToIgnore;

    private Map<String, Integer> histogram1 = new HashMap<>();
    private Map<String, Integer> histogram2 = new HashMap<>();

    private String[]  uniqueWords;
    private Integer[] frequencyVector1;
    private Integer[] frequencyVector2;

    public CosineComparator(String inputText1,
                            String inputText2,
                            Boolean ignoreCase,
                            String[] prepositionsToIgnore) {
        this.ignoreCase = ignoreCase;
        this.inputText1 = inputText1;
        this.inputText2 = inputText2;
        this.prepositionsToIgnore = prepositionsToIgnore;
    }

    /**
     * @return cosineDistance
     */
    public Double getCosineDistance() {
        this.uniqueWords = getUniqueWords();
        this.frequencyVector1 = convertToFrequencyVector(inputText1);
        this.frequencyVector2 = convertToFrequencyVector(inputText2);

        double dotProduct = getDotProduct(frequencyVector1, frequencyVector2);
        double magnitude1 = getMagnitude(frequencyVector1);
        double magnitude2 = getMagnitude(frequencyVector2);

        return dotProduct / (magnitude1 * magnitude2);
    }

    public String[] getUniqueWords() {
        String[] wordsText1 = inputText1.split(" ");
        String[] wordsText2 = inputText2.split(" ");

        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(Arrays.asList(wordsText1));
        uniqueWords.addAll(Arrays.asList(wordsText2));

        String[] uniqueWordsArray = new String[uniqueWords.size()];
        int i = 0;
        for (String word : uniqueWords) {
            uniqueWordsArray[i++] = word;
        }

        return uniqueWordsArray;
    }

    public Integer[] convertToFrequencyVector(String inputText) {
        Map<String, Integer> histogram = new HashMap<>();
        for (String word : inputText.split(" ")) {
            if (histogram.containsKey(word)) {
                int frequency = histogram.get(word);
                histogram.put(word, ++frequency);
            } else {
                histogram.put(word, 1);
            }
        }

        Integer[] frequencyVector = new Integer[uniqueWords.length];
        for (int i = 0; i < frequencyVector.length; i++) {
            frequencyVector[i] = histogram.getOrDefault(uniqueWords[i], 0);
        }

        return frequencyVector;
    }

    public Double getMagnitude(Integer[] vector) {
        double magnitude = 0.0d;
        for (Integer integer : vector) {
            magnitude += integer * integer;
        }
        return magnitude;
    }

    public Integer getDotProduct(Integer[] vector1, Integer[] vector2) throws IllegalArgumentException {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Two input vectors are not of " +
                                                       "the same length");
        }

        int dotProduct = 0;
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += (vector1[i] * vector2[i]);
        }

        return dotProduct;
    }

    public String getInputText1() {
        return inputText1;
    }

    public void setInputText1(String inputText1) {
        this.inputText1 = inputText1;
    }

    public String getInputText2() {
        return inputText2;
    }

    public void setInputText2(String inputText2) {
        this.inputText2 = inputText2;
    }

    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String[] getPrepositionsToIgnore() {
        return prepositionsToIgnore;
    }

    public void setPrepositionsToIgnore(String[] prepositionsToIgnore) {
        this.prepositionsToIgnore = prepositionsToIgnore;
    }

    public Map<String, Integer> getHistogram1() {
        return histogram1;
    }

    public void setHistogram1(Map<String, Integer> histogram1) {
        this.histogram1 = histogram1;
    }

    public Map<String, Integer> getHistogram2() {
        return histogram2;
    }

    public void setHistogram2(Map<String, Integer> histogram2) {
        this.histogram2 = histogram2;
    }

    public void setUniqueWords(String[] uniqueWords) {
        this.uniqueWords = uniqueWords;
    }

    public Integer[] getFrequencyVector1() {
        return frequencyVector1;
    }

    public void setFrequencyVector1(Integer[] frequencyVector1) {
        this.frequencyVector1 = frequencyVector1;
    }

    public Integer[] getFrequencyVector2() {
        return frequencyVector2;
    }

    public void setFrequencyVector2(Integer[] frequencyVector2) {
        this.frequencyVector2 = frequencyVector2;
    }
}
