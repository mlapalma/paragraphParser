package resolvit.paragraphparser.configuration.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import resolvit.paragraphparser.configuration.interfaces.MainConfiguration;

@Component
@PropertySource("classpath:configuration/mainconfiguration.properties")
public class MainConfigurationImpl implements MainConfiguration {

    @Value("${main.paragraph}")
    private String paragraph;
    @Value("${main.wordsToExclude}")
    private String wordsToExclude;
    @Value("${main.pluralEndingRules}")
    private String pluralEndingRules;

    @Override
    public String getWordsToExclude() {
        return wordsToExclude;
    }

    public void setWordsToExclude(String wordsToExclude) {
        this.wordsToExclude = wordsToExclude;
    }

    @Override
    public String getPluralEndingRules() {
        return pluralEndingRules;
    }

    public void setPluralEndingRules(String pluralEndingRules) {
        this.pluralEndingRules = pluralEndingRules;
    }

    @Override
    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }


}
