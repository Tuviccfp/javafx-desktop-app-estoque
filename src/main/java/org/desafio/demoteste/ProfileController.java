package org.desafio.demoteste;

import Presences.TokenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ProfileController {
    @FXML
    private Label email;
    @FXML
    private Label name;
    @FXML
    private Label id;

    @FXML
    public void initialize() {
//        String email = this.email.getText();
//        String name = this.name.getText();
//        String id = this.id.getText();
//        JSONObject responseBody = new JSONObject();
//        responseBody.put("email", email);
//        responseBody.put("name", name);
//        responseBody.put("id", id);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet("http://192.168.0.109:8080/api/register/profile-data"); //Passar
            httpGet.setHeader("Authorization", "Bearer " + TokenManager.getToken());
            httpGet.getMethod();
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseExistEntity = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(responseExistEntity);
                email.setText(jsonObject.getString("email"));
                name.setText(jsonObject.getString("name"));
                id.setText(jsonObject.getString("id"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
