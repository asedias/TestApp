package ru.asedias.testapp.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rorom on 01.09.2018.
 */

public class GetEventInfo extends APIRequest<GetEventInfo.EventInfo> {
    public GetEventInfo(String article) {
        super("post.php?article=" + article);
    }

    @Override
    protected EventInfo parse(JSONObject response) {
        EventInfo info = new EventInfo();
        info.team1 = response.optString("team1");
        info.team2 = response.optString("team2");
        info.time = response.optString("time");
        info.tournament = response.optString("tournament");
        JSONArray articlesArray = response.optJSONArray("article");
        for(int i = 0; i < articlesArray.length(); i++) {
            JSONObject articleInfo = articlesArray.optJSONObject(i);
            String[] article = new String[] {articleInfo.optString("header"), articleInfo.optString("text")};
            info.articles.add(article);
        }
        info.prediction = response.optString("prediction");
        return info;
    }

    public class EventInfo {
        public String team1;
        public String team2;
        public String time;
        public String tournament;
        public ArrayList<String[]> articles = new ArrayList<>();
        public String prediction;
    }
}
