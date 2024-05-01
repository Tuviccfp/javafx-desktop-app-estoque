package org.desafio.demoteste;

import Presences.TokenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    protected void realizarLogin() {
        String email = this.email.getText();
        String password = this.password.getText();
        String token = "";
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://192.168.0.108:8080/api/register/login");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(requestBody.toString()));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                JSONObject responseJsonObject = new JSONObject(responseBody);
                token = responseJsonObject.getString("acessToken");
                TokenManager.saveToken(token);
                welcomeText.setText("Olá {email}, seu token é: " + TokenManager.getToken());
            } else {
                welcomeText.setText("Falha ao realizar o login");
            }
        }
        catch (IOException e) {
            welcomeText.setText("Erro ao se conectar ao servidor");

            System.out.println(token);
        }
    }
}