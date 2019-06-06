package main;

import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tools.WeatherKit;

public class Controller {

    @FXML
    private Label loc;

    @FXML
    private Label max_tem;

    @FXML
    private Label fl_tem;

    @FXML
    private Label update_time;

    @FXML
    private Label hum;

    @FXML
    private Label wind_spd;

    @FXML
    private Label cond_txt;

    public void init() {
        JSONObject t = WeatherKit.get();
        JSONObject basic = t.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("basic");
        JSONObject now = t.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now");
        JSONObject update = t.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("update");
        String status = t.getJSONArray("HeWeather6").getJSONObject(0).getString("status");
        if(status.equals("ok")) {
            loc.setText(basic.getString("location"));
            max_tem.setText(now.getString("tmp") + "°C");
            fl_tem.setText(now.getString("fl")  + "°C");
            update_time.setText(update.getString("loc"));
            hum.setText(now.getString("hum") + "%");
            wind_spd.setText(now.getString("wind_spd") + " km/h");
            cond_txt.setText(now.getString("cond_txt"));
        } else {
            System.err.println("远程服务器数据出现问题！");
        }
    }

}
