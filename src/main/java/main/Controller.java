package main;

import com.alibaba.fastjson.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import tools.ImageKit;
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

    @FXML
    private ImageView weather_icon;

    @FXML
    private TextField loc_input;

    void init() {
        flush();
    }

    private void flush() {
        JSONObject t = WeatherKit.get();
        render(t);
    }

    private void flush(String location) {
        JSONObject t = WeatherKit.get(location);
        render(t);
    }

    private void render(JSONObject t) {
        JSONObject basic = t.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("basic");
        JSONObject now = t.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now");
        JSONObject update = t.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("update");
        String status = t.getJSONArray("HeWeather6").getJSONObject(0).getString("status");
        if(status.equals("ok")) {
            loc.setText(basic.getString("location"));
            loc_input.setText(basic.getString("location"));
            max_tem.setText(now.getString("tmp") + "°C");
            fl_tem.setText(now.getString("fl")  + "°C");
            update_time.setText(update.getString("loc"));
            hum.setText(now.getString("hum") + "%");
            wind_spd.setText(now.getString("wind_spd") + " km/h");
            cond_txt.setText(now.getString("cond_txt"));
            weather_icon.setImage(ImageKit.get(now.getString("cond_code")));
        } else {
            System.err.println("远程服务器数据出现问题！" + status);
        }
    }

    @FXML
    void query(ActionEvent event) {
        String str = loc_input.getText();
        if(str == null || str.isEmpty()) str = "auto_ip";
        flush(str);
    }

}
