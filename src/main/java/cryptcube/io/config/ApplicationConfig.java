package cryptcube.io.config;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ApplicationConfig {
    private final String[] prepositionsBlackList;
    private final Boolean  ignoreCase;
    private final String   inputText1;
    private final String   inputText2;

    public ApplicationConfig(String[] args) throws ArgumentParserException, IOException {
        Namespace namespace = buildNamespace(args);
        this.ignoreCase = namespace.getBoolean("ignoreCase");
        this.prepositionsBlackList = namespace.getString("prepositions" +
                                                                 ".blacklist").split(",");
        String inputFilePath1 = namespace.getString("input.file.1");
        this.inputText1 = Files.readString(Paths.get(inputFilePath1));

        String inputFilePath2 = namespace.getString("input.file.2");
        this.inputText2 = Files.readString(Paths.get(inputFilePath2));
    }

    public String getInputText1() {
        return inputText1;
    }

    public String getInputText2() {
        return inputText2;
    }

    public String[] getPrepositionsBlackList() {
        return prepositionsBlackList;
    }

    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    private Namespace buildNamespace(String[] args) throws ArgumentParserException {
        ArgumentParser argumentParser = ArgumentParsers.newFor("TextComparator").build()
                                                       .defaultHelp(true)
                                                       .description("Clean " +
                                                                            "code example to demonstrate TDD");
        argumentParser.addArgument("--prepositions.blacklist").type(String.class)
                      .setDefault("").help("Comma separated list of " +
                                                   "prepositions to ignore in" +
                                                   " text comparison");
        argumentParser.addArgument("--ignoreCase").type(Boolean.class)
                      .setDefault(false).choices(true, false).help("Ignore " +
                                                                           "case when " +
                                                                           "comparing " +
                                                                           "texts.");

        argumentParser.addArgument("--input.file.1").type(String.class)
                      .setDefault("").help("Path to input file1");
        argumentParser.addArgument("--input.file.2").type(String.class)
                      .setDefault("").help("Path to input file");
        return argumentParser.parseArgs(args);
    }
}
