package ru.asedias.testapp.api;

import android.os.AsyncTask;
import org.json.JSONObject;

/**
 * Created by rorom on 19.05.2018.
 */

public class APIRequest<I> {

    private API mRequest;
    private Callback<I> mCallback;

    public APIRequest(String method) {
        this.mRequest = new API(method).setCallback(new Callback<JSONObject>() {
            I obj;
            @Override
            public void onBackground(JSONObject document) {
                obj = parse(document);
            }

            @Override
            public void onError(Exception e) {
                mCallback.onError(e);
            }

            @Override
            public void onUIThread(JSONObject document) {
                mCallback.onUIThread(obj);
            }
        });
    }

    public APIRequest setCallback(Callback mCallback) {
        this.mCallback = mCallback;
        return this;
    }


    public void cancel() {
        if(!this.mRequest.isCancelled() && this.mRequest.getStatus() != AsyncTask.Status.FINISHED) this.mRequest.cancel(false);
    }

    public void run() {
        this.mRequest.execute();
    }

    protected I parse(JSONObject response) {
        return null;
    }

}
