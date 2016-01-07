package com.valeriymiller.vktop.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Михаил on 20.12.2015.
 */
public class ActiveMQHandler {
    private ActiveMQConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session;
    private Destination destination;

    public void setDestination() {
        this.destination = getDestinationQueue();
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    private String queue = null;

    public Destination getDestination() {
        return destination;
    }

    //Создаем ConnectionFactory ApacheMQ сервера. Вбиваем дефолтные логин и пароль.
    private ActiveMQConnectionFactory getConnectionFactory(){
        return new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "failover://tcp://localhost:61616");
    }

    //Подключаемся к модели точка-точка.
    private Destination getDestinationQueue(){
        try {
            return session.createQueue(queue);
        } catch (JMSException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Session getSession() {
        return session;
    }

    //Подключаемся к серверу.
    public Boolean Connected(){
        try {
            if (connection == null){
                connectionFactory = getConnectionFactory();
                connection=connectionFactory.createConnection(); //получаем экзмпляр класса подключения
                connection.start(); //стартуем
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); //создаем объект сессию без транзакций
                //параметром AUTO_ACKNOWLEDGE мы указали что отчет о доставке будет
                //отправляться автоматически при получении сообщения.
            }else{
                connection.start();
            }
            return true;
        } catch (JMSException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void close() {
        try {
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
