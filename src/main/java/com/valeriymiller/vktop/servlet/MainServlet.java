package com.valeriymiller.vktop.servlet;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.valeriymiller.vktop.api.db.DBApi;
import com.valeriymiller.vktop.model.User;
import project.FilterTop;
import project.TopResult;
import com.valeriymiller.vktop.queue.Consumer;
import com.valeriymiller.vktop.queue.Producer;

import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by valer on 02.12.2015.
 */
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // получаем id пользователя из cookie
        Integer userId = null;
        Cookie cookie = null;
        Cookie[] cookies = null;

        cookies = req.getCookies();

        if( cookies != null ) {
            for (int i = 1; i < cookies.length; i++) {
                cookie = cookies[i];
                userId = Integer.valueOf(cookie.getValue());
            }
        }

        // получаем параметры запроса
        String type = req.getParameter("topType");
        int topType = 1;
        if (type != null) {
           topType = Integer.valueOf(type);
        }

        String size = req.getParameter("topSize");
        int topSize = 10;
        if (size != null) {
            topSize = Integer.valueOf(size);
        }

        String sex = req.getParameter("topSex");
        int topSex = 0;
        if (sex != null) {
            topSex = Integer.valueOf(sex);
        }

        String age = req.getParameter("topAge");
        int topAge = 0;
        if (age != null && !age.equals("")) {
            topAge = Integer.valueOf(age);
        }

        // получаем выбранную дату
        String topDate = req.getParameter("topDate");
        // или получаем текущую дату, если дата не выбрана
        if (topDate == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            topDate = sdf.format(new Date());
        }

        // если требуется информация для другого пользователя
        String specialId = req.getParameter("specialId");
        if (specialId != null && !specialId.equals("")) {
            // проверяем есть ли пользователь в базе
            List specialUsers = DBApi.getUser(specialId);
            if (!specialUsers.isEmpty()) {
                User specialUser = (User) specialUsers.get(0);
                // изменяем id пользователя на необходимого
                userId = specialUser.getId();
            }
        }

        // если пользователь авторизован
        if (userId != null) {
            // посылаем в очередь сообщение с просьбой составить топ
            XStream xstream = new XStream(new StaxDriver());
            Producer producer = new Producer("ToTop");
            FilterTop filterTop = new FilterTop(userId, topSex, topAge, topSize, topType, topDate);
            try {
                producer.sendObjectMessage(xstream.toXML(filterTop));
            } catch (JMSException e) {
                e.printStackTrace();
            }

            // ожидаем из очереди сообщение с составленным топом
            Consumer consumer = new Consumer("Result");
            TopResult topResult = null;
            try {
                topResult = consumer.receivedMessage();
            } catch (JMSException e) {
                e.printStackTrace();
            }

            // возвращаем топ
            req.setAttribute("user", userId);
            req.setAttribute("positions", topResult.getResult());

        } else {
            // в этом else должна быть страница со стандартным топом или заглушка
            // userId обязательно оставить null в этом случае отобразиться кнопка "login"
            req.setAttribute("user", userId);
            req.setAttribute("output", "вы не авторизованы");
        }

        req.setAttribute("topType", topType);
        req.setAttribute("topSize", topSize);
        req.setAttribute("topDate", topDate);
        req.setAttribute("topSex", topSex);
        req.setAttribute("topAge", topAge);
        req.setAttribute("specialId", specialId);
        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
