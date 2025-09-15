package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Card;
import model.Handler;
import model.Set;

// Represents a reader that reads flashcards from JSON data stored in file 
// adapted from a reference file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader that can read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: reads the handler from the source file, and returns it
    //          if there is an error while reading data from file, throws IOException
    public Handler read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHandler(jsonObject);
    }

    // EFFECTS: parses handler from JSON object and returns it
    private Handler parseHandler(JSONObject jsonObject) {
        Handler handler = new Handler();
        addSets(handler, jsonObject);
        return handler; // stub
    }


    // MODIFIES: handler
    // EFFECTS: parses sets from JSON object and adds them to the handler
    private void addSets(Handler handler, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sets");
        for (Object json : jsonArray) {

            JSONObject setJson = (JSONObject) json;
            String name = setJson.getString("name");
            
            handler.newSet(name);
            Set set = handler.getSet(name);
            addCards(set, setJson);
        }
    }

    // MODIFIES: handler
    // EFFECTS: parses cards from JSON object and adds to the set
    private void addCards(Set set, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject obj = (JSONObject) json;
            String question = obj.getString("question");
            String answer = obj.getString("answer");
            String photo = obj.getString("photo");
            Boolean mastery = obj.getBoolean("mastery");
    
            Card card = new Card(question, answer);
            card.changePhoto(photo);
            if (mastery == true) {
                card.mastered();
            }
    
            set.addToSet(card);
        }
        
    }
}
