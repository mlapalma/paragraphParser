package resolvit.paragraphparser.tests;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import resolvit.paragraphparser.configuration.interfaces.ConfigurationsParser;
import resolvit.paragraphparser.configuration.interfaces.MainConfiguration;
import resolvit.paragraphparser.contextconfigurations.ParagraphParserContextConfiguration;
import resolvit.paragraphparser.parsers.interfaces.WordsBusinessRules;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ParagraphParserContextConfiguration.class)
public class WordsBusinessRulesUnitTests {

    @Autowired
    private MainConfiguration mainConfiguration;
    @Autowired
    private ConfigurationsParser configurationsParser;
    @Autowired
    private WordsBusinessRules wordsRules;

    @Test
    public void configurations_parser_should_not_be_null(){
        assertNotNull(configurationsParser);
    }

    @Test
    public void main_configuration_should_not_be_null(){
        assertNotNull(mainConfiguration);
    }

    @Test
    public void words_rules_should_not_be_null(){
        assertNotNull(wordsRules);
    }

    @Test
    public void words_rules_should_parse_all_paragraph_raw_words(){
        List<String> rawWords = wordsRules.parseAllParagraphWords(mainConfiguration.getParagraph());
        assertEquals(127,rawWords.size());
    }

    @Test
    public void words_rules_should_clean_grammar_chars_in_a_word(){
        String wordToClean = "word,.;:\"";
        assertEquals("word",wordsRules.cleanGrammarChars(wordToClean));
    }

    @Test
    public void words_rules_should_exclude_some_words(){
        List<String> rawWords = wordsRules.parseAllParagraphWords(mainConfiguration.getParagraph());
        List<String> wordsDB=new ArrayList<String>();

        for(String word:rawWords){
            wordsDB.add(wordsRules.cleanGrammarChars(word).toLowerCase());
        }

        assertEquals(127,wordsDB.size());

        wordsDB = wordsRules.excludeWords(wordsDB, configurationsParser.parseWordsToExclude(mainConfiguration.getWordsToExclude()));

        assertEquals(88,wordsDB.size());
    }

    @Test
    public void words_rules_should_sort_alphabetically(){
        List<String> rawWords = wordsRules.parseAllParagraphWords(mainConfiguration.getParagraph());
        List<String> wordsDB=new ArrayList<String>();

        for(String word:rawWords){
            wordsDB.add(wordsRules.cleanGrammarChars(word).toLowerCase());
        }

        assertEquals(127,wordsDB.size());

        wordsDB = wordsRules.excludeWords(wordsDB, configurationsParser.parseWordsToExclude(mainConfiguration.getWordsToExclude()));

        assertEquals(88,wordsDB.size());
        assertEquals("take",wordsDB.get(0));

        wordsDB = wordsRules.sortWords(wordsDB);
        assertEquals("all",wordsDB.get(0));
    }

    @Test
    public void words_rules_should_set_occurrences(){
        List<String> rawWords = wordsRules.parseAllParagraphWords(mainConfiguration.getParagraph());
        List<String> wordsDB=new ArrayList<String>();

        for(String word:rawWords){
            wordsDB.add(wordsRules.cleanGrammarChars(word).toLowerCase());
        }

        assertEquals(127,wordsDB.size());

        wordsDB = wordsRules.excludeWords(wordsDB, configurationsParser.parseWordsToExclude(mainConfiguration.getWordsToExclude()));

        assertEquals(88,wordsDB.size());

        assertEquals((int)8, (int)wordsRules.calculateOccurrences("word",
                                                        wordsDB,
                                                        configurationsParser.parsePluralEndingRules(
                                                        mainConfiguration.getPluralEndingRules())));

        assertEquals((int)3, (int)wordsRules.calculateOccurrences("fish",
                                                        wordsDB,
                                                        configurationsParser.parsePluralEndingRules(
                                                        mainConfiguration.getPluralEndingRules())));
    }

    @Test
    public void words_rules_should_find_positions(){
        List<String> rawWords = wordsRules.parseAllParagraphWords(mainConfiguration.getParagraph());
        List<String> wordsDB=new ArrayList<String>();
        List<Integer> positions = new ArrayList<Integer>();
        List<String> sentences=new ArrayList<String>();

        for(String word:rawWords){
            wordsDB.add(wordsRules.cleanGrammarChars(word).toLowerCase());
        }

        assertEquals(127,wordsDB.size());

        wordsDB = wordsRules.excludeWords(wordsDB, configurationsParser.parseWordsToExclude(mainConfiguration.getWordsToExclude()));

        assertEquals(88,wordsDB.size());

        sentences=configurationsParser.parseParagraphSentences(mainConfiguration.getParagraph());
        positions=wordsRules.findPositions("word", sentences,
                                            configurationsParser.parsePluralEndingRules(
                                                            mainConfiguration.getPluralEndingRules()));

        assertEquals(8, positions.size());
    }

    @Test
    public void words_rules_should_exclude_same_meaning_words(){
        List<String> rawWords = wordsRules.parseAllParagraphWords(mainConfiguration.getParagraph());
        List<String> wordsDB=new ArrayList<String>();

        for(String word:rawWords){
            wordsDB.add(wordsRules.cleanGrammarChars(word).toLowerCase());
        }

        assertEquals(127,wordsDB.size());

        wordsDB = wordsRules.excludeWords(wordsDB, configurationsParser.parseWordsToExclude(mainConfiguration.getWordsToExclude()));

        assertEquals(88,wordsDB.size());

        wordsDB = wordsRules.excludeSameMeaningWords(wordsDB, configurationsParser.parseWordsToExclude(mainConfiguration.getPluralEndingRules()));

        assertEquals(65,wordsDB.size());
    }
}
