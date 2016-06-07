/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author BartKneepkens
 */

@Entity
public class QueryResult implements Serializable
{
    @OneToMany
    List<CrawlResult> crawlResults;
    
    @Id
    String query;

    public List<CrawlResult> getCrawlResults() {
        return crawlResults;
    }

    public void setCrawlResults(List<CrawlResult> crawlResults) {
        this.crawlResults = crawlResults;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    public QueryResult(){
        this.crawlResults = new ArrayList<>();
    }
    
    
    public CrawlResult getCrawlResult(int index){
        if((this.crawlResults.size() - 1 )>= index){
            return this.crawlResults.get(index);
        }
        return null;
    }
   
}
