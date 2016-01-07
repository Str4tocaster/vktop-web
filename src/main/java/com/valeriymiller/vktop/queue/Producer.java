package com.valeriymiller.vktop.queue;

import javax.jms.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Михаил on 20.12.2015.
 */
public class Producer {
    private static ActiveMQHandler activeMQHandler = new ActiveMQHandler();
    private static MessageProducer producer;

    public Producer(String queueName){
        activeMQHandler.setQueue(queueName);
        if (activeMQHandler.Connected()){
            activeMQHandler.setDestination();
            if (activeMQHandler.getDestination() != null){
                try {
                    this.producer = activeMQHandler.getSession().createProducer(activeMQHandler.getDestination());
                    this.producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                } catch (JMSException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void sendTextMessage(String result) throws JMSException {
        TextMessage message = activeMQHandler.getSession().createTextMessage(String.valueOf(result));
        producer.send(message);
    }

    public  void sendObjectMessage(String filterTop) throws JMSException {
        ObjectMessage message = activeMQHandler.getSession().createObjectMessage();
        try {
            message.setObject(filterTop);
            producer.send(message);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
