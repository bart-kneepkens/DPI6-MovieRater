/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Crawler;

import Domain.Rating;
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
    
    private static final String url = "http://www.imdb.com/";
    
    // No-Arg constructor.
    public CrawlerBean(){};
    
    public void crawl(String query){
        String id = getID(query);
        Rating rating = getRating(id);
        System.out.println(factory.getMessageBody(rating));
        
        
        // Dispatch the message
    }
    
    public String getID(String query){
        
        String finalUrl = url + "find?q=";
        String id = null;
        
        for (String word : query.split(" ")) {
            finalUrl = finalUrl + word + "+";
        }
        
        finalUrl = finalUrl.substring(0, finalUrl.length() - 1);
        
        Document doc;
        try {
            doc = Jsoup.connect(finalUrl).get();
            Element firstSearchResult = doc.select("td[class=primary_photo]").first();
            
            String fullHTML = firstSearchResult.html();
            id = fullHTML.substring(fullHTML.indexOf("tt"), fullHTML.indexOf("/?ref_"));
        } catch (IOException ex) {
            Logger.getLogger(CrawlerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id;
    }
    
    public Rating getRating(String Id){
        Rating r = null;
        
        String pageUrl = url + "title/" + Id;
        
        try {
            Document doc = Jsoup.connect(pageUrl).get();
            Element ratingValue = doc.select("span[itemprop=ratingValue]").first();
            Element weightValue = doc.select("span[itemprop=ratingCount]").first();
            
            String ra = ratingValue.html();
            Double rad = Double.parseDouble(ra);
            String w = weightValue.html();
            int wi = NumberFormat.getNumberInstance(java.util.Locale.US).parse(w).intValue();
            
            r = new Rating(rad, wi);
            
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(CrawlerBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CrawlerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return r;
    }
    
}
