/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDB;

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
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "MovieRater.Reply"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ClientMDB implements MessageListener
{
    
    @Inject
    Service service;
    
    public ClientMDB() {
    }
    
    @Override
    public void onMessage(Message message) {
        System.out.println("RECEIVED A MESSAGE CLIENT-SIDE:");
        try {
            TextMessage t = (TextMessage) message;
            
            System.out.println(t.getText());
            
            service.addResult(t.getText());
        } catch (JMSException ex) {
            Logger.getLogger(ClientMDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
