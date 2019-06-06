package main;

import bean.Loc;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tools.ImageKit;
import tools.LocationKit;
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
    private ChoiceBox<Loc> hidden_select;

    void init() {
        hidden_select.getSelectionModel().selectedIndexProperty().addListener((ob, o, n) -> {
            if(n.intValue() <= 0) return;
            loc_input.setText(hidden_select.getItems().get(n.intValue()).getLocation());
        });
        loc_input.textProperty().addListener((ob, o, n) -> {
            if(loc_input.getText().length() >= 1) {
                JSONObject jo = LocationKit.get(loc_input.getText());
                JSONArray ja = jo.getJSONArray("HeWeather6").getJSONObject(0).getJSONArray("basic");
                if(ja == null) return;
                hidden_select.getItems().clear();
                for(JSONObject in : ja.toJavaList(JSONObject.class)) {
                    String cid = in.getString("cid");
                    String location = in.getString("location");
                    String parent_city = in.getString("parent_city");
                    String admin_area = in.getString("admin_area");
                    String cnty = in.getString("cnty");
                    double lat = in.getDouble("lat");
                    double lon = in.getDouble("lon");
                    hidden_select.getItems().add(new Loc(cid, location, parent_city, admin_area, cnty, lat, lon));
                }
                hidden_select.show();
            }
        });
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
