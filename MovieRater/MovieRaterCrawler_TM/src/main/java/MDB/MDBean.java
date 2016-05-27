/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package MDB;

import Crawler.CrawlerBean;
import Messages.MessageFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author BartKneepkens
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "MovieRater.Request"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "MovieRater.Request"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "MovieRater.Request"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class MDBean implements MessageListener
{
    @Inject
    CrawlerBean crawler;
    
    @Inject
    MessageFactory factory;
    
    public MDBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        TextMessage t = (TextMessage) message;
        
        String q = factory.getQueryFromRequestMessageBody(t);
        System.out.println("TM - Received Query: " + q);
        
        crawler.crawl(q);
    }
    
}
