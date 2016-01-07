package com.valeriymiller.vktop.servlet;

import com.valeriymiller.vktop.api.Api;
import com.valeriymiller.vktop.api.db.DBApi;
import com.valeriymiller.vktop.api.db.HibernateUtil;
import com.valeriymiller.vktop.model.Token;
import com.valeriymiller.vktop.model.User;
import com.valeriymiller.vktop.queue.Producer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.json.JSONObject;
import project.FilterTop;

import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valer on 02.12.2015.
 */

// логика авторизации

public class LoginServlet extends HttpServlet {
    String AppID = "5099050";
    String client_secret = "jykiEzsP5iai8bOUWYBJ";
    String redirect_uri = "http://localhost:8080/vktop/login";
    String scope = "groups,offline";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code != null) {
            String uri = String.format(
                    "https://oauth.vk.com/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s",
                    AppID,
                    client_secret,
                    redirect_uri,
                    code);

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(uri);
            HttpResponse response = null;

            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            String jsonResponce = convertStreamToString(inputStream);
            JSONObject jsonObject = new JSONObject(jsonResponce);
            String token = jsonObject.getString("access_token");
            User user = Api.getUserInfo(token);

            // проверяем, есть ли пользователь в базе
            List users = DBApi.getUser(user.getVkId());

            if (users.isEmpty()) { // если пользователя нет, помещаем его и токен в базу
                DBApi.saveUser(user);
                DBApi.saveToken(new Token(user.getId(), token));

                // добавляем в очередь заявку на обработку данных пользователя
                Producer producer = new Producer("CreateUser");
                try {
                    producer.sendTextMessage(String.valueOf(user.getId()));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            // записываем id пользователя в cookie
            user = (User) users.get(0);
            Cookie userId = new Cookie("userId", String.valueOf(user.getId()));
            resp.addCookie(userId);

            resp.sendRedirect("http://localhost:8080/vktop/main");
            return;
        }

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = "";

        if (req.getParameter("login") != null) {
            String uri = String.format(
                    "https://oauth.vk.com/authorize?client_id=%s&redirect_uri=%s&display=page&scope=%s&v=5.37",
                    AppID,
                    redirect_uri,
                    scope);

            resp.sendRedirect(uri);
            return;
        }

        req.setAttribute("message", message);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
