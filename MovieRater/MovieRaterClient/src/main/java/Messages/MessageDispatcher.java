/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 *
 * @author BartKneepkens
 */

@Singleton
public class MessageDispatcher implements Serializable
{
    @EJB
    private MessageDispatcher instance;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "MovieRater.Request")
    private Topic t;
    
    public MessageDispatcher() {
    }
    
    public void dispatchMessage(final String messageBody){
        instance.postToJMS(new Runnable()
        {
            @Override
            public void run() {
                try (JMSContext context = connectionFactory.createContext()) {
                    JMSProducer producer = context.createProducer();
                    producer.send(t, messageBody);
                    System.out.println("Client- Message dispatched ");
                }
                // JMScontext auto closes :)
            }
        }
        );
    }
    
    @Asynchronous
    public void postToJMS(Runnable r){
        r.run();
    }
    
}
