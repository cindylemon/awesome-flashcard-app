package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;
import ui.Output;

public class Handler implements Writable {
    List<Set> sets;
    
    // Handles the flashcard app (holding the sets)
    public Handler() {
        sets = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new set to the app
    public void newSet(String name) {
        Set newSet = new Set(name);
        sets.add(newSet);
        EventLog.getInstance().logEvent(new Event("New set named " + name + " created"));
    }

    // EFFECTS: finds a set with given name or null
    public Set getSet(String name) {
        for (Set set : sets) {
            if (set.getName().equals(name)) {
                return set;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: removes set from list of sets
    public void removeSet(Set set) {
        EventLog.getInstance().logEvent(new Event("Set named " + set.getName() + " removed"));
        sets.remove(set);
    }

    public List<Set> getSets() {
        return sets;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sets", setsToJson());
        return json;
    }

    private JSONArray setsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Set s : sets) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }
}
