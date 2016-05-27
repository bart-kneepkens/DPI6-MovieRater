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
    
    private static final String url = "https://www.themoviedb.org";
    
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
        
        String finalUrl = url + "/search?query=";
        String id = null;
        
        for (String word : query.split(" ")) {
            finalUrl = finalUrl + word + "+";
        }
        
        finalUrl = finalUrl.substring(0, finalUrl.length() - 1);
        
        Document doc;
        try {
            doc = Jsoup.connect(finalUrl).get();
            Element firstSearchResult = doc.select("a[class=title result]").first();
            
            //String fullHTML = firstSearchResult.html();
            
            // It will throw java.lang.StringIndexOutOfBoundsException if not a movie or seris
            //id = fullHTML.substring(fullHTML.indexOf("tt"), fullHTML.indexOf("/?ref_"));
            id = firstSearchResult.attr("href");
        } catch (IOException ex) {
            Logger.getLogger(CrawlerBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.StringIndexOutOfBoundsException | NullPointerException exc ){
            
        }
        
        return id;
    }
    
    public Rating getRating(String Id){
        Rating r = null;
        
        String pageUrl = url + Id;
        
        try {
            Document doc = Jsoup.connect(pageUrl).get();
            
            Element ratingValue = doc.select("span[itemprop=ratingValue]").first();
            Element weightValue = doc.select("span[itemprop=ratingCount]").first();
            
            String ra = ratingValue.html();
            Double rad = Double.parseDouble(ra);
            String w = weightValue.html();
            int wi = NumberFormat.getNumberInstance(java.util.Locale.US).parse(w).intValue();
            
            r = new Rating(rad, wi);
            
        } catch (IOException | NumberFormatException | Selector.SelectorParseException | ParseException ex) {
        }
        
        return r;
    }
    
}