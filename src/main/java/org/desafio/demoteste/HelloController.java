package org.desafio.demoteste;

import Presences.TokenManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    private void loadPage() throws IOException {
        Stage stage = new Stage();
        URL fxmlURL = getClass().getResource("/profile.fxml");
        if (fxmlURL == null) {
            throw new FileNotFoundException("Erro ao localizar o tela de login");
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(String.valueOf(fxmlURL))));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void realizarLogin() {
        String email = this.email.getText();
        String password = this.password.getText();
        String token = "";
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://192.168.0.109:8080/api/register/login");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(requestBody.toString()));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                JSONObject responseJsonObject = new JSONObject(responseBody);
                token = responseJsonObject.getString("acessToken");
                TokenManager.saveToken(token);
                loadPage();
//                welcomeText.setText("Olá {email}, seu token é: " + TokenManager.getToken());
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