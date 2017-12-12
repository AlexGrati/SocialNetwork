package it.grati_alexandru.socialnetwork.Utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by utente4.academy on 06/12/2017.
 */

public class FirebaseRestRequests {
    public static final String BASE_URL = "https://socialnetwork-490d3.firebaseio.com/";
    private static final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    private FirebaseRestRequests(){}

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        asyncHttpClient.get(BASE_URL + url + ".json", params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        asyncHttpClient.post(BASE_URL+url+".json",params,responseHandler);
    }
}
