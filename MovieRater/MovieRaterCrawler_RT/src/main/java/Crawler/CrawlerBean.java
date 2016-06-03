/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Crawler;

import Domain.Rating;
import Messages.MessageDispatcher;
import Messages.MessageFactory;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;

/**
 *
 * @author BartKneepkens
 */
@Stateless
@Named
public class CrawlerBean
{
    @Inject
    MessageFactory factory;
    
    @Inject
    MessageDispatcher dispatcher;
    
    private static final String url = "http://www.rottentomatoes.com/";
    
    // No-Arg constructor.
    public CrawlerBean(){};
    
    public void crawl(String query){
        String id = getID(query);
        Rating rating = getRating(id);
        
        String messageBody;
        
        if(rating == null){
            messageBody = factory.notFoundMessageBody();
        }
        else{
            messageBody = factory.getMessageBody(rating);
        }
        
        //System.out.println(messageBody);

        // Dispatch the message
        dispatcher.dispatchMessage(messageBody);
    }
    
    public String getID(String query){
        
        String finalUrl = url + "search/?search=";
        String id = null;
        
        for (String word : query.split(" ")) {
            finalUrl = finalUrl + word + "+";
        }
        
        finalUrl = finalUrl.substring(0, finalUrl.length() - 1);
        
        Document doc;
        try {
            doc = Jsoup.connect(finalUrl).get();
            Element firstSearchResult = doc.select("div[class=nomargin media-heading bold]").first();
            
            String fullHTML = firstSearchResult.html();
            
            
            id = fullHTML.substring(fullHTML.indexOf("m/"), fullHTML.indexOf('"', fullHTML.indexOf("/m/")));
        } catch (IOException ex) {
//            Logger.getLogger(CrawlerBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.StringIndexOutOfBoundsException | NullPointerException exc ){
            
        }
        
        return id;
    }
    
    public Rating getRating(String Id){
        Rating r = null;
        
        String pageUrl = url  + Id;
        
        try {
            Document doc = Jsoup.connect(pageUrl).get();
            
            Element ratingValue = doc.select("div[class=audience-info hidden-xs superPageFontColor]").first();
            //Element weightValue = doc.select("span[itemprop=ratingCount]").first();
            
            String htm = ratingValue.html();
            
            int rindex = htm.indexOf("/5");
            
            // Default, Rotten tomatoes displays rating /5
            // Double this value to get rating /10, like the rest of the crawlers
            String rating = htm.substring(rindex - 3, rindex);
            
            double rad = Double.parseDouble(rating) * 2;
            
            String qu = "User Ratings: </span>";
            
            int aindex = htm.indexOf(qu) + qu.length();
            int endindex = htm.indexOf("</div", aindex);
            
            String amount = htm.substring(aindex + 1, endindex);
           
            int wi = NumberFormat.getNumberInstance(java.util.Locale.US).parse(amount).intValue();
            
            r = new Rating(rad, wi);
            
        } catch (IOException | NumberFormatException | Selector.SelectorParseException | ParseException ex) {
        }
        
        return r;
    }
    
}
