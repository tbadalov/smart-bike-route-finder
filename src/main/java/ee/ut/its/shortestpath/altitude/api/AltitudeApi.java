package ee.ut.its.shortestpath.altitude.api;

import com.google.gson.Gson;
import ee.ut.its.shortestpath.dock.api.DockListRequest;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AltitudeApi {

    public AltitudeApi(){

    }
    public AltitudeApi(OkHttpClient client) {
        this.client = client;
    }

    private OkHttpClient client;
    private final Gson gson = new Gson();
    private final Request listRequest = new Request.Builder()
            .url("https://api.open-elevation.com/api/v1/lookup?locations=58.380671,2026.75543712")

            .build();

    public String all() throws IOException {
        Response response = client.newCall(listRequest).execute();
        String apiResponse = response.body().string();
        System.out.println(apiResponse);
        return apiResponse;
    }
}
