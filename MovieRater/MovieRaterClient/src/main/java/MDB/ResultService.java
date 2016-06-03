/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDB;

import Domain.CrawlResult;
import Domain.QueryResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.primefaces.context.RequestContext;

/**
 *
 * @author BartKneepkens
 */
@Stateless
public class ResultService implements Serializable
{
//    List<String> results;
    
    List<QueryResult> results;
    
    public ResultService(){
        
    }
    
    @PostConstruct
    public void init(){
        results = new ArrayList<>();
    }

    public List<QueryResult> getResults() {
        return results;
    }

    public void setResults(List<QueryResult> results) {
        this.results = results;
    }
    
    public void addResult(CrawlResult result){
        this.results.get(0).getCrawlResults().add(result);
    }
}
