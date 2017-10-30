package resolvit.paragraphparser.parsers.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import resolvit.paragraphparser.parsers.interfaces.WordsBusinessRules;

@Component
public class WordsBusinessRulesImpl implements WordsBusinessRules {

    @Override
    public List<String> parseAllParagraphWords(String paragraph) {
        return Arrays.asList(paragraph.split(" "));
    }

    @Override
    public List<String> excludeWords(List<String> paragraphWords, List<String> wordsToExclude) {
        //This code isn't so clean because the complexity of removing elements in a list in runtime
        //Maybe with some additional time, there exist a better and cleaner way. ML
        Boolean wordRemoved=true;
        while(wordRemoved){
            wordRemoved=false;
            for(int x=0;x<paragraphWords.size();x++){
                if(wordRemoved)
                    break;
                for(String wordToExclude:wordsToExclude){
                    if(paragraphWords.get(x).equals(wordToExclude)||paragraphWords.get(x).isEmpty()){
                        paragraphWords.remove(x);
                        wordRemoved=true;
                        break;
                    }
                }
            }
        }

        return paragraphWords;
    }

    @Override
    public List<String> excludeSameMeaningWords(List<String> paragraphWords, List<String> pluralEndingRules) {
        List<String> wordsToReturn= new ArrayList<String>();
        for(String pluralEnding:pluralEndingRules){
            for(int x=0;x<paragraphWords.size();x++){
                wordsToReturn.add(paragraphWords.get(x));
                Iterator<String> iterator = paragraphWords.iterator();
                while(iterator.hasNext()){
                    String word=iterator.next();
                    if(paragraphWords.get(x).toLowerCase().equals(String.format("%s%s",word.toLowerCase(),pluralEnding.toLowerCase()))){
                       iterator.remove();
                    }
                }
            }
        }
        paragraphWords=paragraphWords.stream().distinct().collect(Collectors.toList());
        return paragraphWords;
    }



    @Override
    public List<String> sortWords(List<String> paragraphWords) {
        Collections.sort(paragraphWords);
        return paragraphWords;
    }

    @Override
    public String cleanGrammarChars(String word) {
        String cleanWord = word.replace(",", "");
        cleanWord = cleanWord.replace(".", "");
        cleanWord = cleanWord.replace(",", "");
        cleanWord = cleanWord.replace(":", "");
        cleanWord = cleanWord.replace(";", "");
        cleanWord = cleanWord.replace("\"", "");
        return cleanWord;
    }

    @Override
    public Integer calculateOccurrences(String word, List<String> paragraphWords, List<String> pluralEndingRules) {
        Integer occurrences=0;
        for(String wordInDB:paragraphWords){
                if(word.toLowerCase().equals(wordInDB.toLowerCase()))
                    occurrences++;
                for(String pluralEnding:pluralEndingRules){
                    if(wordInDB.toLowerCase().equals(String.format("%s%s",word.toLowerCase(),pluralEnding.toLowerCase())))
                        occurrences++;
                }
        }
        return occurrences;
    }

    @Override
    public List<Integer> findPositions(String word, List<String> sentences, List<String> pluralEndingRules) {
        List<Integer> positions = new ArrayList<Integer>();
        List<String> wordsInSentence=new ArrayList<String>();
        for(String sentence:sentences){
            wordsInSentence=Arrays.asList(sentence.split(" "));
            for(int x=0; x<wordsInSentence.size();x++){
                if(word.toLowerCase().equals(wordsInSentence.get(x).toLowerCase()))
                    positions.add(x+1);
                for(String pluralEnding:pluralEndingRules){
                    if(wordsInSentence.get(x).toLowerCase().equals(String.format("%s%s",word.toLowerCase(),pluralEnding.toLowerCase())))
                        positions.add(x+1);
                }
            }
        }
        return positions;
    }

}
