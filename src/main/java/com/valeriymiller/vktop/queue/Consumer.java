package com.valeriymiller.vktop.queue;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import project.TopResult;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Михаил on 20.12.2015.
 */
public class Consumer {
    private static ActiveMQHandler activeMQHandler = new ActiveMQHandler();
    private static MessageConsumer consumer;

    public Consumer(String queueName){
        activeMQHandler.setQueue(queueName);
        if (activeMQHandler.Connected()){
            activeMQHandler.setDestination();
            if (activeMQHandler.getDestination() != null){
                try {
                    this.consumer = activeMQHandler.getSession().createConsumer(activeMQHandler.getDestination());
                } catch (JMSException ex) {
                    Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static TopResult receivedMessage() throws JMSException {
        XStream xstream = new XStream(new StaxDriver());
        ObjectMessage message = (ObjectMessage)consumer.receive();
        TopResult topResult = (TopResult)xstream.fromXML(String.valueOf(message.getObject()));

        // закрываем соединение подписчика после получения данных
        consumer.close();
        return topResult;
    }
}
