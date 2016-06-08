/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Service.ResultService;
import Domain.CrawlResult;
import Domain.EnrichedRating;
import Domain.QueryResult;
import Messages.MessageDispatcher;
import Messages.MessageFactory;
import Service.RatingService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RateEvent;

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
    ResultService resultService;
    
    @Inject
    RatingService ratingService;
    
    private int lastRating;

    public int getLastRating() {
        return lastRating;
    }

    public void setLastRating(int lastRating) {
        this.lastRating = lastRating;
    }
    
    public void onrate(RateEvent rateEvent) {
        // Save To DB
        //get latest rating
        QueryResult result = resultService.getResults().get(0);
        
        EnrichedRating er = new EnrichedRating();
        er.setQueryResult(result);
        er.setUserRating(lastRating);
        
        ratingService.addRating(er);
        
        FacesContext context = FacesContext.getCurrentInstance();  
        context.addMessage(null, new FacesMessage("",  "Your rating has been saved to the database! ") );
        
        resultService.getResults().remove(0);
        lastRating = 0;
    }
    
    public SessionBean(){}
    
    public void btnPressed(){
        
        this.setLastRating(0);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String in = ec.getRequestParameterMap().get("inputForm:in");
        
        
        QueryResult qr = new QueryResult();
        qr.setQuery(in);
        resultService.getResults().add(0,qr);
        
        
        String messageBody = factory.getMessageBody(in);
        dispatcher.dispatchMessage(messageBody);
    }

    public List<QueryResult> getResults() {
        return resultService.getResults();
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
    
    public List<EnrichedRating> getRatingHistory(){
        return ratingService.getHistory();
    }
}