package tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WeatherKit {

    private static final String request_url = "https://free-api.heweather.net/s6/weather/now";

    private static final String web_key = "fd0c76e0bb724faabd7dbc614a582827";

    public static JSONObject get() {
        Map<String, String> map = new HashMap<>();
        map.put("location", "auto_ip");
        map.put("key", web_key);
        return JSON.parseObject(HttpKit.get(request_url, map));
    }

}
