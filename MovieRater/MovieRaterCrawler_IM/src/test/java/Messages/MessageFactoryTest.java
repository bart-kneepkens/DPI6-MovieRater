/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import Domain.Rating;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author BartKneepkens
 */
public class MessageFactoryTest
{
    MessageFactory fac;
    public MessageFactoryTest() {
        fac = new MessageFactory();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testGetMessage(){
        Rating r = new Rating(4.5, 100);
        String result = fac.getMessageBody(r);
        
        System.out.println(result);
    }
}
