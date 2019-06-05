package main;

import com.alibaba.fastjson.JSONObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tools.WeatherKit;

public class Controller {

    @FXML
    private Label loc;

    @FXML
    private Label max_tem;

    public void init() {
        WeatherKit.get((t) -> {
            JSONObject basic = t.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("basic");
            JSONObject now = t.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now");
            Platform.runLater(() -> {
                loc.setText(basic.getString("location"));
                max_tem.setText(now.getString("tmp"));
            });
        });
    }

}
