package resolvit.paragraphparser.storage.interfaces;

import java.util.Collection;
import java.util.List;
import org.json.JSONObject;

public interface JsonStorage {

    public JSONObject createWordRecord(String word, Integer occurrences, List<Integer> positions);

}
