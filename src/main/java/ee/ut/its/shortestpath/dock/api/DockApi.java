package ee.ut.its.shortestpath.dock.api;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class DockApi {

    public DockApi(OkHttpClient client) {
        this.client = client;
    }

    private final OkHttpClient client;
    private final Gson gson = new Gson();
    private final Request request = new Request.Builder()
            .url("https://api.ratas.tartu.ee/cxf/am/station/map/search")
            .method("POST", RequestBody.create("{}", MediaType.get("application/json; charset=utf-8")))
            .header("Content-Type", "application/json")
            .build();


    public List<DockListRequest.DockResponse> all() throws IOException {
        Response response = client.newCall(request).execute();
        String apiResponse = response.body().string();
        DockListRequest dockListRequest = this.gson.fromJson(apiResponse, DockListRequest.class);
        return dockListRequest.getResults();
    }
}
