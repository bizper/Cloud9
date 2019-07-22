package tools;

import com.alibaba.fastjson.JSONObject;
import constant.CONSTANT;

import java.util.HashMap;
import java.util.Map;

public class WeatherKit {

    private static final String request_url = "https://free-api.heweather.net/s6/weather/";

    public static JSONObject getNow() {
        return getNow("auto_ip");
    }

    public static JSONObject getNow(String location) {
        Map<String, Object> map = new HashMap<>();
        map.put("location", location);
        map.put("key", CONSTANT.web_key);
        return HttpKit.getAsJSON(request_url + "now", map);
    }

    public static JSONObject getForecast() {
        return getForecast("auto_ip");
    }

    public static JSONObject getForecast(String location) {
        Map<String, Object> map = new HashMap<>();
        map.put("location", location);
        map.put("key", CONSTANT.web_key);
        return HttpKit.getAsJSON(request_url + "forecast", map);
    }

}
