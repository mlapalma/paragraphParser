package resolvit.paragraphparser.tests;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import resolvit.paragraphparser.configuration.interfaces.MainConfiguration;
import resolvit.paragraphparser.contextconfigurations.ParagraphParserContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ParagraphParserContextConfiguration.class)
public class MainConfigurationUnitTests {

    @Autowired
    private MainConfiguration mainConfiguration;

    @Test
    public void main_configuration_should_not_be_null(){
        assertNotNull(mainConfiguration);
    }

    @Test
    public void main_configuration_should_retrieve_paragraph(){
        String paragraph = mainConfiguration.getParagraph();
        assertTrue(!paragraph.isEmpty());
    }

    @Test
    public void main_configuration_should_retrieve_words_to_exclude(){
        String wordsToExclude = mainConfiguration.getWordsToExclude();
        assertTrue(!wordsToExclude.isEmpty());
    }

    @Test
    public void main_configuration_should_retrieve_plural_ending_rules(){
        String pluralEndingRules = mainConfiguration.getPluralEndingRules();
        assertTrue(!pluralEndingRules.isEmpty());
    }
}
