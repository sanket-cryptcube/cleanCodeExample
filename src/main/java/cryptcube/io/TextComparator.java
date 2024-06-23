package cryptcube.io;

import cryptcube.io.config.ApplicationConfig;
import cryptcube.io.semantics.CosineComparator;

public class TextComparator {
    public static void main(String[] args) throws Exception {
        ApplicationConfig applicationConfig = new ApplicationConfig(args);
        CosineComparator cosineComparator = new CosineComparator(
                applicationConfig.getInputText1(),
                applicationConfig.getInputText2(),
                applicationConfig.getIgnoreCase(), applicationConfig.getPrepositionsBlackList()
        );
        System.out.println("Cosine similarity = " + cosineComparator.getCosineDistance());
    }
}