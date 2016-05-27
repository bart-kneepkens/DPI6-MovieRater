/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import Domain.Rating;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author BartKneepkens
 */
@Stateless
public class MessageFactory
{
    public static final String CRAWLER_ID = "TM";
    public MessageFactory(){}
    
    public String getMessageBody(Rating r){
        JsonObject j = Json.createObjectBuilder()
                .add("rating", r.getRating())
                .add("weight", r.getWeight())
                .add("crawlerID", CRAWLER_ID)
                .build();
        
        return j.toString();
    }
    
    public String notFoundMessageBody(){
        JsonObject j = Json.createObjectBuilder()
                .add("rating", "NOT_FOUND")
                .add("weight", "NOT_FOUND")
                .add("crawlerID", CRAWLER_ID)
                .build();
        
        return j.toString();
    }
    
    // Purely Debug stuff
    public String getQueryFromRequestMessageBody(TextMessage m){
        String s = null;
        try {
            JsonReader jsonReader;
            jsonReader = Json.createReader(new StringReader(m.getText()));
            JsonObject j = jsonReader.readObject();
            s = j.getString("query");
            jsonReader.close();
        } catch (JMSException ex) {
            Logger.getLogger(MessageFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}
