/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author BartKneepkens
 */
@Entity
public class CrawlResult implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;
    
    private double rating;
    private int weight;
    
    @Enumerated(EnumType.STRING)
    private Crawlers crawler;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Crawlers getCrawler() {
        return crawler;
    }

    public void setCrawler(Crawlers crawler) {
        this.crawler = crawler;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public CrawlResult(JsonObject j){
        this.crawler = Crawlers.valueOf(j.getString("crawlerID"));
        
        try{
            this.rating = j.getJsonNumber("rating").doubleValue();
            this.weight = j.getInt("weight");}
        catch(ClassCastException cce){
            this.rating = -1;
            this.weight = -1;
        }
    }
    
    public CrawlResult(){}
}
