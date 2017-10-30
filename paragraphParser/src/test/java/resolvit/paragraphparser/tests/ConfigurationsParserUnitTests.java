package resolvit.paragraphparser.tests;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ParagraphParserContextConfiguration.class)
public class ConfigurationsParserUnitTests {

    @Autowired
    private ConfigurationsParser configurationsParser;
    @Autowired
    private MainConfiguration mainConfiguration;

    @Test
    public void configurations_parser_should_not_be_null(){
        assertNotNull(configurationsParser);
    }

    @Test
    public void main_configuration_should_not_be_null(){
        assertNotNull(mainConfiguration);
    }

    @Test
    public void configurations_parser_should_parse_words_to_exclude(){
        List<String> wordsToExclude = configurationsParser.parseWordsToExclude(mainConfiguration.getWordsToExclude());

        assertEquals(8,wordsToExclude.size());
        assertEquals("a",wordsToExclude.get(0));
        assertEquals("the",wordsToExclude.get(1));
        assertEquals("and",wordsToExclude.get(2));
        assertEquals("of",wordsToExclude.get(3));
        assertEquals("in",wordsToExclude.get(4));
        assertEquals("be",wordsToExclude.get(5));
        assertEquals("also",wordsToExclude.get(6));
        assertEquals("as",wordsToExclude.get(7));
    }

    @Test
    public void configurations_parser_should_parse_plural_ending_rules(){
        List<String> pluralEndingRules = configurationsParser.parsePluralEndingRules(mainConfiguration.getPluralEndingRules());

        assertEquals(3,pluralEndingRules.size());
        assertEquals("s",pluralEndingRules.get(0));
        assertEquals("es",pluralEndingRules.get(1));
        assertEquals("ies",pluralEndingRules.get(2));
    }

    @Test
    public void configurations_parser_should_parse_paragraph_sentences(){
        List<String> sentences = configurationsParser.parseParagraphSentences(mainConfiguration.getParagraph());

        assertEquals(7,sentences.size());
    }

}
