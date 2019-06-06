package tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import constant.CONSTANT;

import java.util.HashMap;
import java.util.Map;

public class WeatherKit {

    private static final String request_url = "https://free-api.heweather.net/s6/weather/now";

    public static JSONObject get() {
        return get("auto_ip");
    }

    public static JSONObject get(String location) {
        Map<String, Object> map = new HashMap<>();
        map.put("location", location);
        map.put("key", CONSTANT.web_key);
        return JSON.parseObject(HttpKit.get(request_url, map));
    }

}
