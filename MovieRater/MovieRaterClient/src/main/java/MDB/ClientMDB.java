/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDB;

import Service.ResultService;
import Domain.CrawlResult;
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
import javax.json.JsonObject;

/**
 *
 * @author BartKneepkens
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "MovieRater.Reply"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ClientMDB implements MessageListener
{
    
    @Inject
    ResultService service;
    
    @Inject
    MessageFactory factory;
    
    public ClientMDB() {
    }
    
    @Override
    public void onMessage(Message message) {
        System.out.println("RECEIVED A MESSAGE CLIENT-SIDE:");
        try {
            TextMessage t = (TextMessage) message;
            
            System.out.println(t.getText());
            
            JsonObject ob = factory.fromString(t.getText());
            
            service.addResult(new CrawlResult(ob));
           
        } catch (JMSException ex) {
            Logger.getLogger(ClientMDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
