package tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import intf.Solution;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.Map;

public class WeatherKit {

    private static final String request_url = "https://free-api.heweather.net/s6/weather/now";

    private static final String web_key = "fd0c76e0bb724faabd7dbc614a582827";

    public static void get(Solution<JSONObject> s) {
        Platform.runLater(() -> {
            Map<String, String> map = new HashMap<>();
            map.put("location", "auto_ip");
            map.put("key", web_key);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s.action(JSON.parseObject(HttpKit.get(request_url, map)));
        });
    }

}
