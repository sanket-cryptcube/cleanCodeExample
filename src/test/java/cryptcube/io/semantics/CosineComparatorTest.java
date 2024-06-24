package cryptcube.io.semantics;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CosineComparatorTest {
    CosineComparator cosineComparator;

    @Test
    void getUniqueWordsTest() {
        System.out.println("Running unique words test");
        String inputText1 = "mary had a little lamb";
        String inputText2 = "john went to a school";

        String[] expectedWords = {"a", "went", "school", "mary", "lamb",
                "had", "john", "to", "little"};

        cosineComparator = new CosineComparator(inputText1, inputText2, true,
                                                null
        );

        String[] uniqueWords = cosineComparator.getUniqueWords();


        Assert.assertEquals(expectedWords.length, uniqueWords.length);
        for (int i = 0; i < expectedWords.length; i++) {
            Assert.assertEquals(expectedWords[i], uniqueWords[i]);
        }
    }

    @Test
    void frequencyVectorTest() {
        System.out.println("Running frequencyVector test");
        String inputText1 = "mary had a little lamb";
        String inputText2 = "john went to a school";

        String[] expectedWords = {"a", "went", "school", "mary", "lamb",
                "had", "john", "to", "little"};

        cosineComparator = new CosineComparator(inputText1, inputText2, true,
                                                null
        );

        String[] uniqueWords = cosineComparator.getUniqueWords();
        cosineComparator.setUniqueWords(uniqueWords);

        Integer[] frequencyVector1 = {1, 0, 0, 1, 1, 1, 0, 0, 1};
        Integer[] frequencyVector2 = {1, 1, 1, 0, 0, 0, 1, 1, 0};

        Integer[] actualFrequencyVector1 =
                cosineComparator.convertToFrequencyVector(inputText1);
        Integer[] actualFrequencyVector2 =
                cosineComparator.convertToFrequencyVector(inputText2);

        for (int i = 0; i < expectedWords.length; i++) {
            Assert.assertEquals(frequencyVector1[i], actualFrequencyVector1[i]);
            Assert.assertEquals(frequencyVector2[i], actualFrequencyVector2[i]);
        }
    }


    @Test
    void getCosineDistanceSimilarText() {
        String inputText1 = "mary had a little lamb";
        String inputText2 = "mary had a little lamb";

        cosineComparator = new CosineComparator(inputText1, inputText2, true,
                                                null
        );

        double cosineDistance = cosineComparator.getCosineDistance();
        Assert.assertEquals(1.0d, cosineDistance, 0.0001d);
    }

    @Test
    void getCosineDistanceNoSimilarText() {
        String inputText1 = "mary had a little lamb";
        String inputText2 = "john goes to school";

        cosineComparator = new CosineComparator(inputText1, inputText2, true,
                                                null
        );

        double cosineDistance = cosineComparator.getCosineDistance();
        Assert.assertEquals(0.0d, cosineDistance, 0.0001d);
    }

    @Test
    void getCosineDistanceBlankText() {
        String inputText1 = "";
        String inputText2 = "";

        cosineComparator = new CosineComparator(inputText1, inputText2, true,
                                                null
        );

        double cosineDistance = cosineComparator.getCosineDistance();
        Assert.assertEquals(1.0d, cosineDistance, 0.0001d);
    }

    @Test
    void getCosineDistanceInvalidText() {
        String inputText1 = null;
        String inputText2 = "";

        try {
            cosineComparator = new CosineComparator(inputText1, inputText2, true,
                                                    null
            );
            Double cosineDistance = cosineComparator.getCosineDistance();
            Assert.assertTrue(false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    void getDotProductTest() {
        System.out.println("Running dot product test");
        String inputText1 = "mary had a little lamb";
        String inputText2 = "john went to a school";

        cosineComparator = new CosineComparator(inputText1, inputText2, true,
                                                null
        );

        Integer[] vector1 = {2, 3, 4};
        Integer[] vector2 = {2, 5, 6};

        Integer expectedDotProduct = ((2 * 2) + (3 * 5) + (4 * 6));

        Integer actualDotProduct = cosineComparator.getDotProduct(
                vector1,
                vector2
                                                                 );
        Assert.assertEquals(expectedDotProduct, actualDotProduct);
    }

    @Test
    void getDotProductTestUnequalVectors() {
        System.out.println("Running dot product test unequal vectors");
        String inputText1 = "mary had a little lamb";
        String inputText2 = "john went to a school";

        cosineComparator = new CosineComparator(inputText1, inputText2, true,
                                                null
        );

        Integer[] vector1 = {2, 3};
        Integer[] vector2 = {2, 5, 6};

        try {
            Integer dotProduct = cosineComparator.getDotProduct(
                    vector1,
                    vector2
                                                               );
        } catch (IllegalArgumentException illegalArgumentException) {
            Assert.assertEquals("Two input vectors are not of the same length", illegalArgumentException.getMessage());
        }
    }

    @Test
    void getMagnitudeTest() {
        System.out.println("Running magnitude test");
        String inputText1 = "mary had a little lamb";
        String inputText2 = "john went to a school";

        cosineComparator = new CosineComparator(inputText1, inputText2, true,
                                                null
        );

        Integer[] vector1 = {2, 3, 0};
        Integer[] vector2 = {2, 5, 6};

        Double expectedMagnitude1 = 3.605d;
        Double expectedMagnitude2 = 8.062d;

        Double actualMagnitude1 = cosineComparator.getMagnitude(vector1);
        Double actualMagnitude2 = cosineComparator.getMagnitude(vector2);

        Assert.assertEquals(expectedMagnitude1, actualMagnitude1, 0.001d);
        Assert.assertEquals(expectedMagnitude2, actualMagnitude2, 0.001d);
    }
}