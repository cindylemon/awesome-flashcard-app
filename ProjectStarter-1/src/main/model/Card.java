package model;

import org.json.JSONObject;

import persistence.Writable;

public class Card implements Writable {

    String question;
    String answer;
    String photo;
    Boolean mastery;

    // Creates a new card with a question and answer, not mastered
    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        photo = "";
        mastery = false;
    }

    // MODIFIES: this
    // EFFECTS: changes the question attatched to the flashcard to the parameter
    public void changeQuestion(String question) {
        this.question = question;
		EventLog.getInstance().logEvent(new Event("Question changed to: " + question));
    }

    // MODIFIES: this
    // EFFECTS: changes the answer attatched to the card to the parameter
    //          currently I can't put in actual photos, so it'll just be a link
    public void changeAnswer(String answer) {
        this.answer = answer;
        EventLog.getInstance().logEvent(new Event("Answer changed to: " + answer));
    }

    // MODIFIES: this
    // EFFECTS: changes the photo to the guy
    public void changePhoto(String photo) {
        this.photo = photo;
        EventLog.getInstance().logEvent(new Event("Photo changed to: " + photo));
    }

    // MODIFIES: this
    // EFFECTS: switches the mastery from mastered to not/not to mastered
    public void toggleMastery() {
        if (getMastery() == true) {
            notMastered();
        } else {
            mastered();
        }
        EventLog.getInstance().logEvent(new Event("Mastery set to : " + mastery));
    }
    
    // MODIFIES: this
    // EFFECTS: sets this card to mastered (doesn't run in the set)
    public void mastered() {
        mastery = true;
    }

    // MODIFIES: this
    // EFFECTS: sets this card to not mastered (runs in the set)
    public void notMastered() {
        mastery = false;
    }

    public String getQuestion() {
        return question;
    }  

    public String getAnswer() {
        EventLog.getInstance().logEvent(new Event("Card '" + question  + "'  answer shown"));
        return answer;
    }

    public String getPhoto() {
        return photo;
    }

    public Boolean getMastery() {
        return mastery;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", question);
        json.put("answer", answer);
        json.put("photo", photo);
        json.put("mastery", mastery);
        return json;
    }
}
