package resolvit.paragraphparser.configuration.implementations;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import resolvit.paragraphparser.configuration.interfaces.ConfigurationsParser;

@Component
public class ConfigurationsParserImpl implements ConfigurationsParser {

    @Override
    public List<String> parseWordsToExclude(String wordsToExclude) {
        return Arrays.asList(wordsToExclude.split(","));
    }

    @Override
    public List<String> parsePluralEndingRules(String pluralEndingRules) {
        return Arrays.asList(pluralEndingRules.split(","));
    }

    @Override
    public List<String> parseParagraphSentences(String paragraph) {
        return Arrays.asList(paragraph.split("\\."));
    }

}
