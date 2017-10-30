package resolvit.paragraphparser.tests;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
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
import resolvit.paragraphparser.storage.interfaces.JsonStorage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ParagraphParserContextConfiguration.class)
public class JsonStorageUnitTests {

    @Autowired
    private MainConfiguration mainConfiguration;
    @Autowired
    private ConfigurationsParser configurationsParser;
    @Autowired
    private WordsBusinessRules wordsRules;
    @Autowired
    private JsonStorage jsonStorage;

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
    public void json_storage_should_not_be_null(){
        assertNotNull(jsonStorage);
    }

    @Test
    public void json_storage_should_create_word_record(){
        List<String> sentences=configurationsParser.parseParagraphSentences(mainConfiguration.getParagraph());
        List<Integer> positions=wordsRules.findPositions("word", sentences,
                                            configurationsParser.parsePluralEndingRules(
                                                            mainConfiguration.getPluralEndingRules()));
        List<String> rawWords = wordsRules.parseAllParagraphWords(mainConfiguration.getParagraph());
        List<String> wordsDB=new ArrayList<String>();

        for(String word:rawWords){
            wordsDB.add(wordsRules.cleanGrammarChars(word).toLowerCase());
        }

        wordsDB = wordsRules.excludeWords(wordsDB, configurationsParser.parseWordsToExclude(mainConfiguration.getWordsToExclude()));

        Integer occurrences = wordsRules.calculateOccurrences("word",
                                                        wordsDB,
                                                        configurationsParser.parsePluralEndingRules(
                                                        mainConfiguration.getPluralEndingRules()));

        JSONObject wordRecord = jsonStorage.createWordRecord("word", occurrences, positions);

        assertEquals("word",wordRecord.get("word"));
    }

}
