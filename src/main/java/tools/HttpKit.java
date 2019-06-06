package tools;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class HttpKit {

    public static String get(String url, Map<String, String> param) {
        CloseableHttpClient client;
        CloseableHttpResponse res;
        try {
            client = HttpClients.createDefault();
            StringBuilder stringBuilder = new StringBuilder();
            param.forEach((k, v) -> stringBuilder.append(k).append("=").append(v).append("&"));
            url += "?" + stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
            HttpGet get = new HttpGet(url);
            res = client.execute(get);
            return EntityUtils.toString(res.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
