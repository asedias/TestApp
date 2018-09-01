package ru.asedias.testapp.api;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rorom on 19.05.2018.
 */

public class API extends AsyncTask<Void, Void, Integer> {

    private Callback<JSONObject> mCallback;
    private String mUrlBase = "http://mikonatoruri.win/";
    private String mUrl;
    private String mMethod;
    public OkHttpClient client;
    public Request request;
    public Response response;
    public Request.Builder builder;
    private final int EXIT_SUCCESS = 0;
    private final int EXIT_ERROR = 1;
    private Exception errorException;

    public API(String mMethod) {
        this.mMethod = mMethod;
        this.mUrl = this.mUrlBase + this.mMethod;
        this.builder = new Request.Builder();
        this.client = new OkHttpClient();
    }

    @Override
    protected void onPreExecute() {
        if(mCallback == null) {
            throw new RuntimeException("Set Callback first: setCallback(Callback callback)");
        }
        if(mUrl.isEmpty()) {
            throw new RuntimeException("Url is Empty");
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        this.request = this.builder.url(this.mUrl).build();
        try {
            this.response = this.client.newCall(this.request).execute();
            String re = this.response.body().string();
            this.mCallback.onBackground(new JSONObject(re));
            response.close();
        } catch (Exception e) {
            errorException = e;
            e.printStackTrace();
            return EXIT_ERROR;
        }
        return EXIT_SUCCESS;
    }

    @Override
    protected void onPostExecute(Integer out) {
        if(out == EXIT_SUCCESS) {
            mCallback.onUIThread(new JSONObject());
        }
        if(out == EXIT_ERROR) {
            mCallback.onError(errorException);
        }
    }

    public API setCallback(Callback<JSONObject> callback) {
        this.mCallback = callback;
        return this;
    }
}
