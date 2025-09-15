package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: return as a JSON object
    JSONObject toJson();
    
}
