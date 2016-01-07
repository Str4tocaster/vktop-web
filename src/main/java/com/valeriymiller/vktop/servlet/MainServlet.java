package com.valeriymiller.vktop.servlet;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
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

        // получаем текущую дату
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String date = sdf.format(new Date());

        // если пользователь авторизован
        if (userId != null) {
            // посылаем в очередь сообщение с просьбой составить топ
            XStream xstream = new XStream(new StaxDriver());
            Producer producer = new Producer("ToTop");
            FilterTop filterTop = new FilterTop(userId, 2, 0, 10, 2);
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
            req.setAttribute("output", topResult.getResult());

        } else {
            // в этом else должна быть страница со стандартным топом или заглушка
            // userId обязательно оставить null в этом случае отобразиться кнопка "login"
            req.setAttribute("user", userId);
            req.setAttribute("output", "вы не авторизованы");
        }

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }

}
