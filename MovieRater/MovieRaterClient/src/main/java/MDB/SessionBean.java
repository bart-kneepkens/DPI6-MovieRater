/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDB;

import Domain.CrawlResult;
import Domain.QueryResult;
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
    ResultService service;
    
    public SessionBean(){}
    
    public void btnPressed(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String in = ec.getRequestParameterMap().get("inputForm:in");
        
        
        QueryResult qr = new QueryResult();
        qr.setQuery(in);
        service.getResults().add(0,qr);
        
        
        String messageBody = factory.getMessageBody(in);
        dispatcher.dispatchMessage(messageBody);
    }

    public List<QueryResult> getResults() {
        List lijst = service.getResults();
        return service.getResults();
    }
    
    public String calculateAverage(QueryResult qr){
        double totalRating = 0;
        int totalVotes = 0;
        
        for (CrawlResult cr : qr.getCrawlResults()) {
            if(cr.getRating() != -1){
                totalRating = totalRating + cr.getRating() * cr.getWeight();
                totalVotes = totalVotes + cr.getWeight();
            } 
        }
        
        double calculatedRating = totalRating / totalVotes;
        
        return String.valueOf(calculatedRating).substring(0, 3) + " by " + totalVotes + " voters";
    }
}