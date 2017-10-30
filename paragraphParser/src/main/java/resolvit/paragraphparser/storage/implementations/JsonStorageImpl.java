package resolvit.paragraphparser.storage.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import resolvit.paragraphparser.storage.interfaces.JsonStorage;

@Component
public class JsonStorageImpl implements JsonStorage {

    @Override
    public JSONObject createWordRecord(String word, Integer occurrences, List<Integer> positions) {
        JSONObject wordRecord = new JSONObject();
        wordRecord.append("word", word);
        wordRecord.append("total-occurrances", String.valueOf(occurrences));
        wordRecord.append("sentence-indexes", positions);
        return wordRecord;
    }
}
