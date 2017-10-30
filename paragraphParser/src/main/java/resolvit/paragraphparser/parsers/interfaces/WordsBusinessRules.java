package resolvit.paragraphparser.parsers.interfaces;

import java.util.List;

public interface WordsBusinessRules {

    public List<String> parseAllParagraphWords(String paragraph);
    public List<String> excludeWords(List<String> paragraphWords, List<String> wordsToExclude);
    public List<String> excludeSameMeaningWords(List<String> paragraphWords, List<String> pluralEndingRules);
    public List<String> sortWords(List<String> paragraphWords);
    public String cleanGrammarChars(String word);
    public Integer calculateOccurrences(String word, List<String> paragraphWords, List<String> pluralEndingRules);
    public List<Integer> findPositions(String word, List<String> sentences, List<String> pluralEndingRules);

}
