
package resolvit.paragraphparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import resolvit.paragraphparser.configuration.interfaces.ConfigurationsParser;
import resolvit.paragraphparser.configuration.interfaces.MainConfiguration;
import resolvit.paragraphparser.parsers.interfaces.WordsBusinessRules;
import resolvit.paragraphparser.storage.interfaces.JsonStorage;

@Component
public class ParagraphHelper {

    @Autowired
    private MainConfiguration mainConfiguration;
    @Autowired
    private ConfigurationsParser configurationsParser;
    @Autowired
    private WordsBusinessRules wordsRules;
    @Autowired
    private JsonStorage jsonStorage;

    public String processParagraphFromConfiguration(){

        Collection<JSONObject> finalOutput = new ArrayList<JSONObject>();
        Integer occurrences = 0;
        List<Integer> positions = new ArrayList<Integer>();
        List<String> rawWords = wordsRules.parseAllParagraphWords(mainConfiguration.getParagraph());
        List<String> wordsDB=new ArrayList<String>();
        List<String> sentences=configurationsParser.parseParagraphSentences(mainConfiguration.getParagraph());
        for(String word:rawWords)
            wordsDB.add(wordsRules.cleanGrammarChars(word).toLowerCase());


        wordsDB = wordsRules.excludeWords(wordsDB, configurationsParser.parseWordsToExclude(mainConfiguration.getWordsToExclude()));

        wordsDB = wordsRules.excludeSameMeaningWords(wordsDB, configurationsParser.parseWordsToExclude(mainConfiguration.getWordsToExclude()));

        for(String word:wordsDB){
            occurrences = wordsRules.calculateOccurrences(word,
                                                        wordsDB,
                                                        configurationsParser.parsePluralEndingRules(
                                                        mainConfiguration.getPluralEndingRules()));
            positions= wordsRules.findPositions(word, sentences,
                                                        configurationsParser.parsePluralEndingRules(
                                                        mainConfiguration.getPluralEndingRules()));

            JSONObject wordRecord = jsonStorage.createWordRecord(word, occurrences, positions);
            finalOutput.add(wordRecord);

        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();

        JsonElement jsonElement = jsonParser.parse(finalOutput.toString());
        return gson.toJson(jsonElement);
    }


}
