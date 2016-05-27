/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDB;

import Messages.MessageDispatcher;
import Messages.MessageFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author BartKneepkens
 */
@SessionScoped
@Named
@ManagedBean
public class SessionBean implements Serializable
{
    @Inject
    MessageFactory factory;
    
    @Inject
    MessageDispatcher dispatcher;
    
    @Inject
    Service service;
    
    public SessionBean(){}
    
    public void btnPressed(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String in = ec.getRequestParameterMap().get("inputForm:in");
        
        
        String messageBody = factory.getMessageBody(in);
        dispatcher.dispatchMessage(messageBody);
    }

    public List<String> getResults() {
        List lijst = service.getResults();
        return service.getResults();
    }
}
