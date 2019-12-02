package tools;

import com.alibaba.fastjson.JSONObject;
import constant.CONSTANT;

import java.util.HashMap;
import java.util.Map;

public class LocationKit {

    private static final String request_url = "https://search.heweather.net/find";

    public static JSONObject get(String loc) {
        Map<String, Object> map = new HashMap<>();
        map.put("location", loc);
        map.put("key", CONSTANT.web_key);
        map.put("group", "cn");
        map.put("number", 5);
        return HttpKit.getAsJSON(request_url, map);
    }

}
