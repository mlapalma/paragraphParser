package resolvit.paragraphparser.configuration.interfaces;

import java.util.List;

public interface ConfigurationsParser {

    public List<String> parseWordsToExclude(String wordsToExclude);
    public List<String> parsePluralEndingRules(String pluralEndingRules);
    public List<String> parseParagraphSentences(String paragraph);

}
