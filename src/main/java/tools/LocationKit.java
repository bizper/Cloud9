package tools;

import bean.Loc;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import constant.CONSTANT;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationKit {

    private static final String request_url = "https://search.heweather.net/find";

    private static final ArrayList<Loc> list = new ArrayList<>();

    public static JSONObject get(String loc) {
        Map<String, Object> map = new HashMap<>();
        map.put("location", loc);
        map.put("key", CONSTANT.web_key);
        map.put("group", "cn");
        map.put("number", 5);
        return JSON.parseObject(HttpKit.get(request_url, map));
    }

    public static List<Loc> check(String loca) {
        List<Loc> lis = new ArrayList<>();
        System.out.println("loca = [" + loca + "]");
        for(Loc loc : list) {
            if(loc.getLocation().startsWith(loca)) lis.add(loc);
            if(lis.size() >= 5) {
                System.out.println("lis = " + lis);
                return lis;
            }
        }
        System.out.println("lis = " + lis);
        return lis;
    }

    public static void load() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(LocationKit.class.getResourceAsStream("/csv/china-city-list.csv")));
            String msg;
            while((msg = br.readLine()) != null) {
                String[] box = msg.split(",");
                String cid = box[0];
                String location = box[2];
                String cnty = box[5];
                String admin_area = box[7];
                String parent_city = box[9];
                double lat = Double.valueOf(box[10]);
                double lon = Double.valueOf(box[11]);
                list.add(new Loc(cid, location, parent_city, admin_area, cnty, lat, lon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
