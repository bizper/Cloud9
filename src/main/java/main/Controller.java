package main;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    @FXML
    private Label pcpn;

    @FXML
    private Label wind_deg;

    @FXML
    private Label vis;

    @FXML
    private Label pres;

    @FXML
    private Label cloud;

    @FXML
    private Label parent;

    @FXML
    private LineChart<String, Number> forecast;

    @FXML
    private ImageView qrcode;

    @FXML
    private TextArea qrtext;

    void init() {
        forecast.setCreateSymbols(true);
        Axis<String> axis =  forecast.getXAxis();
        axis.setAutoRanging(true);
        flush();
    }

    private void flush() {
        JSONObject t = WeatherKit.getNow();
        render(t);
        JSONObject forecast = WeatherKit.getForecast();
        renderForecast(forecast);
    }

    private void flush(String location) {
        JSONObject basic = WeatherKit.getNow(location);
        render(basic);
        JSONObject forecast = WeatherKit.getForecast(location);
        renderForecast(forecast);
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
            parent.setText(basic.getString("admin_area"));
            update_time.setText(update.getString("loc"));
            hum.setText(now.getString("hum") + "%");
            wind_spd.setText(now.getString("wind_spd") + " km/h");
            cond_txt.setText(now.getString("cond_txt"));
            pcpn.setText(now.getString("pcpn"));
            pres.setText(now.getString("pres"));
            wind_deg.setText(now.getString("wind_dir"));
            vis.setText(now.getString("vis"));
            cloud.setText(now.getString("cloud"));
            weather_icon.setImage(ImageKit.get(now.getString("cond_code")));
        } else {
            System.err.println("远程服务器数据出现问题！" + status);
        }
    }

    private void renderForecast(JSONObject t) {
        forecast.getData().clear();
        JSONArray daily_forecast = t.getJSONArray("HeWeather6").getJSONObject(0).getJSONArray("daily_forecast");
        String status = t.getJSONArray("HeWeather6").getJSONObject(0).getString("status");
        if(status.equals("ok")) {
            XYChart.Series<String, Number> max = new XYChart.Series<>();
            max.setName("最高气温");
            for(JSONObject inside : daily_forecast.toJavaList(JSONObject.class)) {
                max.getData().add(new XYChart.Data<>(inside.getString("date"), Integer.parseInt(inside.getString("tmp_max"))));
            }
            XYChart.Series<String, Number> min = new XYChart.Series<>();
            max.setName("最低气温");
            for(JSONObject inside : daily_forecast.toJavaList(JSONObject.class)) {
                min.getData().add(new XYChart.Data<>(inside.getString("date"), Integer.parseInt(inside.getString("tmp_min"))));
            }
            forecast.getData().add(max);
            forecast.getData().add(min);
        } else {
            System.err.println("远程服务器数据出现问题！" + status);
        }
    }

    @FXML
    void qrparse(ActionEvent event) {
        qrtext.setText(ImageKit.parse(qrcode.getImage()));
    }

    @FXML
    void qrgen(ActionEvent event) {
        Image img = ImageKit.create(qrtext.getText());
        qrcode.setImage(img);
    }

    @FXML
    void query(ActionEvent event) {
        String str = loc_input.getText();
        if(str == null || str.isEmpty()) str = "auto_ip";
        flush(str);
    }

    @FXML
    void onEnter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            query(null);
        }
    }

}
