package ru.stqa.pft.mantis.appmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpSession {
    private final CloseableHttpClient httpClient;
    private final ApplicationManager app;

    public HttpSession(ApplicationManager app) {
        this.app = app;
        httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    private boolean login_password(String username) throws IOException {
        HttpPost loginUserPost = new HttpPost(app.getProperty("web.baseUrl") + "/login_password_page.php");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        loginUserPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse loginUserResponse = httpClient.execute(loginUserPost);
        String loginUserResponseBody = getTextFrom(loginUserResponse);
        return loginUserResponseBody.contains(String.format("Enter password for '%s'\t\t\t", username));
    }

    public boolean login(String username, String password) throws IOException {
        if (!login_password(username)) {
            return false;
        }
        HttpPost loginPasswordPost = new HttpPost(app.getProperty("web.baseUrl") + "/login.php");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("perm_login", "on"));
        params.add(new BasicNameValuePair("secure_session", "on"));
        params.add(new BasicNameValuePair("return", "index.php"));
        loginPasswordPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse loginPasswordResponse = httpClient.execute(loginPasswordPost);
        String loginPasswordResponseBody = getTextFrom(loginPasswordResponse);
        return loginPasswordResponseBody
            .contains(String.format("<span class=\"user-info\">%s</span>", username));
    }

    private String getTextFrom(CloseableHttpResponse response) throws IOException {
        try {
            return EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
    }

    public boolean isLoggedInAs(String username) throws IOException {
        HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/account_page.php");
        CloseableHttpResponse response = httpClient.execute(get);
        String body = getTextFrom(response);
        return body.contains(String.format("<span class=\"user-info\">%s</span>",username));
    }
}
