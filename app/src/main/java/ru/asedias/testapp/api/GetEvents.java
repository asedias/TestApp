package ru.asedias.testapp.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetEvents extends APIRequest<ArrayList<GetEvents.Event>> {

    public GetEvents(String category) {
        super("list.php?category=" + category);
    }

    @Override
    protected ArrayList<Event> parse(JSONObject response) {
        ArrayList<Event> events = new ArrayList<>();
        JSONArray eventsJson = response.optJSONArray("events");
        for(int i = 0; i < eventsJson.length(); i++) {
            events.add(new Event(eventsJson.optJSONObject(i)));
        }
        return events;
    }

    public class Event {
        public String title;
        public String coefficient;
        public String time;
        public String place;
        public String preview;
        public String article;

        public Event(JSONObject info) {
            this.title = info.optString("title");
            this.coefficient = info.optString("coefficient");
            this.time = info.optString("time");
            this.place = info.optString("place");
            this.preview = info.optString("preview");
            this.article = info.optString("article");
        }
    }
}
