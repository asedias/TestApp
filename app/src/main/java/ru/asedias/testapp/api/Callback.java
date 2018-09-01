package ru.asedias.testapp.api;

import org.json.JSONObject;

/**
 * Created by rorom on 11.04.2018.
 */

public class Callback<I> {
    public void onError(Exception e) {}
    public void onBackground(JSONObject document) {}
    public void onUIThread(I document) {}
}
